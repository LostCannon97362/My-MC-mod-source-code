package com.example.virusmod;

import com.example.virusmod.event.VirusTransmissionEvents;
import com.example.virusmod.event.ZombieInfectionEvents;
import com.example.virusmod.event.HypnotizeVirusEvents;
import com.example.virusmod.event.HypnotizeCommandEvents;
import com.example.virusmod.event.LoveVirusEvents;
import net.fabricmc.api.ModInitializer;

public class VirusModServer implements ModInitializer {
	@Override
	public void onInitialize() {
		VirusTransmissionEvents.register();
		ZombieInfectionEvents.register();
		HypnotizeVirusEvents.register();
		HypnotizeCommandEvents.register();
		LoveVirusEvents.register();
	}
}
