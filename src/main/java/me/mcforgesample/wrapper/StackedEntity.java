package me.mcforgesample.wrapper;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class StackedEntity implements Comparable<StackedEntity> {
	private String name;
	private Vec3 pos;
	private ArrayList<ItemStack> inv;

	public StackedEntity(String displayName, Vec3 v, ArrayList<ItemStack> inventoryContents) {
		this.name = displayName;
		this.pos = v;
		this.inv = inventoryContents;
	}

	@Override
	public int compareTo(StackedEntity o) {
		if (!(o instanceof StackedEntity))
			return -2;
		if (Math.abs(pos.xCoord - o.pos.xCoord) + Math.abs(pos.zCoord - o.pos.zCoord) <= 0.03126
				&& Math.abs(pos.yCoord - o.pos.yCoord) <= 2.0)
			return 0;
		else if (pos.xCoord != o.pos.xCoord)
			return (int) Math.signum(pos.xCoord - o.pos.xCoord);
		else if (pos.zCoord != o.pos.zCoord)
			return (int) Math.signum(pos.zCoord - o.pos.zCoord);
		else if (pos.yCoord != o.pos.yCoord)
			return (int) Math.signum(pos.yCoord - o.pos.yCoord);
		return 0;
	}

	public String getName() {
		return name;
	}

	public Vec3 getPos() {
		return pos;
	}

	public ArrayList<ItemStack> getInv() {
		return inv;
	}

	@Override
	public String toString() {
		return name + "," + pos.xCoord + "," + pos.yCoord + "," + pos.zCoord + ","
				+ (inv.size() > 0 ? (inv.get(0) != null ? inv.get(0).getDisplayName() : "") : "");
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof StackedEntity))
			return false;
		return compareTo((StackedEntity) obj) == 0;
	}

}