package edu.learncraft.animalsciencecraft.ai;

import edu.learncraft.animalsciencecraft.mobs.EntityBoar;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.util.MathHelper;

public class RealisticAnimalAI extends EntityAIBase {
	
	private final EntityBoar theEntity;
	private boolean visibility;
	
	public RealisticAnimalAI(EntityBoar par1Entity) {
		theEntity = par1Entity;
		setMutexBits(1);		
	}

	@Override
	public boolean shouldExecute() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void startExecuting()
	{
		double d0 = this.theEntity.posX - this.theEntity.posX;
        double d1 = this.theEntity.posZ - this.theEntity.posZ;
        float f = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
        this.theEntity.motionX += d0 / (double)f * 0.5D * 0.800000011920929D + this.theEntity.motionX * 0.20000000298023224D;
        this.theEntity.motionZ += d1 / (double)f * 0.5D * 0.800000011920929D + this.theEntity.motionZ * 0.20000000298023224D;
        this.theEntity.motionY = (double).5;
	}
	
	@Override
	public boolean continueExecuting() {
		return !this.theEntity.onGround;
	}
	
}
