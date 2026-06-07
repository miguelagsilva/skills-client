package com.miguelagsilva.skills.mixin;

import com.miguelagsilva.skills.client.SkillsClient;
import com.miguelagsilva.skills.event.TickEvent;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if (SkillsClient.moduleManager != null) {
            SkillsClient.EVENT_BUS.post(new TickEvent());
        }
    }
}
