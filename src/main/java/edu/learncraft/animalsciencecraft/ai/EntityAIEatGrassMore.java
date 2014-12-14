package edu.learncraft.animalsciencecraft.ai;

import edu.learncraft.animalsciencecraft.mobs.EntityScientific;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIEatGrassMore extends EntityAIBase {

	private EntityScientific animal;
    private World world;
    int timer;
    private static final String __OBFID = "CL_00001582";

    public EntityAIEatGrassMore(EntityScientific animal)
    {
        this.animal = animal;
        this.world = animal.worldObj;
        this.setMutexBits(7);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.animal.getRNG().nextInt(animal.getHunger()*animal.getHunger()/20+4) != 0)
        {
            return false;
        }
        else
        {
            int var1 = MathHelper.floor_double(this.animal.posX);
            int var2 = MathHelper.floor_double(this.animal.posY);
            int var3 = MathHelper.floor_double(this.animal.posZ);
            return this.world.getBlock(var1, var2, var3) == Blocks.tallgrass && this.world.getBlockMetadata(var1, var2, var3) == 1 ? true : this.world.getBlock(var1, var2 - 1, var3) == Blocks.grass;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.timer = 40;
        this.world.setEntityState(this.animal, (byte)10);
        this.animal.getNavigator().clearPathEntity();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.timer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.timer > 0;
    }

    public int func_151499_f()
    {
        return this.timer;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.timer = Math.max(0, this.timer - 1);

        
        if (this.timer == 4)
        {
            int var1 = MathHelper.floor_double(this.animal.posX);
            int var2 = MathHelper.floor_double(this.animal.posY);
            int var3 = MathHelper.floor_double(this.animal.posZ);

            if (this.world.getBlock(var1, var2, var3) == Blocks.tallgrass)
            {
                if (this.world.getGameRules().getGameRuleBooleanValue("mobGriefing"))
                {
                    this.world.func_147480_a(var1, var2, var3, false);
                }

                this.animal.eatGrassBonus();
            }
            else if (this.world.getBlock(var1, var2 - 1, var3) == Blocks.grass)
            {
                if (this.world.getGameRules().getGameRuleBooleanValue("mobGriefing"))
                {
                    this.world.playAuxSFX(2001, var1, var2 - 1, var3, Block.getIdFromBlock(Blocks.grass));
                    this.world.setBlock(var1, var2 - 1, var3, Blocks.dirt, 0, 2);
                }
                this.animal.eatGrassBonus();
            }
        }
    }
}
