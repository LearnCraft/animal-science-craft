package edu.learncraft.animalsciencecraft.mobs;

public class MatedGeneticTraits {
	public final int meatQuantity;
	public final int potentialForProduction;
	public final int domestication;
	public final int feedEfficiency;
	public final String lineage;

	public static int average(int first, int second, int bonus) {
		return Math.min(9, (first + second) / 2+bonus);
	}

	public static MatedGeneticTraits mate(EntityScientific mother, EntityScientific father) {
		// TODO: implement random growth
		int mq = average(mother.getMeatQuantity(), father.getMeatQuantity(), 1);
		int pfp = average(mother.getPotentialForProduction(),
				father.getPotentialForProduction(), 1);
		int d = average(mother.getDomestication(), father.getDomestication(), 1);
		int fe = average(mother.getFeedEfficiency(), father.getFeedEfficiency(), 1);
		return new MatedGeneticTraits(mq, pfp, d, fe,Lineage.mate(father, mother));
	}

	public MatedGeneticTraits(int meatQuantity, int potentialForProduction,
			int domestication, int feedEfficiency, String lineage) {
		super();
		this.meatQuantity = meatQuantity;
		this.potentialForProduction = potentialForProduction;
		this.domestication = domestication;
		this.feedEfficiency = feedEfficiency;
		this.lineage = lineage;
	}
}
