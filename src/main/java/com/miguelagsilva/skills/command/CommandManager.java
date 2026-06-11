package com.miguelagsilva.skills.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.miguelagsilva.skills.SkillsClient;
import com.miguelagsilva.skills.utils.PlayerUtils;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CommandManager {
    private final Map<String, AbstractCommand> commands = new HashMap<>();
    private final String COMMAND_PREFIX = ".";
    private static MinecraftClient client = MinecraftClient.getInstance();
    private boolean nextMessageIgnore = false;

    public CommandManager() {
        registerCommand(new EnchantCommand());
        ClientSendMessageEvents.ALLOW_CHAT.register(this::onSendChatMessage);
    }

    private void registerCommand(AbstractCommand command) {
        commands.put(command.getLabel(), command);
    }

    public boolean onSendChatMessage(String message) {
        if (nextMessageIgnore) {
            nextMessageIgnore = false;
            return true;
        }
        if (client.player == null) return false;

        if (message.startsWith(COMMAND_PREFIX)) {
            String strippedMessage = message.substring(1);
            SkillsClient.Logger.info("stripped " + strippedMessage);
            if (message.startsWith(COMMAND_PREFIX + COMMAND_PREFIX)) {
                nextMessageIgnore = true;
                client.player.networkHandler.sendChatMessage(strippedMessage);
                return false;
            }

            for (AbstractCommand cmd : commands.values()) {
                if (strippedMessage.startsWith(cmd.getLabel())) {
                    cmd.onCall(strippedMessage);
                    return false;
                }
            }
            PlayerUtils.errorMessage("Invalid command!");
            return false;
        }

        return true;
    }

    public AbstractCommand getCommand(String name) {
        return commands.get(name.toLowerCase());
    }
}
