package com.miguelagsilva.skills.mixin;

import com.miguelagsilva.skills.SkillsClient;
import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.render.XRayModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "shouldDrawSide",
    at = @At("RETURN"), cancellable = true)
    private static void shouldDrawSide(BlockState state, BlockState otherState, Direction side, CallbackInfoReturnable<Boolean> cir) {
        XRayModule xrayModule = (XRayModule) SkillsClient.moduleManager.getModule("XRay");
        if (xrayModule == null ||
                !xrayModule.isEnabled() ||
                SkillsClient.client.player == null) return;

        xrayModule.shouldDrawSide(state, otherState, side, cir);
    }
}