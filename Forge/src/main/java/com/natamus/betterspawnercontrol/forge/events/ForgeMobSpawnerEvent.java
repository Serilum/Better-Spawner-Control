package com.natamus.betterspawnercontrol.forge.events;

import com.natamus.betterspawnercontrol.events.MobSpawnerEvent;
import com.natamus.collective.functions.WorldFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeMobSpawnerEvent {
	public static void registerEventsInBus() {
		// BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeMobSpawnerEvent.class);

		MobSpawnEvent.FinalizeSpawn.BUS.addListener(ForgeMobSpawnerEvent::onMobSpawn);
	}

	@SubscribeEvent
	public static boolean onMobSpawn(MobSpawnEvent.FinalizeSpawn e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return false;
		}

		BaseSpawner msbl = e.getSpawner();
		if (msbl != null) {
			BlockEntity spawnerEntity = msbl.getSpawnerBlockEntity();
			if (spawnerEntity == null) {
				return false;
			}

			BlockPos spawnerPos = spawnerEntity.getBlockPos();
			if (!(MobSpawnerEvent.onMobSpawn(e.getEntity(), (ServerLevel)level, spawnerPos, null))) {
				e.setSpawnCancelled(true);
				return true;
			}
		}
		return false;
	}
}
