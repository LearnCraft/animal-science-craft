package edu.learncraft.animalsciencecraft.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelQuadruped;

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

}
