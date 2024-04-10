package com.CaptShekels.MoreFerrets.entity.client;

import com.CaptShekels.MoreFerrets.MoreFerrets;
import com.CaptShekels.MoreFerrets.entity.custom.FerretEntity;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.Map;

public class FerretModel extends GeoModel<FerretEntity> {
    private static final Map<FerretEntity.FerretType, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (entityType) -> {
        entityType.put(FerretEntity.FerretType.STANDARD, new ResourceLocation(MoreFerrets.MODID,"textures/entity/ferret_standard.png"));
        entityType.put(FerretEntity.FerretType.PANDA, new ResourceLocation(MoreFerrets.MODID,"textures/entity/ferret_panda.png"));
    });

    @Override
    public ResourceLocation getModelResource(FerretEntity ferretEntity) {
        return new ResourceLocation(MoreFerrets.MODID, "geo/ferret.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FerretEntity ferretEntity) {
        return TEXTURES.get(ferretEntity.getFerretType());
    }

    @Override
    public ResourceLocation getAnimationResource(FerretEntity ferretEntity) {
        return new ResourceLocation(MoreFerrets.MODID, "animations/ferret.animation.json");
    }

    @Override
    public void setCustomAnimations(FerretEntity animatable, long instanceId, AnimationState<FerretEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("ferret_head");

        if ( head != null ) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotX(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
