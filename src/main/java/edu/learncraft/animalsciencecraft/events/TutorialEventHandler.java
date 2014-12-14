package edu.learncraft.animalsciencecraft.events;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import edu.learncraft.animalsciencecraft.Main;
import edu.learncraft.animalsciencecraft.gui.SciencePigGui;
import edu.learncraft.animalsciencecraft.gui.TutorialGui;

public class TutorialEventHandler {

	// http://jabelarminecraft.blogspot.com/p/minecraft-forge-172-event-handling.html

	private static EntityPlayer player = null;
	private static final String BASIC_TUTORIAL = "beginner_tutorial";
	private static KeyBinding key_openGUI = new KeyBinding("key.tutorial",
			Keyboard.KEY_H, "key.categories.learncraft");

	public TutorialEventHandler() {
		super();
		ClientRegistry.registerKeyBinding(key_openGUI);
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(BlockEvent.BreakEvent event) {
		World world = event.world;
		Block block = event.block;
		int meta = event.blockMetadata;
		EntityPlayer player = event.getPlayer();

		if (!event.world.isRemote) {
			if (event.block == Blocks.log || event.block == Blocks.log2) {
				setTutorial(1);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(InputEvent.KeyInputEvent event) {
		if (player != null && key_openGUI.isPressed()) {
			triggerGui();
		}
	}

	private void triggerGui() {
		if (player.worldObj.isRemote) {
			player.openGui(Main.instance, TutorialGui.id, player.worldObj,
					player.getEntityData().getInteger(BASIC_TUTORIAL), 0, 0);
		}
	}

	private void setTutorial(int level) {
		if (player.getEntityData().getInteger(BASIC_TUTORIAL) != level
				&& level > 0) {
			player.addChatMessage(new ChatComponentText(
					"You have new help available - press 'H'."));
		}
		player.getEntityData().setInteger(BASIC_TUTORIAL, level);
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(EntityJoinWorldEvent event) {
		// Register extended entity properties
		if (event.entity instanceof EntityPlayer
				&& event.world.isRemote == true) {
			player = (EntityPlayer) event.entity;
			player.addChatMessage(new ChatComponentText(
					"Press 'H' for help at any time."));
			if (!player.getEntityData().hasKey(BASIC_TUTORIAL)) {
				setTutorial(0);
			}
		}
	}
}
