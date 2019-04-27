package edu.learncraft.animalsciencecraft.proxy;

import java.util.ArrayList;

import edu.learncraft.animalsciencecraft.gui.pages.Page;

public class CommonProxy {
	public void registerRenderers() {
		// Nothing here as the server doesn't render graphics or entities!
	}

	public Page[] loadTutorialPages() {
		Page blank = new Page("Blank", 0, Page.FLAG_ERROR);
		return new Page[] {blank, blank, blank, blank};
	}

	public void initializeBookServer() {
		// TODO Auto-generated method stub
		
	}
}
