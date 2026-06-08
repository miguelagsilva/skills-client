package com.miguelagsilva.skills.module.movement;

import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;

public class FlightModule extends AbstractModule {
    public FlightModule() {
        super("Flight", "Allows you to fly", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEnable() {
        if (client.player == null) return;
        client.player.getAbilities().allowFlying = true;
    }

    public void onDisable() {
        if (client.player == null) return;
        client.player.getAbilities().allowFlying = false;
    }
}
