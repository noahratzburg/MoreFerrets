package com.CaptShekels.MoreFerrets.item;

import com.CaptShekels.MoreFerrets.MoreFerrets;
import com.CaptShekels.MoreFerrets.entity.ModEntities;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MoreFerrets.MODID);

    /* FERRET SPAWN EGGS */
    public static final RegistryObject<Item> FERRET_SPAWN_EGG = ITEMS.register("ferret_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.FERRET_STANDARD, 0x964B00, 0x000000, new Item.Properties()));
    public static final RegistryObject<Item> FERRET_PANDA_SPAWN_EGG = ITEMS.register("ferret_panda_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.FERRET_PANDA, 0xFFFFFF, 0xD2C9C3, new Item.Properties()));

    /* FERRET DROPS */
    public static final RegistryObject<Item> FERRET_HIDE = ITEMS.register("ferret_hide",
            () -> new Item(new Item.Properties()));

    /* FERRET BREED ITEMS CUSTOM */
    public static final RegistryObject<Item> FERRET_KIBBLE = ITEMS.register("ferret_kibble",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static void registerCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if ( event.getTabKey() == CreativeModeTabs.SPAWN_EGGS ) {
            event.accept(ModItems.FERRET_SPAWN_EGG);
            event.accept(ModItems.FERRET_PANDA_SPAWN_EGG);
        }

        if( event.getTabKey() == CreativeModeTabs.INGREDIENTS ) {
            event.accept(ModItems.FERRET_HIDE);
        }

        if( event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS ) {
            event.accept(ModItems.FERRET_KIBBLE);
        }
    }
}
