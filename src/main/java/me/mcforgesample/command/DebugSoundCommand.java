package me.mcforgesample.command;

import me.mcforgesample.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class DebugSoundCommand extends CommandBase {
	private Main main;

	public DebugSoundCommand(Main main) {
		this.main = main;
	}

	@Override
	public String getCommandName() {
		// the command can be used by typing '/debugsound' in chat
		return "debugsound";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "mcforgesample debugsound command";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args != null && args.length > 0 && args[0].equalsIgnoreCase("off")) {
			main.getSettings().putSetting("debugsound", "false");
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText("McForgeSample > debug sound disabled"));
		} else {
			main.getSettings().putSetting("debugsound", "true");
			String debugFilter = "";
			if (args != null)
				for (int i = 0; i < args.length; i++)
					debugFilter += (debugFilter.isEmpty() ? "" : "|") + ".*" + args[i] + ".*";
			debugFilter = debugFilter.isEmpty() ? ".*" : debugFilter;
			main.getSettings().putSetting("debugSoundFilter", debugFilter);
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText("McForgeSample > debug sound enabled"));
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		// needed so that the user can access the command
		return 0;
	}

}