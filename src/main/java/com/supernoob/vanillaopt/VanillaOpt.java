package com.supernoob.vanillaopt;

import com.supernoob.vanillaopt.villager.ModPointOfInterests;
import com.supernoob.vanillaopt.villager.MoreVillagers;
import net.fabricmc.api.ModInitializer;

import net.minecraft.core.registries.BuiltInRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanillaOpt implements ModInitializer {
	public static final String MOD_ID = "vanillaopt";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		// 1. 先把校频幽匿感测体包装成音酱的工作站点 POI
		ModPointOfInterests.registerPOI();
		// 2. 再激活音酱职业
		MoreVillagers.registerAll(BuiltInRegistries.VILLAGER_PROFESSION);
		LOGGER.info("Hello Fabric world!");
	}
}