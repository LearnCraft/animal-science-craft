package edu.learncraft.animalsciencecraft.mobs;

import java.util.Random;

public class RandomSingleton {
    private static RandomSingleton instance;
    private Random rnd;
 
    private RandomSingleton() {
        rnd = new Random();
    }
 
    public static RandomSingleton getInstance() {
        if(instance == null) {
            instance = new RandomSingleton();
        }
        return instance;
    }
 
    public double nextDouble() {
         return rnd.nextDouble();
    }
    
    public int nextInt() {
		return rnd.nextInt();
    }

	public int nextInt(int i) {
		return rnd.nextInt(i);
	}
}
