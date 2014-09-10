package edu.learncraft.animalsciencecraft.mobs;

import edu.learncraft.animalsciencecraft.ai.RealisticAnimalAI;
import net.minecraft.entity.EntityCreature;
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
import net.minecraft.world.World;

public class EntityBoar extends EntityPig {
	public EntityBoar(World par1World) {
		super(par1World);
		setupAI();
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
	   //tasks.addTask(10, new EntityAILookIdle(this));
	   //targetTasks.addTask(0, new EntityAIHurtByTargetHerdAnimal(this, true));      
	}

	protected void clearAITasks()
	{
	   tasks.taskEntries.clear();
	   targetTasks.taskEntries.clear();
	}

}
