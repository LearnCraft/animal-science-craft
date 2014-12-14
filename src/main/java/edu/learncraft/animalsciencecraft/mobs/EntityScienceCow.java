package edu.learncraft.animalsciencecraft.mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.learncraft.animalsciencecraft.ai.RealisticAnimalAI;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityScienceCow extends EntityScientific {

	private static final int COW_ADULT_THRESHOLD = 20 * 60 * 4;

	protected void initializeWild() {
		super.initializeWild();

		addGeneticTraits(0 + rand.nextInt(3), 1 + rand.nextInt(3), 1,
				1 + rand.nextInt(3), Gender.random(), "",
				worldObj.getTotalWorldTime());
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	public EntityScienceCow(World par1World) {
		super(par1World);
		this.setSize(1.0F, 0.5F);
		setupAI();
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	// set up AI tasks
	protected void setupAI() {
		getNavigator().setAvoidsWater(true);
		clearAITasks(); // clear any tasks assigned in super classes
		tasks.addTask(0, new EntityAISwimming(this));
		// tasks.addTask(1, new RealisticAnimalAI(this));
		// the leap and the collide together form an actual attack
		tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
		tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, true));
		tasks.addTask(5, new EntityAIMate(this, 1.0D));
		tasks.addTask(6, new EntityAITempt(this, 1.25D, Items.wheat, false));
		tasks.addTask(7, new EntityAIFollowParent(this, 1.25D));
		tasks.addTask(8, new EntityAIWander(this, 1.0D));
		tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class,
				6.0F));
		tasks.addTask(10, new EntityAILookIdle(this));
	}

	protected void clearAITasks() {
		tasks.taskEntries.clear();
		targetTasks.taskEntries.clear();
	}

	public Item getDropItem() {
		return Items.beef; // RANDOM.nextInt(1+meatQuantity);
	}

	@Override
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

	public boolean isAdult() {
		return getAge() <= COW_ADULT_THRESHOLD;
	}

	@Override
	public boolean isOld() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getEstrous() {
		// TODO Auto-generated method stub
		return 0;
	}

}
