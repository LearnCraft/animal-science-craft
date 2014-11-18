package edu.learncraft.animalsciencecraft.gui;

import edu.learncraft.animalsciencecraft.mobs.EntityScientific;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerScientific extends Container {
	
	protected EntityScientific entity;
	
	public ContainerScientific(final EntityScientific entity) {
		this.entity = entity;
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		// TODO Auto-generated method stub
		return true;
	}

}
