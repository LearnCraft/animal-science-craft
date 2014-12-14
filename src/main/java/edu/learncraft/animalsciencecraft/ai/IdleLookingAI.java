package edu.learncraft.animalsciencecraft.ai;

import edu.learncraft.animalsciencecraft.mobs.EntityScientific;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class IdleLookingAI extends EntityAIBase {
	/** The entity that is looking idle. */
	private EntityScientific idleEntity;

	/** X offset to look at */
	private double lookX;

	/** Z offset to look at */
	private double lookZ;

	/**
	 * A decrementing tick that stops the entity from being idle once it reaches
	 * 0.
	 */
	private int idleTime;

	private int lookPitch;
	private static final String __OBFID = "CL_00001607";

	public IdleLookingAI(EntityScientific p_i1647_1_) {
		this.idleEntity = p_i1647_1_;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		return this.idleEntity.getRNG().nextInt(10) == 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting() {
		return this.idleTime >= 0;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		double var1 = (Math.PI * 2D) * this.idleEntity.getRNG().nextDouble();
		this.lookX = Math.cos(var1);
		this.lookZ = Math.sin(var1);
		this.lookPitch = this.idleEntity.getRNG().nextInt(360);
		this.idleTime = 200 + this.idleEntity.getRNG().nextInt(20);
	}

	/**
	 * Updates the task
	 */
	public void updateTask() {
		--this.idleTime;
		// this.idleEntity.rotationYaw =
		// this.idleEntity.getRNG().nextInt(180)-90;
		// this.idleEntity.rotationPitch =
		// this.idleEntity.getRNG().nextInt(180)-90;
		/*
		 * this.idleEntity.getLookHelper().setLookPosition( this.idleEntity.posX
		 * + this.lookX, this.idleEntity.posY + (double)
		 * this.idleEntity.getEyeHeight(), this.idleEntity.posZ + this.lookZ,
		 * 10.0F, (float) this.idleEntity.getVerticalFaceSpeed());
		 */
	}
}
