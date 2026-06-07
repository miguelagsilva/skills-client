package com.miguelagsilva.skills.module;

import com.miguelagsilva.skills.client.SkillsClient;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public abstract class AbstractModule implements IModule {
    private final String name;
    private final String description;
    private final ModuleCategory category;
    private boolean isEnabled = false;
    private int keybind = GLFW.GLFW_KEY_UNKNOWN;

    protected static MinecraftClient client = MinecraftClient.getInstance();

    protected AbstractModule(String name, String description, ModuleCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    protected AbstractModule(
            String name, String description, ModuleCategory category, int keybind) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.keybind = keybind;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public int getKeybind() {
        return keybind;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void toggle() {
        isEnabled = !isEnabled;
        if (isEnabled) {
            SkillsClient.EVENT_BUS.register(this);
            onEnable();
        } else {
            SkillsClient.EVENT_BUS.unregister(this);
            onDisable();
        }
        SkillsClient.Logger.info(
                "Module {} has been {}", this.getName(), this.isEnabled() ? "enabled" : "disabled");
    }

    public void onEnable() {}

    public void onDisable() {}
}
