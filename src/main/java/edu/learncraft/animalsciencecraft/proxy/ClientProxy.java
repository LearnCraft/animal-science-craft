package edu.learncraft.animalsciencecraft.proxy;

import net.minecraft.client.model.ModelPig;
import cpw.mods.fml.client.registry.RenderingRegistry;
import edu.learncraft.animalsciencecraft.mobs.EntitySciencePig;
import edu.learncraft.animalsciencecraft.renders.RenderTest;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySciencePig.class,
				new RenderTest(new ModelPig(), 0.5F));

	}
}
