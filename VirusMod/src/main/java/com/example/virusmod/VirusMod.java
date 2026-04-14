package com.example.virusmod;

import com.example.virusmod.status_effect.ModStatusEffects;
import com.example.virusmod.item.ModItems;
import com.example.virusmod.block.ModBlocks;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VirusMod implements ModInitializer {
	public static final String MOD_ID = "virusmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Virus Mod!");
		ModStatusEffects.register();
		ModItems.register();
		ModBlocks.register();
	}
}
