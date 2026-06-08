package com.miguelagsilva.skills.module.player;

import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;
import org.lwjgl.glfw.GLFW;

public class CapeModule extends AbstractModule {
    public CapeModule() {
        super("Cape", "Shows cool cape", ModuleCategory.PLAYER, GLFW.GLFW_KEY_C);
    }
}
