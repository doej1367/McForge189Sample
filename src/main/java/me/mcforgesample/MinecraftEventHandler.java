package me.mcforgesample;

import org.lwjgl.util.vector.Vector3f;

import me.mcforgesample.event.OpenChestEvent;
import me.mcforgesample.util.HypixelEntityExtractor;
import me.mcforgesample.wrapper.StackedEntity;
import moulberry.notenoughupdates.util.RenderUtil;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MinecraftEventHandler {
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
		// useful attributes:
		// # event.name - e.g. "random.successful_hit" or "random.explode"
		// a full list can be found here:
		// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/mapping-and-modding-tutorials/2213619-1-8-all-playsound-sound-arguments
		// # event.sound.getPitch() - e.g. 0.8f or 1.0f
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if (main.getSettings().getSetting("debug").equalsIgnoreCase("true"))
			for (StackedEntity e : HypixelEntityExtractor.extractAllStackedEntities())
				RenderUtil.renderWayPoint(e.getName(),
						new Vector3f((float) e.getPos().xCoord, (float) e.getPos().yCoord, (float) e.getPos().zCoord),
						event.partialTicks); // TODO
	}
}