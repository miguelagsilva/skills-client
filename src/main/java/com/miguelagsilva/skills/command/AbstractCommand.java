package com.miguelagsilva.skills.command;

import net.minecraft.client.MinecraftClient;

public abstract class AbstractCommand {
    private final String label;
    protected static MinecraftClient client = MinecraftClient.getInstance();

    protected AbstractCommand(String label) {
        this.label = label;
    }

    public abstract void onCall(String message);

    public String getLabel() {
        return label;
    }
}
