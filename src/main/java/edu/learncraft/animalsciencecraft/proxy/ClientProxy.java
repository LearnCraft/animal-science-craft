package edu.learncraft.animalsciencecraft.proxy;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.model.ModelPig;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.registry.RenderingRegistry;
import edu.learncraft.animalsciencecraft.Main;
import edu.learncraft.animalsciencecraft.gui.pages.Page;
import edu.learncraft.animalsciencecraft.mobs.EntitySciencePig;
import edu.learncraft.animalsciencecraft.models.ModelScientificPig;
import edu.learncraft.animalsciencecraft.renders.RenderScientificPig;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySciencePig.class,
				new RenderScientificPig(new ModelScientificPig(), 
						new ModelScientificPig(0.25F), 0.25F));

	}
	
	@Override
	public Page[] loadTutorialPages() {
		Page homePage = new Page("Home", 0, Page.FLAG_HOME);
		Page referencesPage, tutorialPage, errorPage;
		
		// Load tutorials
		ResourceLocation tutorialsFile = new ResourceLocation(Main.MODID, "lang/tutorials.txt");
		try {
			tutorialPage = new Page(tutorialsFile, homePage, "Tutorials", Page.FLAG_TUTORIALS);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("No tutorial file found!");
			tutorialPage = new Page(homePage, "Tutorials", Page.FLAG_TUTORIALS);
		}
		homePage.addPage(tutorialPage);
		
		// Load references
		ResourceLocation referencesFile = new ResourceLocation(Main.MODID, "lang/references.txt");
		try {
			referencesPage = new Page(referencesFile, homePage, "Reference", Page.FLAG_REFERENCES);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("No reference file found!");
			referencesPage = new Page(homePage, "References", Page.FLAG_REFERENCES);
		}
		homePage.addPage(referencesPage);
		
		errorPage = new Page(homePage, "Error", Page.FLAG_ERROR);
		
		homePage.printTree();
		
		return new Page[] {homePage, tutorialPage, referencesPage, errorPage};
	}
}
