package com.CaptShekels.MoreFerrets.datagen;

import com.CaptShekels.MoreFerrets.MoreFerrets;
import com.CaptShekels.MoreFerrets.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MoreFerrets.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModItems.FERRET_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }
}
