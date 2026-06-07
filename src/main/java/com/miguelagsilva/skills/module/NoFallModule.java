package com.miguelagsilva.skills.module;

import com.google.common.eventbus.Subscribe;
import com.miguelagsilva.skills.event.TickEvent;

public class NoFallModule extends AbstractModule {
    protected NoFallModule() {
        super("NoFall", "No more fall damage", ModuleCategory.MOVEMENT);
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (client.player != null) {
            client.player.getAbilities().allowFlying = true;
            client.player.fallDistance = 0;
        }
    }
}
