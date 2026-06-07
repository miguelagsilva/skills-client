package com.miguelagsilva.skills.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger("SkillsClient");

    @Inject(at = @At("HEAD"), method = "init()V")
    private void onInit(CallbackInfo info) {
        LOGGER.info("Skills locked and loaded!");
    }
}
