package me.mcforgesample.wrapper;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

public class MinecraftItem {
	private ItemStack itemStack;

	/**
	 * Tip: format codes from attributes can be removed using </br>
	 * <code>.replaceAll("\\u00a7.", "");</code>
	 * 
	 * @param itemStack
	 */
	public MinecraftItem(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	/**
	 * 
	 * @return the name of the chest menu.
	 */
	public String getName() {
		return itemStack.getDisplayName();
	}

	/**
	 * 
	 * @return all lore lines in an ArrayList of strings. Empty if there are none.
	 */
	public ArrayList<String> getLore() {
		NBTTagList lore = itemStack.getTagCompound().getCompoundTag("display").getTagList("Lore", 8);
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < lore.tagCount(); i++)
			result.add(lore.getStringTagAt(i));
		return result;
	}

}
