package com.natamus.betterspawnercontrol;

import com.natamus.betterspawnercontrol.events.MobSpawnerEvent;
import com.natamus.betterspawnercontrol.util.Reference;
import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.collective.fabric.callbacks.CollectiveSpawnEvents;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		CollectiveSpawnEvents.MOB_CHECK_SPAWN.register((Mob entity, ServerLevel world, BlockPos spawnerPos, MobSpawnType spawnReason) -> {
			return MobSpawnerEvent.onMobSpawn(entity, world, spawnerPos, spawnReason);
		});
	}

	private static void setGlobalConstants() {

	}
}
