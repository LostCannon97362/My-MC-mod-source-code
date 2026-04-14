package com.example.virusmod.item;

import com.example.virusmod.status_effect.ModStatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.entity.effect.StatusEffectInstance;

public class NFCGemItem extends Item {
	public NFCGemItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);

		if (!world.isClient) {
			// Apply hypnotize virus to the player
			// 999 days = 999 * 24 * 60 * 60 * 20 ticks = 1,727,272,000 ticks
			player.addStatusEffect(new StatusEffectInstance(
				ModStatusEffects.HYPNOTIZE_VIRUS,
				1727272000,  // 999 IRL days
				0,
				false,
				true
			));

			// Play sound effect
			player.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);

			// Consume the item
			stack.decrement(1);
		}

		player.incrementStat(Stats.USED.getOrCreateStat(this));
		return ActionResult.success(world.isClient);
	}
}
