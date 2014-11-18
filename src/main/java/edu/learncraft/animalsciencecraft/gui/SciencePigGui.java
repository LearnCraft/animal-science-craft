package edu.learncraft.animalsciencecraft.gui;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.client.gui.inventory.GuiContainer;

import org.lwjgl.opengl.GL11;

import edu.learncraft.animalsciencecraft.Main;
import edu.learncraft.animalsciencecraft.mobs.EntitySciencePig;

public class SciencePigGui extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(
			Main.MODID + ":" + "textures/gui/pig.png");
	public static int id = 0;

	public SciencePigGui(EntitySciencePig pig) {
		super(new ContainerScientific(pig));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		// draw text and stuff here
		// the parameters for drawString are: string, x, y, color
		fontRendererObj.drawString("Pig", 8, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		// draw your Gui here, only thing you need to change is the path
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
