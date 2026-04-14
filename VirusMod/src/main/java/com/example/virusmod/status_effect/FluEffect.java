package com.example.virusmod.status_effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FluEffect extends StatusEffect {
	public FluEffect() {
		super(StatusEffectCategory.HARMFUL, 0xFF6B6B);
		this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, 
			"1cf9b638-e1b1-4aa8-a8f4-76fd8f73f16f", -0.35, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
		this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, 
			"2cf9b638-e1b1-4aa8-a8f4-76fd8f73f16g", -2.0, EntityAttributeModifier.Operation.ADDITION);
	}

	@Override
	public void applyUpdateEffect(net.minecraft.entity.LivingEntity entity, int amplifier) {
		// Cause more damage and more frequent damage ticks
		if (entity.getWorld().getTime() % 20 == 0) {
			entity.damage(entity.getWorld().getDamageSources().generic(), 1.0f);
		}
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}
}
