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
			EntityScientific.findEntityByPersistentID(theWorld, raw_relative);
		}
		return null;
	}
	
	public static String getParents(String lineage) {
		if (lineage.length() >= 2 * UUID_SIZE) {
			return lineage.substring(0, 2*UUID_SIZE);
		} else {
			return "";
		}
	}

	public static String mate(EntityScientific father, EntityScientific mother) {
		String lineage = father.getUniqueID().toString() + mother.getUniqueID().toString() + getParents(father.getLineage()) + getParents(mother.getLineage());
		return lineage;
	}

	/**
	 * Returns whether two lineage strings have any UUIDs in common, indicating
	 * a common ancestor.
	 * @param fatherLineage
	 * @param motherLineage
	 * @return
	 */
	public static boolean areRelated(EntityScientific mother, EntityScientific father) {
		String motherLineage = mother.getUniqueID().toString()+mother.getLineage();
		String fatherLineage = father.getUniqueID().toString()+father.getLineage();
		// Build up mother's family as a set
		Set<String> motherFamily = new HashSet<String>();
		for (int i = 0; i < motherLineage.length(); i+= UUID_SIZE) {
			motherFamily.add(motherLineage.substring(i, i+UUID_SIZE));
		}
		// Check if any of the father's family is in the set
		for (int i = 0; i < fatherLineage.length(); i+= UUID_SIZE) {
			String fatherRelative = fatherLineage.substring(i, i+UUID_SIZE);
			if (motherFamily.contains(fatherRelative)) {
				return true;
			}
		}
		return false;
	}

}
