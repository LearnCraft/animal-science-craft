package edu.learncraft.animalsciencecraft.ai;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import scala.tools.nsc.backend.icode.analysis.TypeFlowAnalysis.MethodTFA.TypeOfStackPos;
import edu.learncraft.animalsciencecraft.mobs.EntitySciencePig;
import edu.learncraft.animalsciencecraft.mobs.EntityScientific;
import edu.learncraft.animalsciencecraft.mobs.Lineage;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityXPOrb;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class EntityAIScientificMate extends EntityAIBase {
	private EntityScientific theAnimal;
	World theWorld;
	private EntityScientific targetMate;

	/**
	 * Delay preventing a baby from spawning immediately when two mate-able
	 * animals find each other.
	 */
	int spawnBabyDelay;
	int BABY_THRESHOLD = 20 * 10;

	/** The speed the creature moves at during mating behavior. */
	double moveSpeed;
	private static final String __OBFID = "CL_00001578";

	public EntityAIScientificMate(EntityScientific p_i1619_1_, double p_i1619_2_) {
		this.theAnimal = p_i1619_1_;
		this.theWorld = p_i1619_1_.worldObj;
		this.moveSpeed = p_i1619_2_;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		if (!this.theAnimal.inEstrous()) {
			return false;
		} else {
			this.targetMate = this.getNearbyMate();
			if (this.targetMate != null) {
				return true;
			} else {
				this.theAnimal.panicking = true;
				return false;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting() {
		return this.targetMate.isEntityAlive() && this.theAnimal.inEstrous()
				&& this.spawnBabyDelay < BABY_THRESHOLD;
	}

	/**
	 * Resets the task
	 */
	public void resetTask() {
		this.targetMate = null;
		this.spawnBabyDelay = 0;
	}

	/**
	 * Updates the task
	 */
	public void updateTask() {
		// Move towards mate
		this.theAnimal.getLookHelper().setLookPositionWithEntity(
				this.targetMate, 10.0F,
				(float) this.theAnimal.getVerticalFaceSpeed());
		this.theAnimal.getNavigator().tryMoveToEntityLiving(this.targetMate,
				this.moveSpeed);

		// Move mate towards this
		targetMate.getLookHelper().setLookPositionWithEntity(theAnimal, 10.0F,
				(float) targetMate.getVerticalFaceSpeed());
		targetMate.getNavigator().tryMoveToEntityLiving(theAnimal, moveSpeed);

		++this.spawnBabyDelay;

		if (this.spawnBabyDelay >= BABY_THRESHOLD
				&& this.theAnimal.getDistanceSqToEntity(this.targetMate) < 9.0D) {
			if (this.theAnimal instanceof EntitySciencePig) {
				int number = 1 + theAnimal.getRNG().nextInt(
						2 + (int)(2+theAnimal.getPotentialForProduction() / 3));
				for (int i = 0; i < number; i += 1) {
					this.spawnBaby();
				}
			} else {
				this.spawnBaby();
			}
		}
	}

	/**
	 * Loops through nearby animals and finds another animal of the same type
	 * that can be mated with. Returns the first valid mate found.
	 */
	private EntityScientific getNearbyMate() {
		float var1 = 8.0F;
		List var2 = this.theWorld.getEntitiesWithinAABB(this.theAnimal
				.getClass(), this.theAnimal.boundingBox.expand((double) var1,
				(double) var1, (double) var1));
		double var3 = Double.MAX_VALUE;
		EntityScientific var5 = null;
		Iterator var6 = var2.iterator();

		while (var6.hasNext()) {
			EntityScientific var7 = (EntityScientific) var6.next();
			if (this.theAnimal.canMateWith(var7)
					&& !Lineage.areRelated(var7,
							this.theAnimal)
					&& this.theAnimal.getDistanceSqToEntity(var7) < var3) {
				var5 = var7;
				var3 = this.theAnimal.getDistanceSqToEntity(var7);
			}
		}

		return var5;
	}

	/**
	 * Spawns a baby animal of the same type.
	 */
	private void spawnBaby() {
		EntityScientific var1 = (EntityScientific) this.theAnimal
				.createChild(this.targetMate);
		theAnimal.procreate();
		targetMate.procreate();

		if (var1 != null) {
			EntityPlayer var2 = this.theAnimal.func_146083_cb();

			if (var2 == null && this.targetMate.func_146083_cb() != null) {
				var2 = this.targetMate.func_146083_cb();
			}

			if (var2 != null) {
				var2.triggerAchievement(StatList.field_151186_x);

				/*
				 * if (this.theAnimal instanceof EntityScneCow) {
				 * var2.triggerAchievement(AchievementList.field_150962_H); }
				 */
			}
			var1.setGrowingAge(-var1.getAdultThreshold());
			var1.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY,
					this.theAnimal.posZ, 0.0F, 0.0F);
			this.theWorld.spawnEntityInWorld(var1);
			Random var3 = this.theAnimal.getRNG();

			for (int var4 = 0; var4 < 7; ++var4) {
				double var5 = var3.nextGaussian() * 0.02D;
				double var7 = var3.nextGaussian() * 0.02D;
				double var9 = var3.nextGaussian() * 0.02D;
				this.theWorld
						.spawnParticle(
								"heart",
								this.theAnimal.posX
										+ (double) (var3.nextFloat()
												* this.theAnimal.width * 2.0F)
										- (double) this.theAnimal.width,
								this.theAnimal.posY
										+ 0.5D
										+ (double) (var3.nextFloat() * this.theAnimal.height),
								this.theAnimal.posZ
										+ (double) (var3.nextFloat()
												* this.theAnimal.width * 2.0F)
										- (double) this.theAnimal.width, var5,
								var7, var9);
			}

			if (this.theWorld.getGameRules().getGameRuleBooleanValue(
					"doMobLoot")) {
				this.theWorld.spawnEntityInWorld(new EntityXPOrb(this.theWorld,
						this.theAnimal.posX, this.theAnimal.posY,
						this.theAnimal.posZ, var3.nextInt(7) + 1));
			}
		}
	}
}
