package com.example.virusmod.item;

import com.example.virusmod.status_effect.ModStatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.Text;

public class LoveGemItem extends Item {
	public LoveGemItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (entity instanceof VillagerEntity villager) {
			// Check if villager already has love virus
			if (villager.hasStatusEffect(ModStatusEffects.LOVE_VIRUS)) {
				user.sendMessage(Text.literal("§dThis villager is already in love!"), false);
				return ActionResult.FAIL;
			}

			// Apply love virus to villager with amplifier 0 (cannot spread)
			villager.addStatusEffect(new StatusEffectInstance(
				ModStatusEffects.LOVE_VIRUS,
				12000,  // 600 seconds (10 minutes)
				0,      // Amplifier 0 = cannot spread (from player)
				false,
				false
			));

			// Make villager follow the player (compatible with Lithium AI optimizations)
			if (!villager.getNavigation().isFollowingPath()) {
				villager.getNavigation().startMovingTo(user.getX(), user.getY(), user.getZ(), 1.0);
			}

			// Play heart particles (optimized for Sodium compatibility)
			if (villager.getWorld().isClient) {
				for (int i = 0; i < 8; i++) { // Reduced particle count for better performance
					double offsetX = (Math.random() - 0.5) * 2;
					double offsetY = Math.random() * 1.5;
					double offsetZ = (Math.random() - 0.5) * 2;
					villager.getWorld().addParticle(net.minecraft.particle.ParticleTypes.HEART,
						villager.getX() + offsetX,
						villager.getY() + 1 + offsetY,
						villager.getZ() + offsetZ,
						0, 0, 0
					);
				}
			}

			// Play sound
			villager.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);

			user.sendMessage(Text.literal("§dThe villager has fallen in love with you!"), false);

			// Consume the item
			stack.decrement(1);
			user.incrementStat(Stats.USED.getOrCreateStat(this));

			return ActionResult.SUCCESS;
		}

		return ActionResult.PASS;
	}

	@Override
	public ActionResult use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);

		if (!world.isClient) {
			// Apply love virus to the player
			player.addStatusEffect(new StatusEffectInstance(
				ModStatusEffects.LOVE_VIRUS,
				1727272000,  // 999 IRL days (same as hypnotize virus)
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
