package me.mcforgesample.event;

import java.util.ArrayList;

import me.mcforgesample.wrapper.MinecraftItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class OpenChestEvent extends Event {

	private IInventory chestInventory;

	public OpenChestEvent(IInventory chestInventory) {
		this.chestInventory = chestInventory;
	}

	/**
	 * 
	 * @return the name of the chest menu.
	 */
	public IChatComponent getChestTitle() {
		return chestInventory.getDisplayName();
	}

	/**
	 * 
	 * @return ArrayList of non-null MinecraftItem's. Empty if there are none.
	 */
	public ArrayList<MinecraftItem> getChestContents() {
		ArrayList<MinecraftItem> items = new ArrayList();
		for (int i = 0; i < chestInventory.getSizeInventory(); i++)
			if (chestInventory.getStackInSlot(i) != null)
				items.add(new MinecraftItem(chestInventory.getStackInSlot(i)));
		return items;
	}

}