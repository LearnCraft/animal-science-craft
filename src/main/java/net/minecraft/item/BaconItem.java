package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;

public class BaconItem extends ItemFood {
	public BaconItem() {
		super(5, true);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabFood);
		setUnlocalizedName("bacon");
	}
}
