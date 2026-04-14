package com.example.virusmod.event;

import com.example.virusmod.status_effect.ModStatusEffects;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class ZombieInfectionEvents {
	public static void register() {
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(ZombieInfectionEvents::onLivingEntityDamage);
	}

	private static boolean onLivingEntityDamage(LivingEntity entity, DamageSource source, float amount) {
		// Check if the entity being damaged is a player
		if (entity instanceof PlayerEntity player) {
			// Check if the damage source is from a zombie
			if (source.getAttacker() instanceof ZombieEntity zombie) {
				// 10% chance to infect (10:90 ratio)
				if (zombie.getRandom().nextFloat() < 0.1f) {
					infectPlayer(player, true);
				}
			}
		}
		
		// Allow the damage to go through
		return true;
	}

	private static void infectPlayer(PlayerEntity player, boolean isFlu) {
		// Don't infect if already infected
		if (player.hasStatusEffect(ModStatusEffects.COMMON_COLD) || 
		    player.hasStatusEffect(ModStatusEffects.FLU) ||
		    player.hasStatusEffect(ModStatusEffects.ZOMBIE_VIRUS)) {
			return;
		}

		// Always apply the zombie virus effect
		player.addStatusEffect(new StatusEffectInstance(
			ModStatusEffects.ZOMBIE_VIRUS,
			12000,  // 600 seconds (10 minutes)
			0,
			false,
			true
		));
	}
}
