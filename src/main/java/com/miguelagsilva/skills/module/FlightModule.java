package com.miguelagsilva.skills.module;

public class FlightModule extends AbstractModule{
    FlightModule(){
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
