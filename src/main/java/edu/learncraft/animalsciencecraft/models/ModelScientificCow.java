package edu.learncraft.animalsciencecraft.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelScientificCow extends ModelQuadruped {
	
	public ModelRenderer udder;
	
	public ModelScientificCow()
    {
        this(0.0F);
    }

	public ModelScientificCow(float factor) {
		super(6, factor);
        this.head.setTextureOffset(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, factor);
        this.field_78145_g = 4.0F;
        
        this.udder = new ModelRenderer(this, 14, 0);
        this.udder.addBox(-1F, 8.0F, -2F, 2, 2, 4, 0.0F);
        this.udder.setRotationPoint(0.0F, -1, -4.0F);
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
        this.udder.render(p_78088_7_);
    }

}
