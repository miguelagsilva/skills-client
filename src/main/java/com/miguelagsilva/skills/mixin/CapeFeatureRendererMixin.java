package com.miguelagsilva.skills.mixin;

import com.miguelagsilva.skills.SkillsClient;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.SkinTextures;
import net.minecraft.util.AssetInfo;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeFeatureRenderer.class)
public abstract class CapeFeatureRendererMixin {

    @Inject(
            method =
                    "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V",
            at = @At("HEAD"))
    private void injectCustomCapeState(
            MatrixStack matrixStack,
            OrderedRenderCommandQueue orderedRenderCommandQueue,
            int i,
            PlayerEntityRenderState state,
            float f,
            float g,
            CallbackInfo ci) {
        if (SkillsClient.moduleManager == null
                || SkillsClient.moduleManager.getModule("cape") == null
                || !SkillsClient.moduleManager.getModule("cape").isEnabled()) {
            return;
        }

        SkinTextures originalSkin = state.skinTextures;
        Identifier customCapeId = Identifier.of("skills", "cape");
        AssetInfo.TextureAsset customCapeAsset = new AssetInfo.TextureAssetInfo(customCapeId);

        state.skinTextures =
                new SkinTextures(
                        originalSkin.body(), // Keep their normal body
                        customCapeAsset, // Inject our custom cape
                        customCapeAsset, // Inject custom cape for Elytra too
                        originalSkin.model(), // Keep SLIM/WIDE model type
                        originalSkin.secure() // Keep security state
                        );
    }
}
