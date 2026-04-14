package com.example.virusmod.status_effect;

import com.example.virusmod.VirusMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
	public static StatusEffect COMMON_COLD = new CommonColdEffect();
	public static StatusEffect FLU = new FluEffect();
	public static StatusEffect ZOMBIE_VIRUS = new ZombieVirusEffect();
	public static StatusEffect HYPNOTIZE_VIRUS = new HypnotizeVirusEffect();
	public static StatusEffect LOVE_VIRUS = new LoveVirusEffect();

	public static void register() {
		registerStatusEffect("common_cold", COMMON_COLD);
		registerStatusEffect("flu", FLU);
		registerStatusEffect("zombie_virus", ZOMBIE_VIRUS);
		registerStatusEffect("hypnotize_virus", HYPNOTIZE_VIRUS);
		registerStatusEffect("love_virus", LOVE_VIRUS);
		VirusMod.LOGGER.info("Status effects registered!");
	}

	private static StatusEffect registerStatusEffect(String name, StatusEffect statusEffect) {
		return Registry.register(Registries.STATUS_EFFECT, new Identifier(VirusMod.MOD_ID, name), statusEffect);
	}
}
