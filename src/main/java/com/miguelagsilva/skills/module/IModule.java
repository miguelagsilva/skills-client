package com.miguelagsilva.skills.module;

public interface IModule {
    String getName();

    int getKeybind();

    void setKeybind(int keybind);

    boolean isEnabled();

    void toggle();

    void onEnable();

    void onDisable();
}
