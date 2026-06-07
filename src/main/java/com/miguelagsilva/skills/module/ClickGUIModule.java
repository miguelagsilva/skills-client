package com.miguelagsilva.skills.module;

import com.miguelagsilva.skills.ui.ClickGUIScreen;
import org.lwjgl.glfw.GLFW;

public class ClickGUIModule extends AbstractModule {

    public ClickGUIModule() {
        super("ClickGUI", "Opens the visual menu", ModuleCategory.MISC, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    @Override
    public void onEnable() {
        if (client.player != null) {
            client.setScreen(new ClickGUIScreen());
        }
    }

    @Override
    public void onDisable() {
        if (client.currentScreen instanceof ClickGUIScreen) {
            client.currentScreen.close();
        }
    }
}
