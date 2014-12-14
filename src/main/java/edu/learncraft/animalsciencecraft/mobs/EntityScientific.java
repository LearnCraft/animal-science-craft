package edu.learncraft.animalsciencecraft.mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import edu.learncraft.animalsciencecraft.ai.RealisticAnimalAI;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
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
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityScientific extends EntityAnimal {

	private final double CLOSE_DISTANCE = 3;
	
	protected static int ADULT_THRESHOLD = 20 * 60 * 3;
	
	protected Map<String, Integer> affinities;

	protected String name;
	protected boolean castrated;
	protected int bred;

	protected static final int GENETIC_TRAITS = 23;
	protected static final int HUNGER = 24;
	protected static final int LAST_BRED_AT = 25;

	public boolean panicking;
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		if (!this.name.isEmpty()) {
			compound.setString("name", this.name);
		}
		compound.setInteger("hunger", this.getHunger());
		compound.setInteger("bred", this.bred);
		compound.setLong("lastBredAt", this.getLastBredAt());
		compound.setBoolean("castrated", this.castrated);
		compound.setString("geneticTraits", this.getGeneticTraits());
		//compound.setString("affinities", Affinities.encode(this.affinities));
		// compound.setBoolean("companion", this.companion);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("name")) {
			this.name = compound.getString("name");
		}
		this.bred = compound.getInteger("bred");
		setLastBredAt(compound.getLong("lastBredAt"));
		this.castrated = compound.getBoolean("castrated");
		
		this.setHunger(compound.getInteger("hunger"));
		this.updateGeneticTraits(compound.getString("geneticTraits"));
		
		//this.affinities = Affinities.decode(compound.getString("affinities"));
	}

	private void updateGeneticTraits(String geneticTraits) {
		dataWatcher.updateObject(GENETIC_TRAITS, geneticTraits);
	}

	private void addGeneticTraits(String geneticTraits) {
		dataWatcher.addObject(GENETIC_TRAITS, geneticTraits);
	}

	public EntityScientific(World par1World) {
		super(par1World);
		this.setSize(1.0F, 0.5F);
		setupAI();
		this.setCustomNameTag(HashUUID.encode(getUniqueID()));
	}

	protected void entityInit() {
		super.entityInit();
		
		//this.initializeWild();
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (this.getGrowingAge() < -this.getAdultThreshold()) {
			this.setGrowingAge(-this.getAdultThreshold());
		} else if (this.isOld() || this.getHunger() <= 0) {
			spawnClouds(1);
			this.damageEntity(DamageSource.starve, 1000);
		} else if (getCurrentAge() % (isAdult() ? 30 : 60) == 0) {
			this.setHunger(Math.max(0,  this.getHunger()-1));
		}
		
		EntityPlayer player = this.worldObj.getClosestPlayerToEntity(
				this, this.CLOSE_DISTANCE*2);
		if (!panicking && player != null && player.isEntityAlive()) {
			if (CLOSE_DISTANCE >= this.getDistanceToEntity(player)) {
				//player.attackEntityAsMob(this);
				//this.attackEntityAsMob(player);
				float angle = (float) Math.toDegrees(Math.atan2(player.posZ
						- posZ, player.posX - posX));
				if (angle < 0) {
					angle += 360;
				}
				if (rotationYaw < 0) {
					rotationYaw += 360;
				}
				
				float diff = Math.abs(angle - Math.abs(rotationYaw));
				
				if (diff <= 45) {
					// Approaching from front
					this.panicking = true;
				} else if (diff >= 135) {
					player.attackEntityFrom(DamageSource.causeMobDamage(this), 0.1f);
					this.panicking = true;
				}
			}
		}
	}

	public abstract boolean isOld();

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	// set up AI tasks
	protected void setupAI() {
		getNavigator().setAvoidsWater(true);
		clearAITasks(); // clear any tasks assigned in super classes
		tasks.addTask(0, new EntityAISwimming(this));
		// tasks.addTask(1, new RealisticAnimalAI(this));
		// the leap and the collide together form an actual attack
		/*tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
		tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, true));
		tasks.addTask(5, new EntityAIMate(this, 1.0D));
		tasks.addTask(6, new EntityAITempt(this, 1.25D, Items.wheat, false));
		tasks.addTask(7, new EntityAIFollowParent(this, 1.25D));
		tasks.addTask(8, new EntityAIWander(this, 1.0D));
		tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class,
				6.0F));
		tasks.addTask(10, new EntityAILookIdle(this));*/
	}

	protected void clearAITasks() {
		tasks.taskEntries.clear();
		targetTasks.taskEntries.clear();
	}


	protected void initializeWild() {
		dataWatcher.addObject(HUNGER, 99);
		dataWatcher.addObject(LAST_BRED_AT, ""+worldObj.getTotalWorldTime());
		setBred(0);
		setAffinities(new HashMap<String, Integer>());
		setCastrated(false);
		setName("No name");
	}

	private void setLastBredAt(long time) {
		dataWatcher.updateObject(LAST_BRED_AT, ""+time);
	}

	private void setName(String name) {
		this.name = name;
	}
	
	public void addGeneticTraits(int meatQuantity, int feedEfficiency,
			int domestication, int potentialForProduction, Gender gender,
			String lineage, long birthdate) {
		meatQuantity = Math.max(Math.min(meatQuantity, 9), 0);
		feedEfficiency = Math.max(Math.min(feedEfficiency, 9), 0);
		domestication = Math.max(Math.min(domestication, 9), 0);
		potentialForProduction = Math.max(Math.min(potentialForProduction, 9),
				0);
		addGeneticTraits("" + meatQuantity + feedEfficiency + domestication
				+ potentialForProduction + gender.shortEncode() + lineage + ","
				+ birthdate);
	}

	public int getHunger() {
		return dataWatcher.getWatchableObjectInt(HUNGER);
	}

	public void setHunger(int hunger) {
		dataWatcher.updateObject(HUNGER, hunger);
	}

	public int getMeatQuantity() {
		return Character.getNumericValue(getGeneticTraits().charAt(0));
	}

	public int getFeedEfficiency() {
		return Character.getNumericValue(getGeneticTraits().charAt(1));
	}

	public int getDomestication() {
		return Character.getNumericValue(getGeneticTraits().charAt(2));
	}

	public int getPotentialForProduction() {
		return Character.getNumericValue(getGeneticTraits().charAt(3));
	}

	public Gender getGender() {
		return Gender.shortDecode(getGeneticTraits().charAt(4));
	}

	public int getBred() {
		return bred;
	}

	public void setBred(int bred) {
		this.bred = bred;
	}

	public String getLineage() {
		String geneticTraits = getGeneticTraits();
		int finalComma = geneticTraits.lastIndexOf(',');
		return geneticTraits.substring(5, finalComma);
	}

	public Map<String, Integer> getAffinities() {
		return affinities;
	}

	public void setAffinities(Map<String, Integer> affinities) {
		this.affinities = affinities;
	}

	public boolean isCastrated() {
		return castrated;
	}

	public void setCastrated(boolean castrated) {
		this.castrated = castrated;
	}

	public void face(Entity entity) {
		double xDiff = entity.posX - this.posX;
		double zDiff = entity.posZ - this.posZ;
		this.rotationYaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
	}

	public boolean canMateWith(EntityScientific other) {
		if (other.getClass() != this.getClass()) {
			return false;
		}
		if (other.getGender() != this.getGender()) {
			if (other.canBreed() && this.canBreed()) {
				return true;
			}
		}
		return false;
	}

	private boolean canBreed() {
		if (this.getStress() <= 2 || !this.isAdult()) {
			return false;
		}
		if (this.getGender() == Gender.male) {
			return !this.castrated;
		} else {
			return true;
		}
	}

	public void procreate() {
		bred += 1;
		setLastBredAt(worldObj.getTotalWorldTime());
	}

	public String getGeneticTraits() {
		return dataWatcher.getWatchableObjectString(GENETIC_TRAITS);
	}

	public long getCurrentAge() {
		String geneticTraits = getGeneticTraits();
		int finalComma = geneticTraits.lastIndexOf(',') + 1;
		long birthdate = Long.parseLong(geneticTraits.substring(finalComma));
		return worldObj.getTotalWorldTime() - birthdate;
	}

	public abstract String getProperName();

	public abstract boolean isAdult();

	/**
	 * Useful function to find the entity with the given UUID
	 * 
	 * @param world
	 * @param id
	 * @return
	 */
	public static Entity findEntityByPersistentID(World world, String id) {
		for (Object o : world.getLoadedEntityList()) {
			Entity e = (Entity) o;
			if (e.getPersistentID().toString().equals(id))
				return e;
		}
		return null;
	}

	public float getScaling() {
		if (isChild()) {
			return getFatness() +(float)getGrowingAge() / (1.5f * getAdultThreshold());
		} else {
			return getFatness();
		}
	}

	private float getFatness() {
		return 0.75f + (0.25f * 0.1f * getMeatQuantity())
				+ (0.25f * 0.1f * getPotentialForProduction())
				+ (0.25f * 0.01f * getHunger());
	}
	
	public void spawnHearts(int probability) {
		spawnParticles("heart", probability);
	}
	
	public void spawnClouds(int quantity) {
		for (int i = 0; i < quantity; i+= 1) {
			double var3 = this.rand.nextGaussian() * 0.02D;
            double var5 = this.rand.nextGaussian() * 0.02D;
            double var7 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle("crit", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var3, var5, var7);
		}
		
	}
	
	public void spawnParticles(String type, int probability) {
		if (this.rand.nextInt(probability) == 0) {
            double var3 = this.rand.nextGaussian() * 0.02D;
            double var5 = this.rand.nextGaussian() * 0.02D;
            double var7 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(type, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var3, var5, var7);
        }
	}
	
	public boolean inEstrous() {
		return getGender() == Gender.female && isAdult() && (getEstrous() >= 75);
	}

	public abstract int getEstrous();

	/*
	 * Returns the amount of stress this entity is undergoing, from 0-9.
	 * Below 3 and it won't be able to reproduce.
	 */
	public int getStress() {
		float var1 = 5.0F;
        List var2 = worldObj.getEntitiesWithinAABB(EntityScientific.class, this.boundingBox.expand((double)var1, (double)var1, (double)var1));
        if (var2.size() > 8) {
        	return 0;
        } else {
        	return 5;
        }
	}
	
	public int getAdultThreshold() {
		return ADULT_THRESHOLD;
	}
	

	public long getLastBredAt() {
		return Long.parseLong(dataWatcher.getWatchableObjectString(LAST_BRED_AT));
	}

}
