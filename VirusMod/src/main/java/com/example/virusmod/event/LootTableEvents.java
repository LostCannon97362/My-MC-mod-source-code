package com.example.virusmod.event;

import com.example.virusmod.VirusMod;
import com.example.virusmod.block.ModBlocks;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;

public class LootTableEvents {
	public static void register() {
		LootTableEvents.MODIFY.register(LootTableEvents::modifyLootTable);
	}

	private static void modifyLootTable(Identifier id, LootManager.LootTableGetter getter, LootTableEvents.LootTableModificationContext context) {
		// Apply custom loot tables to our ore blocks
		if (id.equals(ModBlocks.NFC_ORE.getLootTableId())) {
			LootTable table = getter.getLootTable(new Identifier(VirusMod.MOD_ID, "blocks/nfc_ore"));
			context.modify((lootTable) -> table);
		}
		if (id.equals(ModBlocks.DEEPSLATE_NFC_ORE.getLootTableId())) {
			LootTable table = getter.getLootTable(new Identifier(VirusMod.MOD_ID, "blocks/deepslate_nfc_ore"));
			context.modify((lootTable) -> table);
		}
	}
}
