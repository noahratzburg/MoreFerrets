package com.CaptShekels.MoreFerrets.event;

import com.CaptShekels.MoreFerrets.MoreFerrets;
import com.CaptShekels.MoreFerrets.entity.ModEntities;
import com.CaptShekels.MoreFerrets.entity.custom.FerretEntity;
import com.CaptShekels.MoreFerrets.entity.custom.FerretPandaEntity;
import com.CaptShekels.MoreFerrets.entity.custom.FerretStandardEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreFerrets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.FERRET_STANDARD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, FerretStandardEntity::canFerretStandardSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ModEntities.FERRET_PANDA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, FerretPandaEntity::canFerretPandaSpawn, SpawnPlacementRegisterEvent.Operation.OR);
    }

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.FERRET_STANDARD.get(), FerretEntity.setAttributes());
        event.put(ModEntities.FERRET_PANDA.get(), FerretEntity.setAttributes());
    }
}
