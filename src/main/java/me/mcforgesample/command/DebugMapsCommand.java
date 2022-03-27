package me.mcforgesample.command;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;

import me.mcforgesample.Main;
import me.mcforgesample.util.HorizontalPlane;
import me.mcforgesample.util.HypixelEntityExtractor;
import me.mcforgesample.util.MapUtil;
import me.mcforgesample.wrapper.StackedEntitiesList;
import me.mcforgesample.wrapper.StackedEntity;
import moulberry.notenoughupdates.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.world.storage.MapData;

public class DebugMapsCommand extends CommandBase {
	private Main main;

	public DebugMapsCommand(Main main) {
		this.main = main;
	}

	@Override
	public String getCommandName() {
		// the command can be used by typing '/test' in chat
		return "debugmaps";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "mcforgesample debugmaps command";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		// define what happens when the command is executed
		// in this case a simple message is displayed
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("DebugMapsCommand"));
		long timestamp = System.currentTimeMillis();
		File mapsFolder = new File(main.getSettings().getLogFolder(), "maps");
		mapsFolder.mkdirs();
		List<Entity> entities = Minecraft.getMinecraft().theWorld.getLoadedEntityList();
		for (Entity e : entities) {
			if (!(e instanceof EntityItemFrame))
				continue;
			EntityItemFrame itemFrame = (EntityItemFrame) e;
			ItemStack item = itemFrame.getDisplayedItem();
			if (item == null || !item.getUnlocalizedName().equalsIgnoreCase("item.map"))
				continue;
			MapData map = Items.filled_map.getMapData(item, Minecraft.getMinecraft().theWorld);
			if (map == null || map.colors == null)
				continue;
			File image = new File(mapsFolder, timestamp + "-" + e.getPosition().getX() + "-" + e.getPosition().getY()
					+ "-" + e.getPosition().getZ() + ".png");
			try {
				ImageIO.write(new MapUtil(map).getImage(), "png", image);
			} catch (IOException ignored) {
			}
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		// needed so that the user can access the command
		return 0;
	}

}