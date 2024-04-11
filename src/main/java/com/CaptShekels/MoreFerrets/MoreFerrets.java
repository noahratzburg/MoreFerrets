package com.CaptShekels.MoreFerrets;

import com.CaptShekels.MoreFerrets.entity.ModEntities;
import com.CaptShekels.MoreFerrets.entity.client.FerretRenderer;
import com.CaptShekels.MoreFerrets.item.ModItems;
import com.CaptShekels.MoreFerrets.sounds.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// 3rd Party Mod Imports
import software.bernie.geckolib.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MoreFerrets.MODID)
public class MoreFerrets
{
    public static final String MODID = "moreferrets";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MoreFerrets()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        GeckoLib.initialize();

        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSounds.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if ( event.getTabKey() == CreativeModeTabs.SPAWN_EGGS ) {
            event.accept(ModItems.FERRET_SPAWN_EGG);
            event.accept(ModItems.FERRET_PANDA_SPAWN_EGG);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.FERRET_STANDARD.get(), FerretRenderer::new);
            EntityRenderers.register(ModEntities.FERRET_PANDA.get(), FerretRenderer::new);
        }
    }
}
