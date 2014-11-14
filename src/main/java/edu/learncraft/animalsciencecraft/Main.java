package edu.learncraft.animalsciencecraft;

import java.util.Random;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.BaconItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import edu.learncraft.animalsciencecraft.blocks.ModBlocks;
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
	
	public void registerEntitySpawns() {
		
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModBlocks.init();
		unregisterEntity(EntityPig.class);
		unregisterEntity(EntitySheep.class);
		unregisterEntity(EntityCow.class);
		unregisterEntity(EntityChicken.class);
		unregisterEntity(EntityWolf.class);
		registerEntity(EntitySciencePig.class, "entitySciencePig");
		//registerEntity(EntityScienceCow.class, "entityScienceCow");

		BiomeGenBase[] allBiomes = Iterators.toArray(Iterators.filter(
				Iterators.forArray(BiomeGenBase.getBiomeGenArray()),
				Predicates.notNull()), BiomeGenBase.class);
		
		registerItems();

		registerRecipes();
		proxy.registerRenderers();
	}

	private void registerItems() {
		bacon = new BaconItem().setTextureName("animalsciencecraft:bacon");
		GameRegistry.registerItem(bacon, "bacon");
	}

	private static void unregisterEntity(Class entityClass) {
		for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++)
        {
            if (BiomeGenBase.getBiomeGenArray()[i] != null)
            {
                EntityRegistry.removeSpawn(entityClass, EnumCreatureType.creature, BiomeGenBase.getBiomeGenArray()[i]); 
            }
        }
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(new ModGenerator(), 0);
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
		for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++)
        {
            if (BiomeGenBase.getBiomeGenArray()[i] != null)
            {
                EntityRegistry.addSpawn(entityClass, 10, 1, 3, EnumCreatureType.creature, BiomeGenBase.getBiomeGenArray()[i]);
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
}
