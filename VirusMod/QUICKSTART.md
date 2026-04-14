# Quick Start Guide - Virus Mod Setup

## Step 1: Prerequisites

Make sure you have the following installed:

1. **Java 21 or later**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Verify: Open CMD and run `java -version`

2. **Git** (optional, for version control)
   - Download from: https://git-scm.com/

## Step 2: Set Up the Mod Project

The project is already set up with all necessary files. Navigate to the VirusMod folder:

```bash
cd "C:\Users\twhite32\Desktop\VirusMod"
```

## Step 3: Generate Gradle Wrapper

If running on Windows, you may need to initialize the Gradle wrapper:

```bash
gradle wrapper --gradle-version 8.4
```

Or if you have Gradle installed globally:
```bash
gradlew wrapper
```

## Step 4: Set Up the Development Environment

Run the Fabric setup command:

```bash
gradlew genSources
```

This downloads all necessary dependencies and generates decompiled Minecraft source files.

## Step 5: Build the Mod

On Windows:
```bash
build.bat
```

Or manually:
```bash
gradlew build
```

The compiled mod JAR will be at:
```
build/libs/virusmod-1.0.0.jar
```

## Step 6: Install in Minecraft

1. Download and install Fabric Loader: https://fabricmc.net/use/
2. Create a new Fabric instance in your launcher (MultiMC, Prism Launcher, or official launcher with Fabric)
3. Locate the `mods` folder in your Minecraft directory
4. Copy `build/libs/virusmod-1.0.0.jar` into the `mods` folder
5. Launch Minecraft

## Step 7: Test the Mod

In-game (with cheats enabled), run:

```
/effect @s virusmod:common_cold 300 0
```

You should see the Common Cold effect applied!

## Troubleshooting

### "gradlew is not recognized"
- Make sure you're in the correct directory: `C:\Users\twhite32\Desktop\VirusMod`
- Try: `.\gradlew.bat build` (with dot prefix)

### "Java not found"
- Install Java 21+: https://www.oracle.com/java/technologies/downloads/
- Restart your terminal after installation

### Build fails with dependency errors
- Try: `gradlew clean gradleCache`
- Then: `gradlew build`

### Mod doesn't load in-game
- Ensure Fabric Loader is installed (not just Fabric API)
- Check that the JAR is in the correct `mods` folder
- Look for error messages in the Minecraft launcher log

## Next Steps

- **View SETUP_GUIDE.md** for detailed customization options
- **View README.md** for feature descriptions
- Read the source code in `src/main/java/com/example/virusmod/` to understand how it works

Happy modding!
