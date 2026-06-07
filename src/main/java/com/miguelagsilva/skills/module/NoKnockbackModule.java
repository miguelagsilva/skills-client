package com.miguelagsilva.skills.module;

import com.google.common.eventbus.Subscribe;
import com.miguelagsilva.skills.event.PacketEvent;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;

public class NoKnockbackModule extends AbstractModule {

    protected NoKnockbackModule() {
        super("NoKnockback", "Not moving", ModuleCategory.COMBAT);
    }

    @Subscribe
    public void inPacket(PacketEvent.Receive event) {
        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket) {
            event.setCancelled(true);
        }
    }
}
