package com.example.virusmod.status_effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class HypnotizeVirusEffect extends StatusEffect {
	public HypnotizeVirusEffect() {
		super(StatusEffectCategory.BENEFICIAL, 0xFF00FF);  // Magenta/purple color
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return false;  // No passive effects, just the visual indicator
	}
}
