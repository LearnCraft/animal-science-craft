package edu.learncraft.animalsciencecraft.gui;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.client.gui.inventory.GuiContainer;

import org.lwjgl.opengl.GL11;

import edu.learncraft.animalsciencecraft.Main;
import edu.learncraft.animalsciencecraft.mobs.EntitySciencePig;
import edu.learncraft.animalsciencecraft.mobs.HashUUID;

public class SciencePigGui extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(
			Main.MODID + ":" + "textures/gui/pig.png");
	public final static int id = 0;
	private EntitySciencePig entity;

	public SciencePigGui(EntitySciencePig pig) {
		super(new ContainerScientific(pig));
		entity = pig;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		// draw text and stuff here
		// the parameters for drawString are: string, x, y, color
		if (entity != null) {
			fontRendererObj.drawString(entity.getProperName(), 8, 8, 4210752);
			fontRendererObj.drawString("Gender: "+entity.getGender().name(), 8, 16, 4210752);
			fontRendererObj.drawString("Age:"+entity.getCurrentAge() , 8, 24, 4210752);
			fontRendererObj.drawString("ID:"+entity.getEntityId(), 8, 32, 4210752);
			fontRendererObj.drawString("Hunger:"+entity.getHunger(), 8, 40, 4210752);
			fontRendererObj.drawString("Stress:"+entity.getStress(), 8, 48, 4210752);
			fontRendererObj.drawString("Estrous:"+entity.getEstrous(), 8, 56, 4210752);
			fontRendererObj.drawString("Meat Quantity:"+entity.getMeatQuantity(), 8, 64, 4210752);
			fontRendererObj.drawString("Feed Efficiency:"+entity.getFeedEfficiency(), 8, 72, 4210752);
			fontRendererObj.drawString("Production Potential:"+entity.getPotentialForProduction(), 8, 80, 4210752);
			fontRendererObj.drawString("Domestication:"+entity.getDomestication(), 8, 88, 4210752);
			//fontRendererObj.drawString("G-age:"+entity.getGrowingAge(), 8, 88, 4210752);
			fontRendererObj.drawString("Last Bred:"+(entity.worldObj.getTotalWorldTime()-entity.getLastBredAt()), 8, 96, 4210752);
			fontRendererObj.drawString(HashUUID.encodeLineage(entity.getLineage()), 8, 104, 4210752);
			
			//System.out.println("GUI:"+HashUUID.encode(entity.getUniqueID()));
			//fontRendererObj.drawString("Name:"+HashUUID.encode(entity.getUniqueID()), 128, 32, 4210752);
		}
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
