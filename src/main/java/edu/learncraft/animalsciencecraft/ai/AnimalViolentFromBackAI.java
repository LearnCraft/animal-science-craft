package edu.learncraft.animalsciencecraft.ai;

import edu.learncraft.animalsciencecraft.mobs.EntitySciencePig;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class AnimalViolentFromBackAI extends EntityAIBase {

	private World worldObject;
	private EntityPlayer thePlayer;
	private final EntitySciencePig theEntity;
	private final double CLOSE_DISTANCE = 3;

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
		return CLOSE_DISTANCE >= this.theEntity
				.getDistanceToEntity(this.thePlayer);
	}

	public float getAngle() {
		float angle = (float) Math.toDegrees(Math.atan2(thePlayer.posZ
				- theEntity.posZ, thePlayer.posX - theEntity.posX));

		if (angle < 0) {
			angle += 360;
		}
		return angle;
	}

	@Override
	public boolean shouldExecute() {
		this.thePlayer = this.worldObject.getClosestPlayerToEntity(
				this.theEntity, this.CLOSE_DISTANCE);
		if (this.thePlayer != null) {
			if (this.thePlayer.isEntityAlive()) {
				if (this.isPlayerClose()) {
					thePlayer.attackEntityAsMob(theEntity);
					float angle = getAngle();
					float diff = Math.abs(angle - theEntity.rotationYaw);
					System.out.println(diff);
					if (diff <= 45) {
						// Approaching from front
						return this.runInFear();
					} else if (diff >= 90) {
						this.attackPlayer();
						return this.runInFear();
					} else {
						return false;
					}
				}
			}
		}
		return false;
	}
	
	private boolean attackPlayer() {
		return thePlayer.attackEntityFrom(DamageSource.causeMobDamage(theEntity), 3f);
	}

	public boolean runInFear() {
		Vec3 var1 = RandomPositionGenerator.findRandomTarget(
				this.theEntity, 5, 4);
	
		if (var1 == null) {
			return false;
		} else {
			this.randPosX = var1.xCoord;
			this.randPosY = var1.yCoord;
			this.randPosZ = var1.zCoord;
			return true;
		}
	}

	@Override
	public boolean continueExecuting() {
		// return !this.theEntity.onGround;
		return true;
	}

	@Override
	public void startExecuting() {
		this.theEntity.getNavigator().tryMoveToXYZ(this.randPosX,
				this.randPosY, this.randPosZ, .5);
	}

	@Override
	public void updateTask() {
		this.theEntity.getLookHelper().setLookPositionWithEntity(
				this.thePlayer, 30.0F, 30.0F);
		this.theEntity.attackEntityAsMob(this.thePlayer);
	}

}
