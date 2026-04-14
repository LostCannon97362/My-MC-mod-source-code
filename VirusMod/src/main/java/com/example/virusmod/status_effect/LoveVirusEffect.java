package com.example.virusmod.status_effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class LoveVirusEffect extends StatusEffect {
	public LoveVirusEffect() {
		super(StatusEffectCategory.BENEFICIAL, 0xFF69B4);  // Pink color
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return false;  // No passive effects, just the visual indicator
	}
}
