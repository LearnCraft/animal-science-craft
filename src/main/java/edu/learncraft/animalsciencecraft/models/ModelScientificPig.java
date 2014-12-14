package edu.learncraft.animalsciencecraft.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.entity.Entity;

public class ModelScientificPig extends ModelQuadruped {
	
	public ModelScientificPig()
    {
        this(0.0F);
    }

	public ModelScientificPig(float factor) {
		super(6, factor);
        this.head.setTextureOffset(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, factor);
        this.field_78145_g = 4.0F;
	}
	
	/**
     * Sets the models various rotation angles then renders the model.
     */
	@Override
    public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
    {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.head.render(p_78088_7_);
        this.body.render(p_78088_7_);
        this.leg1.render(p_78088_7_);
        this.leg2.render(p_78088_7_);
        this.leg3.render(p_78088_7_);
        this.leg4.render(p_78088_7_);
    }

}
