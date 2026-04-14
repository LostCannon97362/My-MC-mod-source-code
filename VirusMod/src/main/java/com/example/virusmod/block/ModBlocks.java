package com.example.virusmod.block;

import com.example.virusmod.VirusMod;
import com.example.virusmod.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
	public static Block NFC_ORE = new Block(AbstractBlock.Settings.copy(Blocks.STONE)
		.hardness(3.0f)
		.resistance(3.0f)
	);

	public static Block DEEPSLATE_NFC_ORE = new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE)
		.hardness(4.5f)
		.resistance(3.0f)
	);

	public static Block LOVE_ORE = new Block(AbstractBlock.Settings.copy(Blocks.STONE)
		.hardness(3.0f)
		.resistance(3.0f)
	);

	public static Block DEEPSLATE_LOVE_ORE = new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE)
		.hardness(4.5f)
		.resistance(3.0f)
	);

	public static void register() {
		registerBlock("nfc_ore", NFC_ORE);
		registerBlock("deepslate_nfc_ore", DEEPSLATE_NFC_ORE);
		registerBlock("love_ore", LOVE_ORE);
		registerBlock("deepslate_love_ore", DEEPSLATE_LOVE_ORE);
		VirusMod.LOGGER.info("Blocks registered!");
	}

	private static Block registerBlock(String name, Block block) {
		Registry.register(Registries.BLOCK, new Identifier(VirusMod.MOD_ID, name), block);
		Registry.register(Registries.ITEM, new Identifier(VirusMod.MOD_ID, name),
			new BlockItem(block, new Item.Settings()));
		return block;
	}
}
