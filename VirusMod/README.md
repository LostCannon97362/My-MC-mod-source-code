# Virus Mod

A Minecraft Fabric mod that adds realistic disease mechanics to the game, including the common cold and flu.

## Features

- **Common Cold**: A mild virus that reduces movement speed slightly
- **Flu**: A more severe virus that reduces movement speed significantly and decreases attack damage
- **Player-to-Player Transmission**: Viruses spread between players who are within 5 blocks of each other
- **Zombie Infection**: Zombies can infect players with a 10% chance when they attack
- **Realistic Effects**: Infected players take damage over time and experience movement penalties

## Compatibility

This mod is compatible with performance optimization mods:

- **Sodium**: Rendering optimizations - fully compatible
- **Lithium**: Server-side optimizations - optimized for compatibility
- **Other Fabric mods**: Should work with most Fabric mods

## Performance Notes

- Virus spreading checks run every 20 ticks (1 second) to minimize performance impact
- AI modifications only occur when villagers aren't already following a path
- Particle effects are optimized for Sodium compatibility

## Usage

### Testing the Mod

You can use the command `/effect @s virusmod:common_cold 300 0` to apply the common cold effect.

Replace `common_cold` with `flu` for the flu effect.

### Duration

- Common Cold: Lasts 300 seconds (5 minutes) by default
- Flu: Lasts 450 seconds (7.5 minutes) by default

### Player-to-Player Virus Spread

When an infected player is within 5 blocks of a healthy player, there's a 5% chance per tick to transmit the virus.

### Zombie Infection

When a zombie attacks a player, there's a **10% chance** to infect them with either:
- **Common Cold** (50% chance)
- **Flu** (50% chance)

Players who are already infected cannot be reinfected.

## Effects

### Common Cold
- ❄️ -20% Movement Speed
- 🩹 Deals 0.5 damage every 2 seconds

### Flu
- ❄️❄️ -35% Movement Speed
- ⚔️ -2 Attack Damage
- 🩹 Deals 1.0 damage every 1 second

## Building

Run `./gradlew build` to build the mod. The JAR file will be in `build/libs/`.

## License

MIT License
