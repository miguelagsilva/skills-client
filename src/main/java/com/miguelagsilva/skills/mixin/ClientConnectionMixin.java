package com.miguelagsilva.skills.mixin;

import com.miguelagsilva.skills.SkillsClient;
import com.miguelagsilva.skills.event.PacketEvent;
import io.netty.channel.ChannelFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {

    @Inject(
            method =
                    "send(Lnet/minecraft/network/packet/Packet;Lio/netty/channel/ChannelFutureListener;Z)V",
            at = @At("HEAD"),
            cancellable = true)
    public void outPacket(
            Packet<?> packet,
            @Nullable ChannelFutureListener listener,
            boolean flush,
            CallbackInfo ci) {
        PacketEvent.Send event = new PacketEvent.Send(packet);

        SkillsClient.EVENT_BUS.post(event);

        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(
            method =
                    "handlePacket(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;)V",
            at = @At("HEAD"),
            cancellable = true)
    private static <T extends PacketListener> void inPacket(
            Packet<T> packet, PacketListener listener, CallbackInfo ci) {
        PacketEvent.Receive event = new PacketEvent.Receive(packet);

        SkillsClient.EVENT_BUS.post(event);

        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}
