package com.example.virusmod.event;

import com.example.virusmod.status_effect.ModStatusEffects;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.text.Text;
import net.minecraft.server.world.ServerWorld;
import java.util.List;

public class LoveVirusEvents {
	public static void register() {
		UseEntityCallback.EVENT.register(LoveVirusEvents::onUseEntity);
		ServerTickEvents.END_SERVER_TICK.register(LoveVirusEvents::onServerTick);
	}

	private static ActionResult onUseEntity(PlayerEntity player, net.minecraft.world.World world, Hand hand, net.minecraft.entity.Entity entity, net.minecraft.util.hit.EntityHitResult hitResult) {
		// Check if player has love virus
		if (!player.hasStatusEffect(ModStatusEffects.LOVE_VIRUS)) {
			return ActionResult.PASS;
		}

		// Check if entity is a villager
		if (entity instanceof VillagerEntity villager) {
			// Check if villager already has love virus
			if (villager.hasStatusEffect(ModStatusEffects.LOVE_VIRUS)) {
				player.sendMessage(Text.literal("§dThis villager is already in love with you!"), false);
				return ActionResult.SUCCESS;
			}

			// Apply love virus to villager with amplifier 0 (cannot spread)
			villager.addStatusEffect(new StatusEffectInstance(
				ModStatusEffects.LOVE_VIRUS,
				12000,  // 600 seconds (10 minutes)
				0,      // Amplifier 0 = cannot spread (from player)
				false,
				false
			));

			// Make villager follow the player (compatible with Lithium AI optimizations)
			if (!villager.getNavigation().isFollowingPath()) {
				villager.getNavigation().startMovingTo(player.getX(), player.getY(), player.getZ(), 1.0);
			}

			// Play heart particles (optimized for Sodium compatibility)
			if (world.isClient) {
				for (int i = 0; i < 6; i++) { // Reduced particle count for better performance
					double offsetX = (Math.random() - 0.5) * 1.5;
					double offsetY = Math.random() * 1.2;
					double offsetZ = (Math.random() - 0.5) * 1.5;
					world.addParticle(net.minecraft.particle.ParticleTypes.HEART,
						villager.getX() + offsetX,
						villager.getY() + 1 + offsetY,
						villager.getZ() + offsetZ,
						0, 0, 0
					);
				}
			}

			player.sendMessage(Text.literal("§dThe villager has fallen in love with you! (Cannot spread virus)"), false);
			return ActionResult.SUCCESS;
		}

		return ActionResult.PASS;
	}

	private static void onServerTick(net.minecraft.server.MinecraftServer server) {
		// Only process every 20 ticks (1 second) for better performance with Lithium
		if (server.getTicks() % 20 != 0) return;

		for (ServerWorld world : server.getWorlds()) {
			List<VillagerEntity> villagers = world.getEntitiesByClass(
				VillagerEntity.class,
				entity -> entity.hasStatusEffect(ModStatusEffects.LOVE_VIRUS),
				java.util.Collections.emptyList()
			);

			for (VillagerEntity infectedVillager : villagers) {
				// Only spread if infected by love gas (amplifier 1)
				if (canSpreadVirus(infectedVillager)) {
					spreadLoveVirus(infectedVillager, villagers);
				}
			}
		}
	}

	private static boolean canSpreadVirus(VillagerEntity villager) {
		StatusEffectInstance effect = villager.getStatusEffect(ModStatusEffects.LOVE_VIRUS);
		return effect != null && effect.getAmplifier() >= 1;
	}

	private static void spreadLoveVirus(VillagerEntity infected, List<VillagerEntity> nearbyVillagers) {
		for (VillagerEntity target : nearbyVillagers) {
			// Don't infect self
			if (target == infected) continue;

			// Check distance (5 blocks)
			if (infected.distanceTo(target) <= 5.0) {
				// 10% chance per tick to infect nearby villagers if they don't have the virus
				if (!target.hasStatusEffect(ModStatusEffects.LOVE_VIRUS) && infected.getRandom().nextFloat() < 0.1f) {
					target.addStatusEffect(new StatusEffectInstance(
						ModStatusEffects.LOVE_VIRUS,
						12000,  // 600 seconds (10 minutes)
						0,
						false,
						false
					));

					// Make the newly infected villager follow the original infected villager
					target.getNavigation().startMovingTo(infected.getX(), infected.getY(), infected.getZ(), 1.0);

					// Play heart particles
					for (int i = 0; i < 5; i++) {
						double offsetX = (Math.random() - 0.5) * 2;
						double offsetY = Math.random() * 2;
						double offsetZ = (Math.random() - 0.5) * 2;
						infected.getWorld().addParticle(net.minecraft.particle.ParticleTypes.HEART,
							target.getX() + offsetX,
							target.getY() + 1 + offsetY,
							target.getZ() + offsetZ,
							0, 0, 0
						);
					}
				}
			}
		}
	}
}
