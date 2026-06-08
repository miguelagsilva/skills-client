package com.miguelagsilva.skills.mixin;

import com.miguelagsilva.skills.client.SkillsClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

    @Shadow
    protected abstract boolean canBeCulled(T entity);

    @Inject(method = "shouldRender", at = @At("RETURN"), cancellable = true)
    private void shouldRender(
            T entity,
            Frustum frustum,
            double x,
            double y,
            double z,
            CallbackInfoReturnable<Boolean> cir) {
        if (SkillsClient.moduleManager == null
                || SkillsClient.moduleManager.getModule("EntityESP") == null
                || !SkillsClient.moduleManager.getModule("EntityESP").isEnabled()) {
            return;
        }
        boolean vanillaResult = cir.getReturnValue();

        if (!vanillaResult && this.canBeCulled(entity)) {
            cir.setReturnValue(true);
        }
    }
}
