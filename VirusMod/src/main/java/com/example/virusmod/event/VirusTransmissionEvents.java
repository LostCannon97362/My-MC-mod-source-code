package com.example.virusmod.event;

import com.example.virusmod.status_effect.ModStatusEffects;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.player.PlayerEntity;
import java.util.List;

public class VirusTransmissionEvents {
	public static void register() {
		ServerTickEvents.END_SERVER_TICK.register(VirusTransmissionEvents::onServerTick);
	}

	private static void onServerTick(net.minecraft.server.MinecraftServer server) {
		for (ServerWorld world : server.getWorlds()) {
			List<PlayerEntity> players = world.getPlayers(p -> true);
			
			for (PlayerEntity player : players) {
				// Check if player has any virus
				if (hasVirus(player)) {
					spreadVirus(player, players);
				}
			}
		}
	}

	private static boolean hasVirus(PlayerEntity player) {
		return player.hasStatusEffect(ModStatusEffects.COMMON_COLD) || 
		       player.hasStatusEffect(ModStatusEffects.FLU);
	}

	private static void spreadVirus(PlayerEntity infected, List<PlayerEntity> nearbyPlayers) {
		for (PlayerEntity target : nearbyPlayers) {
			// Don't infect self
			if (target == infected) continue;
			
			// Check distance (5 blocks)
			if (infected.distanceTo(target) <= 5.0) {
				// 5% chance per tick to infect nearby players if they don't have immunity
				if (!hasVirus(target) && infected.getRandom().nextFloat() < 0.05f) {
					// Determine which virus to spread
					if (infected.hasStatusEffect(ModStatusEffects.COMMON_COLD)) {
						target.addStatusEffect(new StatusEffectInstance(
							ModStatusEffects.COMMON_COLD, 
							6000, // Duration in ticks (300 seconds)
							0, 
							false, 
							true
						));
					} else if (infected.hasStatusEffect(ModStatusEffects.FLU)) {
						target.addStatusEffect(new StatusEffectInstance(
							ModStatusEffects.FLU, 
							9000, // Duration in ticks (450 seconds)
							0, 
							false, 
							true
						));
					}
				}
			}
		}
	}
}
