package com.miguelagsilva.skills.module.combat;

import com.google.common.eventbus.Subscribe;
import com.miguelagsilva.skills.event.PacketEvent;
import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;

public class NoKnockbackModule extends AbstractModule {

    public NoKnockbackModule() {
        super("NoKnockback", "Not moving", ModuleCategory.COMBAT);
    }

    @Subscribe
    public void inPacket(PacketEvent.Receive event) {
        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket) {
            event.setCancelled(true);
        }
    }
}
