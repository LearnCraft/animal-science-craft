package edu.learncraft.animalsciencecraft.renders;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import edu.learncraft.animalsciencecraft.Main;

public class RenderTest extends RenderPig {

	private static final ResourceLocation textureLocation = new ResourceLocation(
			Main.MODID + ":" + "textures/entities/pig/pig.png");

	public RenderTest(ModelPig model, float shadowSize) {
		super(model, model, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return textureLocation;
	}
}