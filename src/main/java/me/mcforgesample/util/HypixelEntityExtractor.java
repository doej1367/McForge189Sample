package me.mcforgesample.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

import me.mcforgesample.wrapper.StackedEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class HypixelEntityExtractor {
	/**
	 * 
	 * @return all stacked entities
	 */
	public static ArrayList<StackedEntity> extractAllStackedEntities() {
		return extractStackedEntities(null, 0, false);
	}

	/**
	 * 
	 * @param position           - the center of the selected area
	 * @param radius             - the radius of the selected area around the center
	 * @param horizontalDistance - defines the shape of the selected area. true
	 *                           means the selected area is a cylinder from y = 0 to
	 *                           y = 255, false means it is a sphere
	 * @return all stacked entities in a selected area
	 */
	public static ArrayList<StackedEntity> extractStackedEntities(Vec3 position, double radius,
			boolean horizontalDistance) {
		// INFO EntityTypes: EntityWither, EntityPlayerSP, EntityOtherPlayerMP,
		// EntityItem, EntityArmorStand
		try {
			// step 1: filter for entites at and around given position
			List<Entity> entities = Minecraft.getMinecraft().theWorld.getLoadedEntityList();

			ArrayList<StackedEntity> drops = new ArrayList<StackedEntity>();
			for (Entity e : entities) {
				Vec3 v = e.getPositionVector();
				if (position != null && (horizontalDistance ? HorizontalPlane.distanceBetween(position, v) > radius
						: (position.distanceTo(v) > radius)))
					continue;
				String displayName = e.getDisplayName().getUnformattedText();
				ArrayList<ItemStack> inventoryContents = new ArrayList<ItemStack>();
				ItemStack[] inv = e.getInventory();
				if (inv != null)
					for (ItemStack i : inv)
						if (i != null)
							inventoryContents.add(i);
				drops.add(new StackedEntity(displayName, v, inventoryContents));
			}
			Collections.sort(drops);
			// step 2: group together multiple entities belonging to the same drop
			ArrayList<StackedEntity> mergedDrops = new ArrayList<StackedEntity>();
			StackedEntity d0 = null, d1, created = null;
			for (int i = 0; i < drops.size() - 1; i++) {
				d0 = (created == null) ? drops.get(i) : created;
				created = null;
				d1 = drops.get(i + 1);
				if (d0.compareTo(d1) == 0) {
					ArrayList tmp = new ArrayList(d0.getInv());
					tmp.addAll(d1.getInv());
					created = new StackedEntity((d0.getName() + "\n" + d1.getName()).trim(), d0.getPos(), tmp);
				} else {
					mergedDrops.add(d0);
				}
			}
			if (drops.size() > 0)
				mergedDrops.add((created == null) ? drops.get(drops.size() - 1) : created);
			return mergedDrops;
		} catch (ConcurrentModificationException e) {
			return new ArrayList<StackedEntity>();
		}
	}
}