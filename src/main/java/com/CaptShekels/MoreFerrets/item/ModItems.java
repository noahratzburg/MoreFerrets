package com.CaptShekels.MoreFerrets.item;

import com.CaptShekels.MoreFerrets.MoreFerrets;
import com.CaptShekels.MoreFerrets.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MoreFerrets.MODID);

    public static final RegistryObject<Item> FERRET_SPAWN_EGG = ITEMS.register("ferret_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.FERRET_STANDARD, 0x964B00, 0x000000, new Item.Properties()));

    public static final RegistryObject<Item> FERRET_PANDA_SPAWN_EGG = ITEMS.register("ferret_panda_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.FERRET_PANDA, 0xFFFFFF, 0xD2C9C3, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
