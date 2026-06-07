package com.miguelagsilva.skills.module;

import com.google.common.eventbus.Subscribe;
import com.miguelagsilva.skills.event.TickEvent;

public class AutoSprintModule extends AbstractModule {
    protected AutoSprintModule() {
        super("AutoSprint", "Run forest run", ModuleCategory.MOVEMENT);
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (client.player != null
                && client.player.lastX != client.player.getX()
                && client.player.lastZ != client.player.getZ()
                && (client.player.forwardSpeed != 0 || client.player.sidewaysSpeed != 0)) {
            client.player.setSprinting(true);
        }
    }
}
