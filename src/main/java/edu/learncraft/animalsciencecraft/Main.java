package edu.learncraft.animalsciencecraft;

import java.util.Random;

import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import edu.learncraft.animalsciencecraft.blocks.ModBlocks;
import edu.learncraft.animalsciencecraft.mobs.EntityBoar;
import edu.learncraft.animalsciencecraft.proxy.CommonProxy;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
	public static final String MODID = "animalsciencecraft";
	public static final String VERSION = "1.0";

	@Instance(MODID)
	public static Main instance;

	@SidedProxy(clientSide = "edu.learncraft.animalsciencecraft.proxy.ClientProxy", 
			serverSide = "edu.learncraft.animalsciencecraft.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModBlocks.init();
		registerEntity(EntityBoar.class, "entityTest");
		proxy.registerRenderers();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// some example code
		System.out.println("DIRT BLOCK >> " + Blocks.dirt.getUnlocalizedName());
	}

	public static void registerEntity(Class entityClass, String name) {
		int entityID = EntityRegistry.findGlobalUniqueEntityId();
		long seed = name.hashCode();
		Random rand = new Random(seed);
		int primaryColor = rand.nextInt() * 16777215;
		int secondaryColor = rand.nextInt() * 16777215;

		EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
		EntityRegistry.registerModEntity(entityClass, name, entityID, instance,
				64, 1, true);
		EntityList.entityEggs.put(Integer.valueOf(entityID),
				new EntityList.EntityEggInfo(entityID, primaryColor,
						secondaryColor));
	}
}
