package com.miguelagsilva.skills.mixin;

import com.miguelagsilva.skills.accessor.PlayerMoveC2SAccessor;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerMoveC2SPacket.class)
public interface PlayerMoveC2SPacketMixin extends PlayerMoveC2SAccessor {
    @Accessor("onGround")
    @Mutable
    @Override
    public void setOnGround(boolean onGround);
}
