package com.example.virusmod.status_effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.LivingEntity;

public class ZombieVirusEffect extends StatusEffect {
	public ZombieVirusEffect() {
		super(StatusEffectCategory.HARMFUL, 0x00AA00);  // Green color
		// Lower max HP to 5 hearts (10 health points)
		this.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, 
			"3cf9b638-e1b1-4aa8-a8f4-76fd8f73f16h", -10.0, EntityAttributeModifier.Operation.ADDITION);
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		// Apply slowness V and weakness V
		if (entity.getWorld().getTime() % 10 == 0) {
			entity.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(
				StatusEffects.SLOWNESS, 
				20,  // Duration: 1 second, will be reapplied frequently
				4,   // Level V (0-indexed, so 4 = V)
				false, 
				false
			));
			entity.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(
				StatusEffects.WEAKNESS, 
				20,  // Duration: 1 second, will be reapplied frequently
				4,   // Level V (0-indexed, so 4 = V)
				false, 
				false
			));
		}
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}
}
