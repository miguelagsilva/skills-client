package com.miguelagsilva.skills.module.player;

import com.google.common.eventbus.Subscribe;
import com.miguelagsilva.skills.event.PacketEvent;
import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;

public class NoHandSwingModule extends AbstractModule {
    public NoHandSwingModule() {
        super("NoHandSwing", "No more hand movement", ModuleCategory.PLAYER);
    }

    @Subscribe
    public void outPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof HandSwingC2SPacket) {
            event.setCancelled(true);
        }
    }
}
