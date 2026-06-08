package com.miguelagsilva.skills.module;

import com.google.common.eventbus.Subscribe;
import com.miguelagsilva.skills.event.PacketEvent;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;

public class NoHandSwingModule extends AbstractModule {
    protected NoHandSwingModule() {
        super("NoHandSwing", "No more hand movement", ModuleCategory.PLAYER);
    }

    @Subscribe
    public void outPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof HandSwingC2SPacket) {
            event.setCancelled(true);
        }
    }
}
