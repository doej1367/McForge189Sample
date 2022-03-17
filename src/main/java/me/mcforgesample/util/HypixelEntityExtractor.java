package me.mcforgesample.util;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import me.mcforgesample.wrapper.StackedEntitiesList;
import me.mcforgesample.wrapper.StackedEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class HypixelEntityExtractor {
	static long timestamp_last_chache = 0;
	static ArrayList<StackedEntity> cached = new ArrayList<>();

	/**
	 *
	 * @return all stacked entities
	 */
	public static ArrayList<StackedEntity> extractAllStackedEntities() {
		return extractStackedEntities(null, 0, false);
	}

	/**
	 * This excludes the player on the client side
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
		if (timestamp_last_chache + 300L > System.currentTimeMillis())
			return cached;
		// INFO EntityTypes: EntityWither, EntityPlayerSP, EntityOtherPlayerMP,
		// EntityItem, EntityArmorStand
		try {
			// step 1: filter for entities at and around given position
			List<Entity> entities = Minecraft.getMinecraft().theWorld.getLoadedEntityList();
			StackedEntitiesList costumMobs = new StackedEntitiesList();
			for (Entity e : entities) {
				if (((e instanceof EntityOtherPlayerMP || e instanceof EntityPlayerSP))
						&& e.getName().contains(Minecraft.getMinecraft().thePlayer.getName()))
					continue;
				Vec3 v = e.getPositionVector();
				if (position != null && (horizontalDistance ? HorizontalPlane.distanceBetween(position, v) > radius
						: (position.distanceTo(v) > radius)))
					continue;
				costumMobs.add(e);
			}
			// step 2: group together multiple entities belonging to the same drop
			cached = costumMobs.getStackedEntities();
			timestamp_last_chache = System.currentTimeMillis();
			return cached;
		} catch (ConcurrentModificationException e) {
			return new ArrayList<>();
		}
	}
}