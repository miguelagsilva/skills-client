package com.miguelagsilva.skills.module.movement;

import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;
import net.minecraft.entity.attribute.EntityAttributes;
import org.lwjgl.glfw.GLFW;

public class MoonModule extends AbstractModule {
    public MoonModule() {
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
