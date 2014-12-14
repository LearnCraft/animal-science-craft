package edu.learncraft.animalsciencecraft;

/**
 * TODO:
 * open minecraft for the first time
 * break your first wood block
 * if you break some other kind of block
 * 
 * -) Milk cures poison (also stacks). In general, give special abilities for the new food items.  
 * -) Grooming glove, used to collect feathers and make dogs love you (make sure feathers don't drop like crazy)
 * -) 
 * -) Teaching dogs to find items (ways to give it a scent) (powederize)
 * -) Pigs should find mushrooms
 */

import java.util.Random;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import edu.learncraft.animalsciencecraft.blocks.ModBlocks;
import edu.learncraft.animalsciencecraft.events.TutorialEventHandler;
import edu.learncraft.animalsciencecraft.gui.GuiHandler;
import edu.learncraft.animalsciencecraft.item.BaconItem;
import edu.learncraft.animalsciencecraft.mobs.EntityScienceCow;
import edu.learncraft.animalsciencecraft.mobs.EntitySciencePig;
import edu.learncraft.animalsciencecraft.proxy.CommonProxy;
import edu.learncraft.animalsciencecraft.worlds.ModGenerator;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
	public static final String MODID = "animalsciencecraft";
	public static final String VERSION = "1.0";

	@Instance(MODID)
	public static Main instance;

	@SidedProxy(clientSide = "edu.learncraft.animalsciencecraft.proxy.ClientProxy", serverSide = "edu.learncraft.animalsciencecraft.proxy.CommonProxy")
	public static CommonProxy proxy;

	// Items
	public static Item bacon;

	public static void registerEntity(Class entityClass, String name) {
		int entityID = EntityRegistry.findGlobalUniqueEntityId();
		long seed = name.hashCode();
		Random rand = new Random(seed);
		int primaryColor = rand.nextInt() * 16777215;
		int secondaryColor = rand.nextInt() * 16777215;

		EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
		EntityRegistry.registerModEntity(entityClass, name, entityID, instance,
				64, 3, true);
		for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
			if (BiomeGenBase.getBiomeGenArray()[i] != null) {
				EntityRegistry.addSpawn(entityClass, 10, 1, 3,
						EnumCreatureType.creature,
						BiomeGenBase.getBiomeGenArray()[i]);
			}
		}
		EntityList.entityEggs.put(Integer.valueOf(entityID),
				new EntityList.EntityEggInfo(entityID, primaryColor,
						secondaryColor));
	}

	public static void registerRecipes() {
		ItemStack wheatStack = new ItemStack(Items.wheat);
		ItemStack dirtStack = new ItemStack(Blocks.dirt);
		ItemStack silageStack = new ItemStack(ModBlocks.silageBlock);

		GameRegistry.addRecipe(silageStack, "x  ", "x  ", "x  ", 'x',
				wheatStack);
		GameRegistry.addRecipe(silageStack, " x ", " x ", " x ", 'x',
				wheatStack);
		GameRegistry.addRecipe(silageStack, "  x", "  x", "  x", 'x',
				wheatStack);
	}

	private static void unregisterEntity(Class entityClass) {
		for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
			if (BiomeGenBase.getBiomeGenArray()[i] != null) {
				EntityRegistry.removeSpawn(entityClass,
						EnumCreatureType.creature,
						BiomeGenBase.getBiomeGenArray()[i]);
			}
		}
	}

	@Mod.EventHandler
	public void onInit(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(new ModGenerator(), 0);
		registerRecipes();
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModBlocks.init();
		registerAnimals();

		BiomeGenBase[] allBiomes = Iterators.toArray(Iterators.filter(
				Iterators.forArray(BiomeGenBase.getBiomeGenArray()),
				Predicates.notNull()), BiomeGenBase.class);

		registerItems();
		registerGUIs();
		registerEventListeners();

		proxy.registerRenderers();
	}

	private void registerGUIs() {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}

	public void registerEntitySpawns() {

	}

	private void registerItems() {
		bacon = new BaconItem().setTextureName("animalsciencecraft:bacon");
		GameRegistry.registerItem(bacon, "bacon");
	}
	
	private void registerAnimals() {
		unregisterEntity(EntityPig.class);
		unregisterEntity(EntitySheep.class);
		unregisterEntity(EntityCow.class);
		unregisterEntity(EntityChicken.class);
		unregisterEntity(EntityWolf.class);
		unregisterEntity(EntityHorse.class);
		
		registerEntity(EntitySciencePig.class, "entitySciencePig");
		// registerEntity(EntityScienceCow.class, "entityScienceCow");
	}
	
	public void registerEventListeners() 
	{
	    // DEBUG
	    System.out.println("Registering event listeners");

	    FMLCommonHandler.instance().bus().register(new TutorialEventHandler());
	    MinecraftForge.EVENT_BUS.register(new TutorialEventHandler());
	}
}
