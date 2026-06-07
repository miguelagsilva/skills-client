package com.miguelagsilva.skills.module;

import net.minecraft.entity.attribute.EntityAttributes;
import org.lwjgl.glfw.GLFW;

public class MoonModule extends AbstractModule {
    protected MoonModule() {
        super("Moon", "Makes you fly", ModuleCategory.MOVEMENT, GLFW.GLFW_KEY_F);
    }

    @Override
    public void onEnable() {
        if (client.player == null) return;
        client.player.getAttributeInstance(EntityAttributes.GRAVITY).setBaseValue(0.01);
    }

    @Override
    public void onDisable() {
        if (client.player == null) return;
        client.player.getAttributeInstance(EntityAttributes.GRAVITY).setBaseValue(0.08);
    }
}
