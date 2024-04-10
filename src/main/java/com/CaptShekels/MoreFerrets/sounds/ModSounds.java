package com.CaptShekels.MoreFerrets.sounds;

import com.CaptShekels.MoreFerrets.MoreFerrets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MoreFerrets.MODID);

    public static final RegistryObject<SoundEvent> FERRET_IDLE_ONE = registerSoundEvents("ferret_idle_one");
    public static final RegistryObject<SoundEvent> FERRET_IDLE_TWO = registerSoundEvents("ferret_idle_two");
    public static final RegistryObject<SoundEvent> FERRET_HIT = registerSoundEvents("ferret_hit");
    public static final RegistryObject<SoundEvent> FERRET_DEATH = registerSoundEvents("ferret_death");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MoreFerrets.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
