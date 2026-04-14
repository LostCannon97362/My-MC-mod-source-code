package com.example.virusmod.item;

import com.example.virusmod.status_effect.ModStatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.text.Text;
import net.minecraft.particle.ParticleTypes;
import java.util.List;

public class HenchmenSummonerItem extends Item {
	public HenchmenSummonerItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult use(World world, PlayerEntity player, Hand hand) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		}

		ItemStack stack = player.getStackInHand(hand);

		// Check if player has hypnotize virus
		if (!player.hasStatusEffect(ModStatusEffects.HYPNOTIZE_VIRUS)) {
			player.sendMessage(Text.literal("§cYou must have the Hypnotize Virus to use this!"), false);
			return ActionResult.FAIL;
		}

		// Get all hypnotized animals and wardens in a large radius
		List<AnimalEntity> hypnotizedAnimals = world.getEntitiesByClass(
			AnimalEntity.class,
			player.getBoundingBox().expand(100.0),
			animal -> hasHypnotizeEffect(animal) && isOwner(player, animal)
		);

		List<WardenEntity> hypnotizedWardens = world.getEntitiesByClass(
			WardenEntity.class,
			player.getBoundingBox().expand(100.0),
			warden -> hasHypnotizeEffect(warden)
		);

		int summonedCount = hypnotizedAnimals.size() + hypnotizedWardens.size();

		if (summonedCount == 0) {
			player.sendMessage(Text.literal("§cNo hypnotized creatures found nearby!"), false);
			return ActionResult.FAIL;
		}

		// Teleport all hypnotized animals to the player
		for (AnimalEntity animal : hypnotizedAnimals) {
			teleportEntity(animal, player);
		}

		// Teleport all hypnotized wardens to the player
		for (WardenEntity warden : hypnotizedWardens) {
			teleportEntity(warden, player);
		}

		// Play sound and particles
		player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
		player.sendMessage(Text.literal("§6Summoned §e" + summonedCount + "§6 hypnotized creatures!"), false);

		// Spawn particles at player location
		for (int i = 0; i < 20; i++) {
			double offsetX = (Math.random() - 0.5) * 2;
			double offsetY = (Math.random() - 0.5) * 2;
			double offsetZ = (Math.random() - 0.5) * 2;
			world.addParticle(ParticleTypes.PORTAL, 
				player.getX() + offsetX, 
				player.getY() + 1 + offsetY, 
				player.getZ() + offsetZ, 
				0, 0, 0
			);
		}

		player.incrementStat(Stats.USED.getOrCreateStat(this));
		return ActionResult.SUCCESS;
	}

	private void teleportEntity(LivingEntity entity, PlayerEntity player) {
		// Teleport entity to player with slight offset to avoid stacking
		double offsetX = (Math.random() - 0.5) * 3;
		double offsetZ = (Math.random() - 0.5) * 3;
		
		entity.teleport(
			player.getX() + offsetX,
			player.getY(),
			player.getZ() + offsetZ
		);

		// Play teleport sound at entity location
		entity.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

		// Stop their movement
		entity.getNavigation().stop();
	}

	private boolean hasHypnotizeEffect(LivingEntity entity) {
		return entity.hasStatusEffect(ModStatusEffects.HYPNOTIZE_VIRUS);
	}

	private boolean isOwner(PlayerEntity owner, AnimalEntity animal) {
		// Check if animal is owned by this player
		if (animal instanceof net.minecraft.entity.passive.TameableEntity tameable) {
			return tameable.getOwner() == owner;
		}
		return true;  // Non-tameable animals can be controlled by all players
	}
}
