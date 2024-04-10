package com.CaptShekels.MoreFerrets.entity.client;

import com.CaptShekels.MoreFerrets.MoreFerrets;
import com.CaptShekels.MoreFerrets.entity.custom.FerretEntity;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class FerretRenderer extends GeoEntityRenderer<FerretEntity> {
    private static final Map<FerretEntity.FerretType, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (entityType) -> {
        entityType.put(FerretEntity.FerretType.STANDARD, new ResourceLocation(MoreFerrets.MODID,"textures/entity/ferret_standard.png"));
        entityType.put(FerretEntity.FerretType.PANDA, new ResourceLocation(MoreFerrets.MODID,"textures/entity/ferret_panda.png"));
    });

    public FerretRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FerretModel());
    }

    @Override
    public ResourceLocation getTextureLocation(FerretEntity ferret) {
        return TEXTURES.get(ferret.getFerretType());
    }

    @Override
    public void render(FerretEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if ( entity.isBaby() ) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
