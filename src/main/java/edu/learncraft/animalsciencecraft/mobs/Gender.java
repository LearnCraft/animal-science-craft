package edu.learncraft.animalsciencecraft.mobs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Gender {
	male, female;

	// Cache Random, List, and size for performance
	private static final Random RANDOM = new Random();
	private static final List<Gender> VALUES = Collections
			.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();

	public static Gender random() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

	public static Gender shortDecode(char c) {
		switch (c) {
		case 'm':
			return male;
		case 'f':
			return female;
		default:
			return male;
		}
	}

	public char shortEncode() {
		switch (this) {
		case female:
			return 'f';
		case male:
			return 'm';
		default:
			return 'm';
		}
	}
}
