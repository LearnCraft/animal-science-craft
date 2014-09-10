package edu.learncraft.animalsciencecraft.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import edu.learncraft.animalsciencecraft.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

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
