package me.mcforgesample.command;

import java.util.List;

import me.mcforgesample.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
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
	}

	@Override
	public int getRequiredPermissionLevel() {
		// needed so that the user can access the command
		return 0;
	}

}