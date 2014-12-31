package edu.learncraft.animalsciencecraft.gui.pages;

import edu.learncraft.animalsciencecraft.gui.TutorialGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public abstract class PageContent {
	public abstract int render(TutorialGui context, int xPos, int yPos, FontRenderer fontRendererObj);

	public static PageContent fromString(String newContent) {
		if (newContent.startsWith(":image:")) {
			return new ImageContent(newContent.substring(7).trim());
		} else {
			return new StringContent(newContent);
		}
	}
}
