package com.CaptShekels.MoreFerrets.entity;

import com.CaptShekels.MoreFerrets.MoreFerrets;
import com.CaptShekels.MoreFerrets.entity.custom.FerretEntity;
import com.CaptShekels.MoreFerrets.entity.custom.FerretPandaEntity;
import com.CaptShekels.MoreFerrets.entity.custom.FerretStandardEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MoreFerrets.MODID);

    public static final RegistryObject<EntityType<FerretStandardEntity>> FERRET_STANDARD =
            ENTITY_TYPES.register("ferret",
                    () -> EntityType.Builder.of(FerretStandardEntity::new, MobCategory.CREATURE)
                    .sized(1f, 0.4f)
                    .build(new ResourceLocation(MoreFerrets.MODID, "ferret").toString()));

    public static final RegistryObject<EntityType<FerretPandaEntity>> FERRET_PANDA =
            ENTITY_TYPES.register("ferret_panda",
                    () -> EntityType.Builder.of(FerretPandaEntity::new, MobCategory.CREATURE)
                            .sized(1f, 0.4f)
                            .build(new ResourceLocation(MoreFerrets.MODID, "ferret_panda").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
