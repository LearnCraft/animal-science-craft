package edu.learncraft.animalsciencecraft.mobs;

import java.util.ArrayList;
import java.util.HashMap;

import edu.learncraft.animalsciencecraft.Main;
import edu.learncraft.animalsciencecraft.ai.AnimalViolentFromBackAI;
import edu.learncraft.animalsciencecraft.ai.EntityAIEatGrassMore;
import edu.learncraft.animalsciencecraft.ai.EntityAIPanicking;
import edu.learncraft.animalsciencecraft.ai.EntityAIScientificMate;
import edu.learncraft.animalsciencecraft.ai.IdleLookingAI;
import edu.learncraft.animalsciencecraft.ai.RealisticAnimalAI;
import edu.learncraft.animalsciencecraft.ai.WanderSlowlyAI;
import edu.learncraft.animalsciencecraft.gui.SciencePigGui;
import edu.learncraft.animalsciencecraft.gui.TutorialGui;
import edu.learncraft.animalsciencecraft.item.BaconItem;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class EntityScienceCow extends EntityScientific {

	protected static int ADULT_THRESHOLD = 20 * 60 * 3;
	protected static int ESTROUS_CYCLE = 20 * 60 * 2;

	public EntityScienceCow(World par1World) {
		super(par1World);
		this.initializeWild();
		this.setSize(0.9F, 0.9F);
		setupAI();
		if (getGrowingAge() >= 0 && this.getCurrentAge() <= getAdultThreshold()) {
			this.setGrowingAge((int) this.getCurrentAge() - getAdultThreshold());
		}
	}

	public EntityScienceCow(World worldObj, EntityScientific mother,
			EntityScientific father) {
		super(worldObj);
		this.initializeWild(mother, father);
		this.setSize(0.9F, 0.9F);
		setupAI();
		if (getGrowingAge() >= 0 && this.getCurrentAge() <= getAdultThreshold()) {
			this.setGrowingAge((int) this.getCurrentAge() - getAdultThreshold());
		}
	}

	public boolean isOld() {
		return getCurrentAge() >= (ESTROUS_CYCLE * 8);
	}

	public String toString() {
		return "Cow (gender=" + getGender().name() + ")";
	}

	protected void initializeWild(EntityScientific mother,
			EntityScientific father) {
		super.initializeWild();
		MatedGeneticTraits mgt = MatedGeneticTraits.mate(mother, father);
		addGeneticTraits(mgt.meatQuantity, mgt.feedEfficiency,
				mgt.domestication, mgt.potentialForProduction, Gender.random(),
				mgt.lineage, worldObj.getTotalWorldTime());
	}

	protected void initializeWild() {
		super.initializeWild();
		addGeneticTraits(0 + rand.nextInt(3), 1 + rand.nextInt(3), 1,
				1 + rand.nextInt(3), Gender.random(), "",
				worldObj.getTotalWorldTime());
	}

	protected String getLivingSound() {
		return "mob.cow.say";
	}

	protected String getHurtSound() {
		return "mob.cow.say";
	}

	protected String getDeathSound() {
		return "mob.cow.death";
	}

	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	// set up AI tasks
	protected void setupAI() {
		getNavigator().setAvoidsWater(true);
		clearAITasks(); // clear any tasks assigned in super classes

		// AI should try and swim or panic (panic occurs when burning)
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1D));
		// tasks.addTask(1, new EntityAIPanicking(this, 1.25D));

		// tasks.addTask(2, new AnimalViolentFromBackAI(this));

		// Respond to threats
		tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
		tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, true));

		// Should try to mate if possible
		tasks.addTask(4, new EntityAIScientificMate(this, 0.5D));

		// / Any grass to eat?
		this.tasks.addTask(5, this.eatGrassAI);

		tasks.addTask(6, new EntityAITempt(this, 1.25D, Items.wheat, false));

		tasks.addTask(8, new WanderSlowlyAI(this, 0.3f));

		tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class,
				6.0F));

		// tasks.addTask(0, new AnimalViolentFromBackAI(this));

		// tasks.addTask(1, new RealisticAnimalAI(this));
		// the leap and the collide together form an actual attack
		//
		//
		// tasks.addTask(5, new EntityAIMate(this, 1.0D));
		// tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class,
		// 6.0F));
		//
	}

	private EntityAIEatGrassMore eatGrassAI = new EntityAIEatGrassMore(this);

	@Override
	public void eatGrassBonus() {
		// [0 - 10]
		int bonus = (int) (getFeedEfficiency() * 2.5 + 25);
		this.setHunger(Math.min(99, this.getHunger() + bonus));
	}

	protected void clearAITasks() {
		tasks.taskEntries.clear();
		targetTasks.taskEntries.clear();
	}

	public boolean interact(EntityPlayer player) {
		if (player.worldObj.isRemote) {
			player.openGui(Main.instance, SciencePigGui.id, worldObj,
					this.getEntityId(), 0, 0);
			System.out.println("Client:"+HashUUID.encode(getUniqueID()));
		} else {
			System.out.println("Remote:"+HashUUID.encode(getUniqueID()));
		}
		return super.interact(player);
	}

	public String getProperName() {
		if (isAdult()) {
			switch (getGender()) {
			case female:
				if (getBred() > 0) {
					return "Cow";
				} else {
					return "Heifer";
				}
			case male:
				if (isCastrated()) {
					return "Steer";
				} else {
					return "Bull";
				}
			default:
				return "Cattle";
			}
		} else {
			return "Calf";
		}
	}

	@Override
	public void onUpdate() {
		if (inEstrous()) {
			spawnHearts(13 + Math.abs(getEstrous() - 12));
		}
		super.onUpdate();
	}

	private boolean isHungry() {
		return getHunger() < 5;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable father) {
		return new EntityScienceCow(worldObj, this, (EntityScientific) father);
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
		Item droppedItem = this.isBurning() ? Items.cooked_beef
				: Items.beef;
		if (this.getMeatQuantity() < 2) {
			// Terrible cow: 0-1
		} else if (this.getMeatQuantity() < 6) {
			// Good cow: 2-5
			int quantity = 1 + this.rand.nextInt((int) Math.ceil(this
					.getMeatQuantity() / 2));
			this.dropItem(droppedItem, quantity);
		} else {
			// Best cw: 6-9
			this.dropItem(droppedItem, 3);
			int quantity = 1 + this.rand.nextInt(this.getMeatQuantity() / 3);
			//this.dropItem(Main.filet, quantity);
		}
	}

	public boolean isAdult() {
		return getCurrentAge() > ADULT_THRESHOLD;
	}

	/**
	 * Returns a number between 0 and 99 (exclusive) that indicates the progress
	 * of Estrous. >=75 means that it is in Estrous.
	 */
	public int getEstrous() {
		if (getGrowingAge() >= 0) {
			long baseTime = worldObj.getTotalWorldTime() - getLastBredAt();
			return (int) (100 * (baseTime % ESTROUS_CYCLE) / ESTROUS_CYCLE);
		} else {
			return 0;
		}
	}
	
	private float getFatness() {
		return 0.75f + (0.25f * 0.1f * getMeatQuantity())
				+ (0.25f * 0.1f * getPotentialForProduction())
				+ (0.25f * 0.01f * getHunger()) + (Gender.male == getGender() ? 0.25f : 0.0f);
	}

}
