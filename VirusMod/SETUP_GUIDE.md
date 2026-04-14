# Virus Mod - Minecraft Fabric Mod

## Project Structure

```
VirusMod/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/virusmod/
│       │       ├── VirusMod.java                 # Main mod class
│       │       ├── VirusModServer.java           # Server event handler
│       │       ├── status_effect/
│       │       │   ├── CommonColdEffect.java     # Common cold virus effect
│       │       │   ├── FluEffect.java            # Flu virus effect
│       │       │   └── ModStatusEffects.java     # Status effect registry
│       │       └── event/
│       │           ├── VirusTransmissionEvents.java   # Player-to-player spread logic
│       │           └── ZombieInfectionEvents.java     # Zombie infection system
│       └── resources/
│           ├── fabric.mod.json                   # Mod metadata
│           ├── virusmod.mixins.json              # Mixin configuration
│           └── assets/virusmod/
│               ├── lang/
│               │   └── en_us.json                # English translations
│               └── textures/                     # Texture assets
├── build.gradle                                  # Gradle build configuration
├── gradle.properties                             # Gradle properties
├── settings.gradle                               # Gradle settings
├── build.bat                                     # Windows build script
├── build.sh                                      # Linux/Mac build script
├── README.md                                     # Mod documentation
└── LICENSE                                       # MIT License

```

## How to Build

### Prerequisites
- Java 21 or later
- Gradle (included via gradlew)

### Build Commands

**Windows:**
```batch
build.bat
```

**Linux/Mac:**
```bash
chmod +x build.sh
./build.sh
```

**Manual (All platforms):**
```bash
gradlew build
```

The compiled JAR will be located in: `build/libs/virusmod-1.0.0.jar`

## How to Install

1. Install Fabric Loader: https://fabricmc.net/use/
2. Create/access your Fabric Minecraft instance
3. Copy the compiled JAR to the `mods` folder
4. Launch Minecraft with Fabric

## Virus System Details

### Common Cold
- **Duration:** 300 seconds (5 minutes)
- **Effects:**
  - Reduces movement speed by 20%
  - Deals 0.5 damage every 2 seconds
  - Color indicator: Light Blue (0xB0D0FF)

### Flu
- **Duration:** 450 seconds (7.5 minutes)
- **Effects:**
  - Reduces movement speed by 35%
  - Reduces attack damage by 2
  - Deals 1.0 damage every 1 second
  - Color indicator: Red (0xFF6B6B)

### Transmission
- Range: 5 blocks
- Transmission chance: 5% per tick per nearby player
- Prevents reinfection while virus is active

### Zombie Infection
- Activation: When a zombie attacks a player
- Infection chance: 10% per hit
- Virus type: 50% Common Cold, 50% Flu
- Prevents reinfection while virus is active

## Testing Commands

Apply viruses with these commands in-game (requires cheats enabled):

```
/effect @s virusmod:common_cold 300 0
/effect @s virusmod:flu 450 0
/effect @s clear  # Remove all effects
```

Replace `@s` with a player name to affect other players:
```
/effect @p virusmod:common_cold 300 0  # Nearest player
/effect @a virusmod:flu 450 0          # All players
```

## Modifications & Customization

### Change Virus Duration
Edit [event/VirusTransmissionEvents.java](event/VirusTransmissionEvents.java):
- Line 43: Change `6000` for Common Cold duration (in ticks: 1 tick = 0.05 seconds)
- Line 50: Change `9000` for Flu duration

### Adjust Virus Effects
Edit the status effect classes:
- [status_effect/CommonColdEffect.java](status_effect/CommonColdEffect.java)
- [status_effect/FluEffect.java](status_effect/FluEffect.java)

Modify the `addAttributeModifier()` calls to change movement speed and damage reductions.

### Change Transmission Range
Edit [event/VirusTransmissionEvents.java](event/VirusTransmissionEvents.java) line 38:
```java
if (infected.distanceTo(target) <= 5.0) {  // Change 5.0 to desired range
```

### Change Zombie Infection Chance
Edit [event/ZombieInfectionEvents.java](event/ZombieInfectionEvents.java) line 25:
```java
if (zombie.getRandom().nextFloat() < 0.1f) {  // Change 0.1f (currently 10%)
```

### Change Transmission Probability
Edit [event/VirusTransmissionEvents.java](event/VirusTransmissionEvents.java) line 42:
```java
if (!hasVirus(target) && infected.getRandom().nextFloat() < 0.05f) {  // Change 0.05f
```

## Compatibility

- **Minecraft Version:** 1.20.1
- **Fabric Loader:** ≥ 0.15.0
- **Java:** ≥ 21
- **Fabric API:** 0.95.8+1.20.1

Multi-version support can be added by adjusting `gradle.properties`.

## Troubleshooting

### Build Fails
- Ensure Java 21+ is installed: `java -version`
- Delete `build/` folder and try again
- Run `gradlew clean` before rebuilding

### Mod not appearing in-game
- Check that the JAR is in the correct `mods` folder
- Verify Fabric Loader is installed
- Check logs for error messages

### Effects not working
- Verify status effect names match exactly: `virusmod:common_cold`, `virusmod:flu`
- Check that cheats are enabled if using commands

## License

MIT License - See LICENSE file for details

## Support

For issues or feature requests, create an issue on GitHub or consult the Fabric community.

---

**Created:** 2024  
**Version:** 1.0.0  
**Last Updated:** April 14, 2026
