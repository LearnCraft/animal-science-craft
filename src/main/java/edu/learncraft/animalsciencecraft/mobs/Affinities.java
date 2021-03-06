package edu.learncraft.animalsciencecraft.mobs;

import java.util.HashMap;
import java.util.Map;

import scala.actors.threadpool.Arrays;
import net.minecraft.entity.Entity;

public enum Affinities {
	ENTITY_SCIENCE_PIG, ENTITY_SCIENCE_COW;
	
	private static final String[] names ={ EntitySciencePig.class.getName(),
		EntityScienceCow.class.getName() }; 

	public static String encode(Map<String, Integer> affinities) {
		/*int[] values = new int[names.length];
		for (int i = 0; i < names.length; i += 1) {
			if (affinities.containsKey(names[i])) {
				values[i] = affinities.get(names[i]);
			} else {
				values[i] = 1;
			}
		}
		return values;
		*/
		return "";
	}

	public static Map<String, Integer> decode(String string) {
		Map<String, Integer> result = new HashMap<String, Integer>(32);
		for (int i = 0; i < names.length; i += 1) {
			result.put(names[i], Integer.parseInt(""+string.charAt(i)));
		}
		return result;
	}
}
