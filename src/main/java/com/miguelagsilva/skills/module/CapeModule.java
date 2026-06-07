package com.miguelagsilva.skills.module;

import org.lwjgl.glfw.GLFW;

public class CapeModule extends AbstractModule {
    protected CapeModule() {
        super("Cape", "Shows cool cape", ModuleCategory.PLAYER, GLFW.GLFW_KEY_C);
    }
}
