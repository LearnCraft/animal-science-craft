package edu.learncraft.animalsciencecraft.gui.server;

import java.util.Stack;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class BookProfile implements IExtendedEntityProperties {
	public final static String EXT_PROP_NAME = "BookProfile";

	private final EntityPlayer player;

	private Stack<BookURL> previousPages;
	private Stack<BookURL> nextPages;

	public BookProfile(EntityPlayer player) {
		this.player = player;
		// Custom properties
		this.previousPages = new Stack<BookURL>();
		this.nextPages = new Stack<BookURL>();
	}

	/**
	 * Used to register these extended properties for the player during
	 * EntityConstructing event This method is for convenience only; it will
	 * make your code look nicer
	 */
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(BookProfile.EXT_PROP_NAME,
				new BookProfile(player));
	}

	/**
	 * Returns BookProfile properties for player This method is for convenience
	 * only; it will make your code look nicer
	 */
	public static final BookProfile get(EntityPlayer player) {
		return (BookProfile) player.getExtendedProperties(EXT_PROP_NAME);
	}

	@Override
	public void init(Entity arg0, World arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		
		// properties.getString("previousPages")
		this.previousPages = new Stack<BookURL>();
		this.nextPages = new Stack<BookURL>(); 
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		// We need to create a new tag compound that will save everything for
		// our Extended Properties
		NBTTagCompound properties = new NBTTagCompound();

		properties.setString("previousPages", "");
		properties.setString("nextPages", "");
		
		compound.setTag(EXT_PROP_NAME, properties);
	}

}
