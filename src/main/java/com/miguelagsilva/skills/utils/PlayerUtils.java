package com.miguelagsilva.skills.utils;

import com.miguelagsilva.skills.SkillsClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PlayerUtils {
    private static final Text CLIENT_PREFIX = Text.literal("ѕᴋɪʟʟѕ ► ").setStyle(Style.EMPTY.withColor(0x860111));
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void sendMessage(String message) {
        if (client.player == null) return;
        client.player.sendMessage(Text.empty().append(CLIENT_PREFIX).append(Text.literal(message)), false);
    }
    public static void sendMessage(String message, Formatting format) {
        if (client.player == null) return;
        client.player.sendMessage(Text.empty().append(CLIENT_PREFIX).append(Text.literal(message).formatted(format)), false);
    }

    public static void errorMessage(String message) {
        sendMessage(message, Formatting.RED);
    }
}
