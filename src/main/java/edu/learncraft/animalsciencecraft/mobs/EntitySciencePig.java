package edu.learncraft.animalsciencecraft.mobs;

import java.util.ArrayList;
import java.util.HashMap;

import edu.learncraft.animalsciencecraft.Main;
import edu.learncraft.animalsciencecraft.ai.AnimalViolentFromBackAI;
import edu.learncraft.animalsciencecraft.ai.RealisticAnimalAI;
import edu.learncraft.animalsciencecraft.enums.Gender;
import edu.learncraft.animalsciencecraft.gui.SciencePigGui;
import net.minecraft.block.Block;
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
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class EntitySciencePig extends EntityScientific {
	
	private static int PIG_ADULT_THRESHOLD = 20 * 60 * 3;
	
	public EntitySciencePig(World par1World) {
		super(par1World);
		this.setSize(2.0F,10.5F);
		setupAI();
	}
	
	public String toString() {
		return "Pig (gender="+this.gender.name()+")";
	}
	
	protected void initializeWild() {
		super.initializeWild();
		
		feedEfficiency = 1 + RANDOM.nextInt(3);
		potentialForProduction = 1 + RANDOM.nextInt(3);
		meatQuantity = 0 + RANDOM.nextInt(3);
	}
	
	protected String getLivingSound()
    {
        return "mob.pig";
    }

    protected String getHurtSound()
    {
        return "mob.pighurt";
    }

    protected String getDeathSound()
    {
        return "mob.pighurt";
    }

    protected float getSoundVolume()
    {
        return 0.4F;
    }

	@Override
	public boolean isAIEnabled()
	{
	   return true;
	}
	
	// set up AI tasks
	protected void setupAI()
	{
	   getNavigator().setAvoidsWater(true);
	   clearAITasks(); // clear any tasks assigned in super classes
	   tasks.addTask(0,  new AnimalViolentFromBackAI(this));
	   //tasks.addTask(0, new EntityAISwimming(this));
	   //tasks.addTask(1, new RealisticAnimalAI(this));
	   // the leap and the collide together form an actual attack
	   //tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
	   //tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, true));
	   //tasks.addTask(5, new EntityAIMate(this, 1.0D));
	   //tasks.addTask(6, new EntityAITempt(this, 1.25D, Items.wheat, false));
	   //tasks.addTask(7, new EntityAIFollowParent(this, 1.25D));
	   tasks.addTask(8, new EntityAIWander(this, 1.0D));
	   //tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
	   tasks.addTask(10, new EntityAILookIdle(this));      
	}

	protected void clearAITasks()
	{
	   tasks.taskEntries.clear();
	   targetTasks.taskEntries.clear();
	}
	
	public boolean interact(EntityPlayer player) {
		if (player.worldObj.isRemote) {
			player.openGui(Main.instance, SciencePigGui.id, worldObj, this.getEntityId(), 0, 0);
		}
		return super.interact(player);
	}

	public String getProperName() {
		if (isAdult()) {
			switch (getGender()) {
			case female:
				if (getBred() > 0) {
					return "sow";
				} else {
					return "gilt";
				}
			case male:
				if (isCastrated()) {
					return "barrow";
				} else {
					return "boar";
				}
			default:
				return "hog";
			}
		} else {
			return "piglet";
		}
	}
	
	public boolean isAdult() {
		return getAge() <= PIG_ADULT_THRESHOLD;
	}

}
