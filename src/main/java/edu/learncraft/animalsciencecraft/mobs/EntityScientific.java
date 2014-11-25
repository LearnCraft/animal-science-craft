package edu.learncraft.animalsciencecraft.mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.learncraft.animalsciencecraft.ai.RealisticAnimalAI;
import edu.learncraft.animalsciencecraft.enums.Gender;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class EntityScientific extends EntityAnimal {
	
	protected String name;
	protected int hunger;
	protected List<EntityScientific> knownViableMates;
	protected int domestication;
	protected int potentialForProduction;
	protected Gender gender;
	protected int bred;
	protected String lineage;
	protected List<Block> lastKnownTroughs;
	protected Map<String, Integer> affinities;
	protected int meatQuantity;
	protected boolean castrated;
	protected int currentAge;
	protected int feedEfficiency;
	
	public static final Random RANDOM = new Random();
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		if (!this.name.isEmpty()) {
			compound.setString("name", this.name);
		}
		compound.setInteger("hunger", this.hunger);
		compound.setInteger("domestication", this.domestication);
		compound.setInteger("potentialForProduction", this.potentialForProduction);
		compound.setString("gender", this.gender.name());
		compound.setInteger("bred", this.bred);
		compound.setInteger("meatQuantity", this.meatQuantity);
		compound.setInteger("currentAge", this.currentAge);
		compound.setInteger("feedEfficiency", this.feedEfficiency);
		compound.setBoolean("castrated", this.castrated);
		if (!this.lineage.isEmpty()) {
			compound.setString("lineage", this.lineage);
		}
		compound.setIntArray("affinities", Affinities.encode(this.affinities));
		//compound.setBoolean("companion", this.companion);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("name")) {
			this.name = compound.getString("name");
		}
		this.hunger = compound.getInteger("hunger");
		this.domestication = compound.getInteger("domestication");
		this.potentialForProduction = compound.getInteger("potentialForProduction");
		System.out.println("Banana"+ compound.getString("gender"));
		if (compound.hasKey("gender")) {
			this.gender = Gender.valueOf(compound.getString("gender"));
		}
		this.bred = compound.getInteger("bred");
		this.meatQuantity = compound.getInteger("meatQuantity");
		this.currentAge = compound.getInteger("currentAge");
		this.feedEfficiency = compound.getInteger("feedEfficiency");
		this.castrated = compound.getBoolean("castrated");
		if (compound.hasKey("lineage")) {
			this.lineage = compound.getString("lineage");
		}
		this.affinities = Affinities.decode(compound.getIntArray("affinities"));
	}
	
	public EntityScientific(World par1World) {
		super(par1World);
		this.initializeWild();
		this.setSize(1.0F,0.5F);
		setupAI();
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		currentAge += 1;
	}
	
	private int getCurrentAge() {
		return currentAge;
	}

	@Override
	public boolean isAIEnabled()
	{
	   return true;
	}
	
	// set up AI tasks
	protected void setupAI()
	{
	   getNavigator().setAvoidsWater(true);
	   clearAITasks(); // clear any tasks assigned in super classes
	   tasks.addTask(0, new EntityAISwimming(this));
	   //tasks.addTask(1, new RealisticAnimalAI(this));
	   // the leap and the collide together form an actual attack
	   tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
	   tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, true));
	   tasks.addTask(5, new EntityAIMate(this, 1.0D));
	   tasks.addTask(6, new EntityAITempt(this, 1.25D, Items.wheat, false));
	   tasks.addTask(7, new EntityAIFollowParent(this, 1.25D));
	   tasks.addTask(8, new EntityAIWander(this, 1.0D));
	   tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
	   tasks.addTask(10, new EntityAILookIdle(this));      
	}

	protected void clearAITasks()
	{
	   tasks.taskEntries.clear();
	   targetTasks.taskEntries.clear();
	}

	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	protected void initializeWild() {
		hunger = 10;
		bred = 0;
		knownViableMates = new ArrayList<EntityScientific>();
		lineage = "";
		lastKnownTroughs = new ArrayList<Block>();
		affinities = new HashMap<String, Integer>();
		gender = Gender.random();
		castrated = false;
		currentAge = 0;
		domestication = 1;
		name = "No name";
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}

	public List<EntityScientific> getKnownViableMates() {
		return knownViableMates;
	}

	public void setKnownViableMates(List<EntityScientific> knownViableMates) {
		this.knownViableMates = knownViableMates;
	}

	public int getDomestication() {
		return domestication;
	}

	public void setDomestication(int domestication) {
		this.domestication = domestication;
	}

	public int getPotentialForProduction() {
		return potentialForProduction;
	}

	public void setPotentialForProduction(int potentialForProduction) {
		this.potentialForProduction = potentialForProduction;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getBred() {
		return bred;
	}

	public void setBred(int bred) {
		this.bred = bred;
	}

	public String getLineage() {
		return lineage;
	}

	public void setLineage(String lineage) {
		this.lineage = lineage;
	}

	public List<Block> getLastKnownTroughs() {
		return lastKnownTroughs;
	}

	public void setLastKnownTroughs(List<Block> lastKnownTroughs) {
		this.lastKnownTroughs = lastKnownTroughs;
	}

	public Map<String, Integer> getAffinities() {
		return affinities;
	}

	public void setAffinities(Map<String, Integer> affinities) {
		this.affinities = affinities;
	}

	public int getMeatQuantity() {
		return meatQuantity;
	}

	public void setMeatQuantity(int meatQuantity) {
		this.meatQuantity = meatQuantity;
	}

	public boolean isCastrated() {
		return castrated;
	}

	public void setCastrated(boolean castrated) {
		this.castrated = castrated;
	}

	public int getFeedEfficiency() {
		return feedEfficiency;
	}

	public void setFeedEfficiency(int feedEfficiency) {
		this.feedEfficiency = feedEfficiency;
	}

	public void setCurrentAge(int currentAge) {
		this.currentAge = currentAge;
	}
	
	
	public abstract String getProperName();

	public abstract boolean isAdult();
	
}
