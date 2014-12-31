package edu.learncraft.animalsciencecraft.gui;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import scala.actors.threadpool.Arrays;
import edu.learncraft.animalsciencecraft.Main;
import edu.learncraft.animalsciencecraft.gui.pages.Page;
import edu.learncraft.animalsciencecraft.gui.pages.PageContent;
import edu.learncraft.animalsciencecraft.mobs.EntitySciencePig;

public class TutorialGui extends GuiScreen {

	public class GotoPageButton extends GuiButton {

		public GotoPageButton(int id, int x, int y, int w, int h, String value,
				Integer[] to_page) {
			super(id, x, y, w, h, value);
			this.to_page = to_page;
			// TODO Auto-generated constructor stub
		}

		public Integer[] to_page;
	}

	// private static final String texture = Main.MODID + ":" +
	// "textures/gui/tutorial-%s.png";
	ResourceLocation texture = new ResourceLocation(Main.MODID + ":"
			+ "textures/gui/full.png");

	static Page homePage;
	static Page referencesPage;
	static Page tutorialsPage;
	static Page errorPage;
	public static Stack<Integer[]> seenPages;

	public final static int id = 1;

	private static final int GOTO_EXIT = 0;
	private static final int GOTO_HOME = 1;
	private static final int GOTO_BACK = 2;
	private static final int GOTO_PAGE = 5;

	private GuiTextField search;
	private int BG_X;
	private final int BG_Y = 3;
	private static final int BG_WIDTH = 256;
	private static final int BG_HEIGHT = 184;
	private static final int PADDING = 4;
	private int BOTTOM_Y;
	private int CENTER_X;

	private static int SCROLL_X;
	private static int SCROLL_Y;
	private static int SCROLL_BAR_WIDTH;
	private static int SCROLL_BAR_HEIGHT;
	private static int SCROLL_BOX_WIDTH;
	private static int SCROLL_BOX_HEIGHT;

	private boolean isScrollPressed = false;
	private int scrollPos = 0; // up to 93

	private GuiButton back;

	public TutorialGui() {
		super();

		homePage = Main.homePage;
		referencesPage = Main.referencesPage;
		tutorialsPage = Main.tutorialsPage;
		errorPage = Main.errorPage;

		seenPages = Main.seenPages;
	}

	public Page getCurrentPage() {
		if (seenPages.isEmpty()) {
			return homePage;
		} else {
			return homePage.follow(seenPages.peek());
		}
	}

