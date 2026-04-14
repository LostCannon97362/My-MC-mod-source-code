package com.example.virusmod.event;

import com.example.virusmod.status_effect.ModStatusEffects;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.mob.WardenEntity;

public class HypnotizeVirusEvents {
	public static void register() {
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(HypnotizeVirusEvents::onLivingEntityDamage);
	}

	private static boolean onLivingEntityDamage(LivingEntity entity, net.minecraft.entity.damage.DamageSource source, float amount) {
		// Check if the attacker is a player
		if (source.getAttacker() instanceof PlayerEntity player) {
			// Check if the player has the hypnotize virus
			if (player.hasStatusEffect(ModStatusEffects.HYPNOTIZE_VIRUS)) {
				// Hypnotize animals
				if (entity instanceof AnimalEntity animal) {
					animal.addStatusEffect(new StatusEffectInstance(
						ModStatusEffects.HYPNOTIZE_VIRUS,
						8000,
						0,
						false,
						false
					));
					
					// Make the animal passive and make it follow/sit
					if (animal instanceof PassiveEntity passiveEntity) {
						passiveEntity.getNavigation().stop();
						// Try to tame it if it's tameable
						if (animal instanceof net.minecraft.entity.passive.TameableEntity tameable) {
							tameable.setOwner(player);
						}
					}
				}
				// Hypnotize Warden
				else if (entity instanceof WardenEntity warden) {
					warden.addStatusEffect(new StatusEffectInstance(
						ModStatusEffects.HYPNOTIZE_VIRUS,
						8000,
						0,
						false,
						false
					));
					
					// Make the Warden passive
					warden.getNavigation().stop();
					warden.setTarget(null);
				}
			}
		}
		
		// Allow the damage to go through
		return true;
	}
}

