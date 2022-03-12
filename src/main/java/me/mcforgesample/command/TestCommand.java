package me.mcforgesample.command;

import org.lwjgl.util.vector.Vector3f;

import me.mcforgesample.Main;
import me.mcforgesample.util.HypixelEntityExtractor;
import me.mcforgesample.wrapper.StackedEntity;
import moulberry.notenoughupdates.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class TestCommand extends CommandBase {
	private Main main;

	public TestCommand(Main main) {
		this.main = main;
	}

	@Override
	public String getCommandName() {
		// the command can be used by typing '/test' in chat
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "hyblockrnganalyzer test command";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		// define what happens when the command is executed
		// in this case a simple message is displayed
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("TestCommand"));

		// display all entities visible to the client
		for (StackedEntity e : HypixelEntityExtractor.extractAllStackedEntities()) {
			RenderUtil.renderWayPoint(e.getName(),
					new Vector3f((float) e.getPos().xCoord, (float) e.getPos().yCoord, (float) e.getPos().zCoord), 50); // TODO
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		// needed so that the user can access the command
		return 0;
	}

}