	public void drawBackground() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		this.drawTexturedModalRect(BG_X, BG_Y, 0, 0, BG_WIDTH, BG_HEIGHT);
	}

	public void drawHeader() {
		/*fontRendererObj.drawString(screensToString(), back.xPosition + PADDING
				+ back.width + 20, back.yPosition + back.height + 10, 4210752);*/
		this.search.drawTextBox();
	}

	private void drawScroll(int mouseY) {
		SCROLL_X = BG_X + 237;
		SCROLL_Y = BG_Y + 35;
		SCROLL_BAR_WIDTH = 12;
		SCROLL_BAR_HEIGHT = 15;
		SCROLL_BOX_WIDTH = 12;
		SCROLL_BOX_HEIGHT = 145;
		int MAX_SCROLL_BOX_HEIGHT = SCROLL_BOX_HEIGHT - SCROLL_BAR_HEIGHT - 2;
		if (isScrollPressed) {
			scrollPos = mouseY - SCROLL_Y - SCROLL_BAR_HEIGHT + 8;
			scrollPos = scrollPos < 0 ? 0 : scrollPos;
			scrollPos = scrollPos > MAX_SCROLL_BOX_HEIGHT ? MAX_SCROLL_BOX_HEIGHT
					: scrollPos;
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(SCROLL_X, SCROLL_Y + scrollPos, 0, 185,
				SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawBackground();
		this.drawScroll(mouseY);
		this.drawHeader();
		
		int height = BG_Y + 30;
		for (int i = 0; i < getCurrentPage().body.size(); i += 1) {
			PageContent c = getCurrentPage().body.get(i);
			c.render(this, BG_X + PADDING, height + i
					* (10 + PADDING), this.fontRendererObj);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);

	}

	private void drawText() {
		int var4 = (this.width - 256) / 2;
		fontRendererObj.drawString("(1) Approach a tree", var4 + 20, 135,
				4210752);
	}

	public String screensToString() {
		StringBuilder sb = new StringBuilder(">");
		if (!seenPages.isEmpty()) {
			for (Integer path : seenPages.peek()) {
				sb.append(path + "/");
			}
		}
		return sb.toString();
	}

	@Override
	public void initGui() {
		super.initGui();
		System.out.println("Current Page:"+Arrays.toString(this.getCurrentPage().getPath()));

		BG_X = width / 2 - BG_WIDTH / 2 + 3;
		BOTTOM_Y = BG_HEIGHT - fontRendererObj.FONT_HEIGHT - PADDING;
		CENTER_X = width / 2;

		this.buttonList.clear();

		// Home, Up, Back buttons
		GuiButton home = new GuiButton(GOTO_HOME, BG_X + PADDING, BG_Y
				+ PADDING, 40, 20, "Home");
		buttonList.add(home);
		GuiButton up = new GuiButton(GOTO_BACK, home.xPosition + PADDING
				+ home.getButtonWidth(), BG_Y + PADDING, 20, 20, "<");
		buttonList.add(up);
		back = new GuiButton(GOTO_BACK, up.xPosition + PADDING
				+ up.getButtonWidth(), BG_Y + PADDING, 20, 20, "Up");
		buttonList.add(back);

		Keyboard.enableRepeatEvents(true);
		this.search = new GuiTextField(this.fontRendererObj, back.xPosition
				+ back.getButtonWidth() + PADDING, BG_Y + PADDING, BG_WIDTH
				- back.xPosition - back.getButtonWidth() - PADDING * 2, 20); // this.fontRendererObj.FONT_HEIGHT);
		this.search.setTextColor(16777215);
		this.search.setFocused(true);
		this.search.setText("");

		// Enable, Disable, Visibility
		if (getCurrentPage().flag == Page.FLAG_HOME) {
			up.enabled = false;
			home.enabled = false;
			search.setEnabled(false);
		} else {
			search.setEnabled(true);
		}
		if (seenPages.isEmpty()) {
			back.enabled = false;
		}

		if (!getCurrentPage().children.isEmpty()) {
			for (int i = 0; i < getCurrentPage().children.size(); i += 1) {
				Page current = getCurrentPage().children.get(i);
				GotoPageButton newBtn = new GotoPageButton(GOTO_PAGE, BG_X
						+ PADDING, home.height + home.yPosition + PADDING
						* 2 + i * (20 + PADDING),
						current.name.length() * 10, 20, current.name,
						getCurrentPage().getPath(i));
				buttonList.add(newBtn);
			}
		}
		switch (getCurrentPage().flag) {
		/*case Page.FLAG_HOME:
			buttonList.add(new GotoPageButton(GOTO_PAGE, BG_X + PADDING,
					BG_HEIGHT / 2 - home.height / 2 - PADDING / 2, 40, 20,
					"Reference", referencesPage.getPath()));
			buttonList.add(new GotoPageButton(GOTO_PAGE, BG_X + PADDING,
					BG_HEIGHT / 2 + home.height / 2 + PADDING / 2, 40, 20,
					"Tutorial", tutorialsPage.getPath()));
			break;*/
		case Page.FLAG_OTHER:
			
			break;
		default:
			break;
		}
	}

	/**
	 * Fired when a key is typed. This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e).
	 */
	protected void keyTyped(char letter, int code) {
		if (this.search != null && this.search.textboxKeyTyped(letter, code)) {
			// Action
		} else {
			super.keyTyped(letter, code);
		}
	}

	protected void actionPerformed(GuiButton guibutton) {
		// id is the id you give your button
		switch (guibutton.id) {
		case GOTO_EXIT:
			mc.thePlayer.closeScreen();
			break;
		case GOTO_HOME:
			seenPages.clear();
			initGui();
			break;
		case GOTO_BACK:
			goBack();
			initGui();
			break;
		case GOTO_PAGE:
			GotoPageButton gpb = (GotoPageButton) guibutton;
			System.out.println("Moving to:"+Arrays.toString(gpb.to_page));
			seenPages.push(gpb.to_page);
			initGui();
			break;
		default:
			seenPages.push(homePage.getPath());
			initGui();
			break;
		}
		// Packet code here
		// PacketDispatcher.sendPacketToServer(packet); //send packet
	}

	private void goBack() {
		if (!seenPages.isEmpty()) {
			seenPages.pop();
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	private boolean isMouseOverArea(int mouseX, int mouseY, int posX, int posY,
			int sizeX, int sizeY) {
		return (mouseX >= posX && mouseX < posX + sizeX && mouseY >= posY && mouseY < posY
				+ sizeY);
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int wheelState = Mouse.getEventDWheel();
		if (wheelState != 0) {
			scrollPos += wheelState > 0 ? -8 : 8;
			scrollPos = scrollPos < 0 ? 0 : scrollPos;
			scrollPos = scrollPos > 93 ? 93 : scrollPos;
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		if (button != 0)
			return;
		if (isMouseOverArea(mouseX, mouseY, SCROLL_X, SCROLL_Y,
				SCROLL_BOX_WIDTH, SCROLL_BOX_HEIGHT)) {
			isScrollPressed = true;
			return;
		}
		super.mouseClicked(mouseX, mouseY, button);
		/*
		 * for (int i = 0; i < skills.skillBar.length; i++) if
		 * (skills.skillBar[i] != null && isMouseOverArea(mouseX, mouseY, width
		 * / 2 - 96, (height / 2 - 45) + (21 * i), 16, 16)) { heldSkill =
		 * skills.skillBar[i]; sendSkillUpdate(i, null); return; } int offset =
		 * Math.round((scrollPos * (skills.knownSkills.size() - 5)) / 93);
		 * offset = offset < 0 ? 0 : offset; for (int i = offset; i < offset + 5
		 * && i < skills.knownSkills.size(); i++) if (isMouseOverArea(mouseX,
		 * mouseY, width / 2 - 71, height / 2 - 45 + (21 * (i - offset)), 16,
		 * 16) && skills.knownSkills.get(i) != null) { if
		 * (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ||
		 * Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) for (int j = 0; j <
		 * skills.skillBar.length; j++) if (skills.skillBar[j] == null) {
		 * sendSkillUpdate(j, skills.knownSkills.get(i)); return; } heldSkill =
		 * SkillRegistry.get(skills.knownSkills.get(i)); return; }
		 */
	}

	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int button) {
		if (!Mouse.isButtonDown(0)) {
			isScrollPressed = false;
			/*
			 * if (heldSkill != null) { for (int i = 0; i <
			 * skills.skillBar.length; i++) if (isMouseOverArea(mouseX, mouseY,
			 * width / 2 - 96, (height / 2 - 45) + (21 * i), 16, 16)) {
			 * sendSkillUpdate(i, heldSkill.getName()); break; } heldSkill =
			 * null; }
			 */
		}
		super.mouseMovedOrUp(mouseX, mouseY, button);
	}

}
