package edu.learncraft.animalsciencecraft.gui;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import edu.learncraft.animalsciencecraft.Main;
import edu.learncraft.animalsciencecraft.mobs.EntitySciencePig;

public class TutorialGui extends GuiScreen {

	// private static final String texture = Main.MODID + ":" +
	// "textures/gui/tutorial-%s.png";
	public final static int id = 1;
	private int level;

	private GuiTextField search;

	public TutorialGui(int level) {
		super();
		this.level = level;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// draw text and stuff here
		// the parameters for drawString are: string, x, y, color
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation texture = new ResourceLocation(Main.MODID + ":"
				+ "textures/gui/tutorial-" + level + ".png");
		this.mc.getTextureManager().bindTexture(texture);

		int field_146478_u = 256;
		int field_146477_v = 192;
		int var4 = (this.width - field_146478_u) / 2;
		byte var5 = 2;
		this.drawTexturedModalRect(var4, var5, 0, 0, field_146478_u,
				field_146477_v);
		this.drawText();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.search.drawTextBox();
	}

	private void drawText() {
		int var4 = (this.width - 256) / 2;
		switch (level) {
		case 0:
			fontRendererObj.drawString("(1) Approach a tree", var4 + 20, 135,
					4210752);
			fontRendererObj.drawString("(2) Hold left mouse button", var4 + 20,
					145, 4210752);
			break;
		case 1:
			fontRendererObj.drawString(
					"(1) Press 'E' to bring up your Inventory", var4 + 2, 125,
					4210752);
			fontRendererObj.drawString(
					"(2) Click your log, click your crafting table", var4 + 2,
					135, 4210752);
			fontRendererObj.drawString(
					"(3) Click your wood, click your inventory bar", var4 + 2,
					145, 4210752);
			break;
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
		// make buttons
		// id, x, y, width, height, text
		int var4 = (this.width - 192) / 2;
		buttonList.add(new GuiButton(0, var4 + 70, 160, 40, 20, "Okay"));

		Keyboard.enableRepeatEvents(true);
		this.search = new GuiTextField(this.fontRendererObj, var4 + 20, 20, 89,
				this.fontRendererObj.FONT_HEIGHT);

		this.search.setTextColor(16777215);
		//this.search.setMaxStringLength(15);
		this.search.setFocused(true);
		this.search.setEnabled(true);
		this.search.setText("Test");
	}

	/**
	 * Fired when a key is typed. This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e).
	 */
	protected void keyTyped(char letter, int code) {
		System.out.println("Keyboard:"+letter+","+code);
		if (this.search.textboxKeyTyped(letter, code)) {
			// Action
			System.out.println("Action!");
		} else {
			System.out.println("Entered");
			super.keyTyped(letter, code);
		}
	}

	protected void actionPerformed(GuiButton guibutton) {
		// id is the id you give your button
		switch (guibutton.id) {
		case 0:
			mc.thePlayer.closeScreen();
			break;
		}
		// Packet code here
		// PacketDispatcher.sendPacketToServer(packet); //send packet
	}

}
