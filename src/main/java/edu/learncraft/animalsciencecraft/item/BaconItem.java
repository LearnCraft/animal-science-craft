package edu.learncraft.animalsciencecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class BaconItem extends ItemFood {
	public BaconItem() {
		super(5, true);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabFood);
		setUnlocalizedName("bacon");
	}
}
