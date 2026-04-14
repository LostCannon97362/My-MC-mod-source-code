package com.example.virusmod.status_effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CommonColdEffect extends StatusEffect {
	public CommonColdEffect() {
		super(StatusEffectCategory.HARMFUL, 0xB0D0FF);
		this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, 
			"0cf9b638-e1b1-4aa8-a8f4-76fd8f73f16e", -0.2, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public void applyUpdateEffect(net.minecraft.entity.LivingEntity entity, int amplifier) {
		// Cause occasional damage and slowness
		if (entity.getWorld().getTime() % 40 == 0) {
			entity.damage(entity.getWorld().getDamageSources().generic(), 0.5f);
		}
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}
}
