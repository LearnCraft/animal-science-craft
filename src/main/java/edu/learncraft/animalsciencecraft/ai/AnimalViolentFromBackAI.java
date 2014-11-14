package edu.learncraft.animalsciencecraft.ai;

import edu.learncraft.animalsciencecraft.mobs.EntitySciencePig;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class AnimalViolentFromBackAI extends EntityAIBase {
	
	private World worldObject;
	private EntityPlayer thePlayer;
	private final EntitySciencePig theEntity;
	private final double CLOSE_DISTANCE = 10;
	
	private double previousX;
	private double previousZ;
	private double totalMovement;
	
	private double randPosX;
    private double randPosY;
    private double randPosZ;
	
	public AnimalViolentFromBackAI(EntitySciencePig theEntity) {
		this.theEntity = theEntity;
		this.worldObject = theEntity.worldObj;
		setMutexBits(1);
	}
	
	public boolean isPlayerClose() {
		return CLOSE_DISTANCE >= this.theEntity.getDistanceToEntity(this.thePlayer);
	}

	@Override
	public boolean shouldExecute() {
		this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.theEntity, this.CLOSE_DISTANCE);
		if (this.thePlayer != null) {
			if (this.thePlayer.isEntityAlive()) {
				if (this.isPlayerClose()) {
					totalMovement = Math.abs(this.thePlayer.motionX) + Math.abs(this.thePlayer.motionZ);
					if (totalMovement >= .2) {
						Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.theEntity, 5, 4);

			            if (var1 == null)
			            {
			                return false;
			            }
			            else
			            {
			            	System.out.println("BITE");
			                this.randPosX = var1.xCoord;
			                this.randPosY = var1.yCoord;
			                this.randPosZ = var1.zCoord;
			                return true;
			            }
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean continueExecuting() {
		//return !this.theEntity.onGround;
		return true;
	}
	
	@Override
	public void startExecuting()
	{
		this.theEntity.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, .5);
	}
	
	@Override
	public void updateTask() {
		this.theEntity.getLookHelper().setLookPositionWithEntity(this.thePlayer, 30.0F, 30.0F);
		this.theEntity.attackEntityAsMob(this.thePlayer);
	}
	
}
