package com.natamus.betterspawnercontrol.neoforge.events;

import com.mojang.datafixers.util.Either;
import com.natamus.betterspawnercontrol.events.MobSpawnerEvent;
import com.natamus.collective.functions.WorldFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;

@EventBusSubscriber
public class NeoForgeMobSpawnerEvent {
	@SubscribeEvent
	public static void onMobSpawn(MobSpawnEvent.PositionCheck e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		BaseSpawner msbl = e.getSpawner();
		if (msbl != null) {
			Either<BlockEntity, Entity> spawnerEntityEither = msbl.getOwner();
			if (spawnerEntityEither == null) {
				return;
			}

			BlockPos spawnerPos;
			if (spawnerEntityEither.left().isPresent()) {
				spawnerPos = spawnerEntityEither.left().get().getBlockPos();
			}
			else if (spawnerEntityEither.right().isPresent()) {
				spawnerPos = spawnerEntityEither.right().get().blockPosition();
			}
			else {
				return;
			}

			if (!(MobSpawnerEvent.onMobSpawn(e.getEntity(), (ServerLevel)level, spawnerPos, null))) {
				e.setResult(MobSpawnEvent.PositionCheck.Result.FAIL);
			}
		}
	}
}
