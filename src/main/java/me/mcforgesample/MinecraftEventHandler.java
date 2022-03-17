package me.mcforgesample;

import java.util.HashSet;
import java.util.Iterator;

import org.lwjgl.util.vector.Vector3f;

import me.mcforgesample.event.OpenChestEvent;
import me.mcforgesample.util.HypixelEntityExtractor;
import me.mcforgesample.wrapper.MinecraftSound;
import me.mcforgesample.wrapper.StackedEntity;
import moulberry.notenoughupdates.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MinecraftEventHandler {
	private HashSet<MinecraftSound> soundSet = new HashSet<>();
	private Main main;

	public MinecraftEventHandler(Main main) {
		this.main = main;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onChatMessageReceived(ClientChatReceivedEvent event) {
		// event.type: 0 = chat, 2 = overHotbar
		byte type = event.type;

	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiOpen(final GuiScreenEvent.InitGuiEvent.Post event) {
		if (event.gui != null && event.gui instanceof GuiChest) {
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(50);
						IInventory chestInventory = ((ContainerChest) ((GuiChest) event.gui).inventorySlots)
								.getLowerChestInventory();
						MinecraftForge.EVENT_BUS.post(new OpenChestEvent(chestInventory));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onSoundPlayed(PlaySoundEvent event) {
		if (event != null && event.sound != null && event.sound.getSoundLocation() != null
				&& Minecraft.getMinecraft().thePlayer != null
				&& Minecraft.getMinecraft().thePlayer.getPositionVector().distanceTo(
						new Vec3(event.sound.getXPosF(), event.sound.getYPosF(), event.sound.getZPosF())) > 0.1)
			soundSet.add(new MinecraftSound(event));
		// useful attributes:
		// # event.name - e.g. "random.successful_hit" or "random.explode"
		// a full list can be found here:
		// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/mapping-and-modding-tutorials/2213619-1-8-all-playsound-sound-arguments
		// # event.sound.getPitch() - e.g. 0.8f or 1.0f
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if (main.getSettings().getSetting("debugview").equalsIgnoreCase("true")) {
			String debugFilter = main.getSettings().getSetting("debugViewFilter").equalsIgnoreCase("default") ? ""
					: main.getSettings().getSetting("debugViewFilter");
			for (StackedEntity e : HypixelEntityExtractor.extractAllStackedEntities())
				if (e.getName().matches(debugFilter))
					RenderUtil.renderWayPoint(e.getName(), new Vector3f((float) e.getPos().xCoord,
							(float) e.getPos().yCoord, (float) e.getPos().zCoord), event.partialTicks); // TODO
		}
		if (main.getSettings().getSetting("debugsound").equalsIgnoreCase("true")) {
			String debugFilter = main.getSettings().getSetting("debugSoundFilter").equalsIgnoreCase("default") ? ""
					: main.getSettings().getSetting("debugSoundFilter");
			for (Iterator iterator = soundSet.iterator(); iterator.hasNext();) {
				MinecraftSound sound = (MinecraftSound) iterator.next();
				if (sound.getAge() > 2000L)
					iterator.remove();
			}
			for (MinecraftSound e : soundSet) {
				if (e.getName().matches(debugFilter) && e.getAge() <= 2000L)
					RenderUtil.renderWayPoint(
							e.getName() + " " + Math.round(e.getPitch() * 10) / 10.0 + " "
									+ Math.round(e.getVolume() * 10) / 10.0,
							new Vector3f(e.getX(), e.getY(), e.getZ()), event.partialTicks); // TODO
			}
		}
	}
}