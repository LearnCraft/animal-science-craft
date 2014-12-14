package edu.learncraft.animalsciencecraft.ai;

import edu.learncraft.animalsciencecraft.mobs.EntityScientific;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;


public class WanderSlowlyAI extends EntityAIBase{
    private EntityScientific entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;
    private static final String __OBFID = "CL_00001608";

    public WanderSlowlyAI(EntityScientific p_i1648_1_, double p_i1648_2_)
    {
        this.entity = p_i1648_1_;
        this.speed = p_i1648_2_;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        /*if (this.entity.getAge() >= 100)
        {
            return false;
        }
        else*/
    	final int probability = 50 + 2*entity.getHunger()-entity.getEstrous()/2+entity.getStress()*50;
    	if (this.entity.getRNG().nextInt(probability) != 0)
        {
            return false;
        }
        else
        {
            Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.entity, 15, 7);

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.xPosition = var1.xCoord;
                this.yPosition = var1.yCoord;
                this.zPosition = var1.zCoord;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.entity.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }
}
