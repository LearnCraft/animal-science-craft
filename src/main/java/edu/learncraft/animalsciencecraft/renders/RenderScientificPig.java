package edu.learncraft.animalsciencecraft.renders;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import edu.learncraft.animalsciencecraft.Main;
import edu.learncraft.animalsciencecraft.enums.Gender;
import edu.learncraft.animalsciencecraft.mobs.EntitySciencePig;

public class RenderScientificPig extends RenderLiving {

	private static final ResourceLocation maleTextureLocation = new ResourceLocation(
			Main.MODID + ":" + "textures/entities/pig/pig_male.png");
	private static final ResourceLocation femaleTextureLocation = new ResourceLocation(
			Main.MODID + ":" + "textures/entities/pig/pig_female.png");

	public RenderScientificPig(ModelBase p_i1265_1_, ModelBase p_i1265_2_, float p_i1265_3_) {
		super(p_i1265_1_, p_i1265_3_);
		this.setRenderPassModel(p_i1265_2_);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntitySciencePig)entity);
	}
	protected ResourceLocation getEntityTexture(EntitySciencePig entity) {
		if (entity.getGender() == Gender.male) {
			return maleTextureLocation;
		} else {
			return femaleTextureLocation; 
		}
	}
}