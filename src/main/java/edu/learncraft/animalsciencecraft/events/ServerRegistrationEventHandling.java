package edu.learncraft.animalsciencecraft.events;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import edu.learncraft.animalsciencecraft.gui.server.BookProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class ServerRegistrationEventHandling {

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEntityConstructing(EntityConstructing event) {
		/*
		 * Be sure to check if the entity being constructed is the correct type
		 * for the extended properties you're about to add! The null check may
		 * not be necessary - I only use it to make sure properties are only
		 * registered once per entity
		 */
		if (event.entity instanceof EntityPlayer
				&& BookProfile.get((EntityPlayer) event.entity) == null)
			// This is how extended properties are registered using our
			// convenient method from earlier
			BookProfile.register((EntityPlayer) event.entity);
	}

}
