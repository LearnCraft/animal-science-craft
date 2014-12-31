package edu.learncraft.animalsciencecraft.gui.pages;

import java.util.Vector;

import edu.learncraft.animalsciencecraft.gui.TutorialGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class StringContent extends PageContent {
	
	public String value;

	public StringContent(String value) {
		this.value = value;
	}

	@Override
	public int render(TutorialGui context, int xPos, int yPos, FontRenderer fontRendererObj) {
		//context.drawString(, p_73731_2_, p_73731_3_, p_73731_4_, p_73731_5_);
		// TODO Auto-generated method stub
		fontRendererObj.drawString(value, xPos, yPos,
				4210752);
		return yPos;
	}
}
