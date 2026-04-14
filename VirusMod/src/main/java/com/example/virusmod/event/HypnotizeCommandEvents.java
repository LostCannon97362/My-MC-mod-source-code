package com.example.virusmod.event;

import com.example.virusmod.status_effect.ModStatusEffects;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import java.util.ArrayList;
import java.util.List;

public class HypnotizeCommandEvents {
	public static void register() {
		ServerMessageEvents.CHAT_MESSAGE.register(HypnotizeCommandEvents::onChatMessage);
	}

	private static void onChatMessage(net.minecraft.server.filter.FilteredMessage message, ServerPlayerEntity player, net.minecraft.server.network.ServerPlayNetworkHandler handler) {
		String chat = message.raw();
		
		// Check if player has hypnotize virus
		if (!player.hasStatusEffect(ModStatusEffects.HYPNOTIZE_VIRUS)) {
			return;
		}

		// Get the world and find all animals near the player
		List<AnimalEntity> nearbyAnimals = player.getWorld().getEntitiesByClass(
			AnimalEntity.class,
			player.getBoundingBox().expand(50.0),
			animal -> hasHypnotizeEffect(animal) && isOwner(player, animal)
		);

		// Get nearby Wardens
		List<net.minecraft.entity.mob.WardenEntity> nearbyWardens = player.getWorld().getEntitiesByClass(
			net.minecraft.entity.mob.WardenEntity.class,
			player.getBoundingBox().expand(50.0),
			warden -> hasHypnotizeEffect(warden)
		);

		// Combine into one list for processing
		List<LivingEntity> allHypnotized = new ArrayList<>();
		allHypnotized.addAll(nearbyAnimals);
		allHypnotized.addAll(nearbyWardens);

		// Filter to only entities the player is looking at
		List<LivingEntity> lookedAtEntities = new ArrayList<>();
		for (LivingEntity entity : allHypnotized) {
			if (isPlayerLookingAt(player, entity)) {
				lookedAtEntities.add(entity);
			}
		}

		if (lookedAtEntities.isEmpty()) {
			player.sendMessage(Text.literal("§cYou must look at the hypnotized creatures!"), false);
			return;
		}

		// Parse command from chat
		String command = chat.toLowerCase().trim();

		if (command.startsWith("sit") || command.startsWith("stay")) {
			for (LivingEntity entity : lookedAtEntities) {
				entity.getNavigation().stop();
				// Mark animal as sitting if possible
				if (entity instanceof net.minecraft.entity.passive.TameableEntity tameable) {
					tameable.setSitting(true);
				}
			}
			player.sendMessage(Text.literal("§6Hypnotized creatures: Sitting!"), false);

		} else if (command.startsWith("follow") || command.startsWith("come")) {
			for (LivingEntity entity : lookedAtEntities) {
				entity.getNavigation().startMovingTo(player.getX(), player.getY(), player.getZ(), 1.0);
				if (entity instanceof net.minecraft.entity.passive.TameableEntity tameable) {
					tameable.setSitting(false);
				}
			}
			player.sendMessage(Text.literal("§6Hypnotized creatures: Following you!"), false);

		} else if (command.startsWith("attack")) {
			// Find nearby hostile mobs and make creatures attack them
			List<net.minecraft.entity.mob.HostileEntity> hostiles = player.getWorld().getEntitiesByClass(
				net.minecraft.entity.mob.HostileEntity.class,
				player.getBoundingBox().expand(30.0),
				hostile -> true
			);

			if (!hostiles.isEmpty()) {
				net.minecraft.entity.mob.HostileEntity target = hostiles.get(0);
				for (LivingEntity entity : lookedAtEntities) {
					if (entity instanceof net.minecraft.entity.passive.TameableEntity tameable) {
						tameable.setSitting(false);
						tameable.setTarget(target);
					}
					// Wardens can also attack
					if (entity instanceof net.minecraft.entity.mob.WardenEntity warden) {
						warden.setTarget(target);
					}
				}
				player.sendMessage(Text.literal("§6Hypnotized creatures: Attacking!"), false);
			} else {
				player.sendMessage(Text.literal("§cNo enemies nearby!"), false);
			}

		} else if (command.startsWith("relax") || command.startsWith("free")) {
			for (LivingEntity entity : lookedAtEntities) {
				entity.getNavigation().stop();
				if (entity instanceof net.minecraft.entity.passive.TameableEntity tameable) {
					tameable.setSitting(false);
				}
				// Clear Warden target
				if (entity instanceof net.minecraft.entity.mob.WardenEntity warden) {
					warden.setTarget(null);
				}
			}
			player.sendMessage(Text.literal("§6Hypnotized creatures: Relaxing!"), false);
		}
	}

	private static boolean hasHypnotizeEffect(AnimalEntity animal) {
		return animal.hasStatusEffect(ModStatusEffects.HYPNOTIZE_VIRUS);
	}

	private static boolean isOwner(PlayerEntity owner, AnimalEntity animal) {
		// Check if animal is owned by this player
		if (animal instanceof net.minecraft.entity.passive.TameableEntity tameable) {
			return tameable.getOwner() == owner;
		}
		return true;  // Non-tameable animals can be controlled by all players
	}

	private static boolean isPlayerLookingAt(PlayerEntity player, AnimalEntity animal) {
		// Get the player's looking direction
		net.minecraft.math.Vec3d cameraPos = player.getCameraPos();
		net.minecraft.math.Vec3d direction = player.getRotationVec(1.0f).normalize();
		
		// Raycast distance
		double rayDistance = 50.0;
		net.minecraft.math.Vec3d endPos = cameraPos.add(direction.x * rayDistance, direction.y * rayDistance, direction.z * rayDistance);
		
		RaycastContext context = new RaycastContext(cameraPos, endPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player);
		HitResult hit = player.getWorld().raycast(context);
		
		// Check if the raycast hit the animal
		return hit.getType() == HitResult.Type.MISS || 
		       (hit.getType() != HitResult.Type.MISS && 
		        player.getWorld().getBlockCollisions(player, animal.getBoundingBox()).iterator().hasNext());
	}
}
