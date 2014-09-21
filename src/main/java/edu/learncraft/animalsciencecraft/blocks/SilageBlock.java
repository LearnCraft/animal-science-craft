package edu.learncraft.animalsciencecraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import edu.learncraft.animalsciencecraft.Main;

public class SilageBlock extends Block {

	private String name = "silageBlock";

	protected SilageBlock() {
		super(Material.plants);

		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockName(Main.MODID + "_" + name);
		setBlockTextureName(Main.MODID + ":" + name);

		GameRegistry.registerBlock(this, name);
	}

}
