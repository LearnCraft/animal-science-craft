package edu.learncraft.animalsciencecraft.proxy;

import net.minecraft.client.model.ModelPig;
import cpw.mods.fml.client.registry.RenderingRegistry;
import edu.learncraft.animalsciencecraft.mobs.EntitySciencePig;
import edu.learncraft.animalsciencecraft.models.ModelScientificPig;
import edu.learncraft.animalsciencecraft.renders.RenderScientificPig;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySciencePig.class,
				new RenderScientificPig(new ModelScientificPig(), 
						new ModelScientificPig(0.5F), 0.5F));

	}
}
