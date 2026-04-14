package com.example.virusmod.item;

import com.example.virusmod.VirusMod;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
	public static Item NFC_GEM = new NFCGemItem(new Item.Settings());
	public static Item HENCHMEN_SUMMONER = new HenchmenSummonerItem(new Item.Settings().maxCount(1));
	public static Item LOVE_GEM = new LoveGemItem(new Item.Settings());
	public static Item LOVE_GAS = new LoveGasItem(new Item.Settings());

	public static void register() {
		registerItem("nfc_gem", NFC_GEM);
		registerItem("henchmen_summoner", HENCHMEN_SUMMONER);
		registerItem("love_gem", LOVE_GEM);
		registerItem("love_gas", LOVE_GAS);
		VirusMod.LOGGER.info("Items registered!");
	}

	private static Item registerItem(String name, Item item) {
		return Registry.register(Registries.ITEM, new Identifier(VirusMod.MOD_ID, name), item);
	}
}
