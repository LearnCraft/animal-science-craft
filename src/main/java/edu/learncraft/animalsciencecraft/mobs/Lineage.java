package edu.learncraft.animalsciencecraft.mobs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import cpw.mods.fml.common.registry.EntityRegistry;

public class Lineage {

	private static final int MAX_RELATIVES = 2+4;
	private static final int UUID_SIZE = 36;

	public static EntityScientific getSire(World theWorld, String lineage) {
		// TODO
		Iterable<String> split_relatives = Splitter.fixedLength(UUID_SIZE).split(lineage);
		int index = 0;
		for (String raw_relative : split_relatives) {
			findEntityByPersistentID(theWorld, raw_relative);
		}
		return null;
	}

	public static Lineage mate(EntityScientific father, EntityScientific mother) {
		// TODO
		return null;
	}

	/**
	 * Returns whether two lineage strings have any UUIDs in common, indicating
	 * a common ancestor.
	 * @param fatherLineage
	 * @param motherLineage
	 * @return
	 */
	public boolean areRelated(String fatherLineage, String motherLineage) {
		// Build up mother's family as a set
		Set<String> motherFamily = new HashSet<String>();
		for (int i = 0; i < motherLineage.length(); i+= UUID_SIZE) {
			motherFamily.add(motherLineage.substring(i, UUID_SIZE));
		}
		// Check if any of the father's family is in the set
		for (int i = 0; i < fatherLineage.length(); i+= UUID_SIZE) {
			String fatherRelative = fatherLineage.substring(i, UUID_SIZE);
			if (motherFamily.contains(fatherRelative)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Useful function to find the entity with the given UUID
	 * @param world
	 * @param id
	 * @return
	 */
	private static Entity findEntityByPersistentID(World world, String id) {
		for (Object o : world.getLoadedEntityList()) {
			Entity e = (Entity)o;
			if (e.getPersistentID().toString().equals(id)) return e;
		}
		return null;
	}
}
