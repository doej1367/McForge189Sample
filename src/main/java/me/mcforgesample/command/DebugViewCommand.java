package me.mcforgesample.command;

import me.mcforgesample.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class DebugViewCommand extends CommandBase {
	private Main main;

	public DebugViewCommand(Main main) {
		this.main = main;
	}

	@Override
	public String getCommandName() {
		// the command can be used by typing '/test' in chat
		return "debugview";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "mcforgesample test command";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (main.getSettings().getSetting("debug").equalsIgnoreCase("true")) {
			main.getSettings().putSetting("debug", "false");
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText("McForgeSample > debug view disabled"));
		} else {
			main.getSettings().putSetting("debug", "true");
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText("McForgeSample > debug view enabled"));
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		// needed so that the user can access the command
		return 0;
	}

}