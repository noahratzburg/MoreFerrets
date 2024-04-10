package com.CaptShekels.MoreFerrets.entity.custom;

import com.CaptShekels.MoreFerrets.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class FerretPandaEntity extends FerretEntity{
    public FerretPandaEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setFerretType(FerretType.PANDA);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return ModEntities.FERRET_PANDA.get().create(level);
    }

    public static boolean canFerretPandaSpawn(EntityType<FerretPandaEntity> entity, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return Animal.checkAnimalSpawnRules(entity, level, spawnType, position, random);
    }
}
