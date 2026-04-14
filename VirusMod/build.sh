#!/bin/bash
# Virus Mod Build Script for Linux/Mac
# This script builds the Virus Mod for Minecraft Fabric

echo "Building Virus Mod..."
./gradlew build

if [ $? -eq 0 ]; then
    echo ""
    echo "Build successful! JAR file created in build/libs/"
else
    echo ""
    echo "Build failed! Check the error messages above."
fi
