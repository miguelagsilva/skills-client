package com.miguelagsilva.skills.mixin;

import com.miguelagsilva.skills.client.SkillsClient;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightmapTextureManager.class)
public class LightMapTextureManagerMixin {
    // We intercept the Math.max(float, float) call inside the update() method
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"))
    private float injectFullbrightGamma(float a, float b) {
        if (SkillsClient.moduleManager == null
                || SkillsClient.moduleManager.getModule("fullbright") == null
                || !SkillsClient.moduleManager.getModule("fullbright").isEnabled()) {
            return Math.max(a, b);
        }
        return 100.0F;
    }
}
