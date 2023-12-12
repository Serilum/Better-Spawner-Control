package com.natamus.betterspawnercontrol.events;

import com.natamus.collective.functions.BlockPosFunctions;
import com.natamus.collective.functions.EntityFunctions;
import com.natamus.collective.functions.TileEntityFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

public class MobSpawnerEvent {
	public static boolean onMobSpawn(Mob entity, ServerLevel world, BlockPos spawnerPos, MobSpawnType spawnReason) {
		if (EntityFunctions.isEntityFromSpawner(entity)) {
			if (spawnerPos == null) {
				return true;
			}
			
			boolean alltorches = true;
			for (BlockPos ap : BlockPosFunctions.getBlocksAround(spawnerPos, false)) {
				Block block = world.getBlockState(ap).getBlock();
				if (!(block instanceof TorchBlock) && !(block instanceof WallTorchBlock)) {
					alltorches = false;
					break;
				}
			}
			
			if (alltorches) {
				BlockEntity blockentity = world.getBlockEntity(spawnerPos);
				if (blockentity instanceof SpawnerBlockEntity) {
					SpawnerBlockEntity sbe = (SpawnerBlockEntity)blockentity;
					if (sbe != null) {
						BaseSpawner basespawner = sbe.getSpawner();
						TileEntityFunctions.resetMobSpawnerDelay(basespawner);
					}
				}
				return false;
			}
		}
		
		return true;
	}
}