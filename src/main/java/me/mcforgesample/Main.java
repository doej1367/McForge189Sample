package me.mcforgesample;

import me.mcforgesample.command.TestCommand;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main
{
    public static final String MODID = "mcforgesample";
    public static final String VERSION = "1.0.0";
    
    
    @EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println("[OK] preInit MC Forge 1.8.9 Example Mod");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// create commands
		ClientCommandHandler.instance.registerCommand(new TestCommand(this));
		// converting Forge events into readable Minecraft events
		MinecraftForge.EVENT_BUS.register(new MinecraftEventHandler(this));
		// handling custom Minecraft events
		
		
		System.out.println("[OK] registered events");
		System.out.println("[OK] init MC Forge 1.8.9 Example Mod");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println("[OK] postInit MC Forge 1.8.9 Example Mod");
	}
}
