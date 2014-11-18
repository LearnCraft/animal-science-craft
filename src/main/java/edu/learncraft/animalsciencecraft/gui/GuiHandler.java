package edu.learncraft.animalsciencecraft.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import edu.learncraft.animalsciencecraft.mobs.EntitySciencePig;
import edu.learncraft.animalsciencecraft.mobs.EntityScientific;

public class GuiHandler implements IGuiHandler {
	//returns an instance of the Container you made earlier
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world,
                    int x, int y, int z) {
            return new ContainerScientific((EntityScientific) world.getEntityByID(x));
    }

    //returns an instance of the Gui you made earlier
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world,
                    int x, int y, int z) {
    	return new SciencePigGui((EntitySciencePig) world.getEntityByID(x));
    }
}
