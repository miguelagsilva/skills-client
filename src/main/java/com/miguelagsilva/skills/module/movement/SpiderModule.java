package com.miguelagsilva.skills.module.movement;

import com.google.common.eventbus.Subscribe;
import com.miguelagsilva.skills.event.TickEvent;
import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;
import org.lwjgl.glfw.GLFW;

public class SpiderModule extends AbstractModule {
    public SpiderModule() {
        super("Spider", "Climbs walls", ModuleCategory.MOVEMENT, GLFW.GLFW_KEY_V);
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (client.player != null && client.player.horizontalCollision) {
            client.player.setVelocity(
                    client.player.getVelocity().x, 0.2, client.player.getVelocity().z);
        }
    }
}
