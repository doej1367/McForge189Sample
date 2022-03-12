package me.mcforgesample.util;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import me.mcforgesample.wrapper.StackedEntitiesList;
import me.mcforgesample.wrapper.StackedEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
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
			// step 1: filter for entities at and around given position
			List<Entity> entities = Minecraft.getMinecraft().theWorld.getLoadedEntityList();
			StackedEntitiesList costumMobs = new StackedEntitiesList();
			for (Entity e : entities) {
				Vec3 v = e.getPositionVector();
				if (position != null && (horizontalDistance ? HorizontalPlane.distanceBetween(position, v) > radius
						: (position.distanceTo(v) > radius)))
					continue;
				costumMobs.add(e);
			}
			// step 2: group together multiple entities belonging to the same drop
			ArrayList<StackedEntity> result = costumMobs.getStackedEntities();
			Vec3 playerPos = Minecraft.getMinecraft().thePlayer.getPositionVector();
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				StackedEntity stackedEntity = (StackedEntity) iterator.next();
				if (stackedEntity.getPos().distanceTo(playerPos) <= 0.5)
					iterator.remove();
			}
			return result;
		} catch (ConcurrentModificationException e) {
			return new ArrayList<>();
		}
	}
}