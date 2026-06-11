package com.miguelagsilva.skills.command;

import java.util.ArrayList;

import com.miguelagsilva.skills.SkillsClient;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CommandManager {
    private final ArrayList<AbstractCommand> commands = new ArrayList<>();
    private final String COMMAND_PREFIX = ".";
    private static MinecraftClient client = MinecraftClient.getInstance();
    private boolean nextMessageIgnore = false;

    public CommandManager() {
        registerCommand(new FakeEnchantCommand());
        ClientSendMessageEvents.ALLOW_CHAT.register(this::onSendChatMessage);
    }

    private void registerCommand(AbstractCommand command) {
        commands.add(command);
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

            for (AbstractCommand cmd : commands) {
                if (strippedMessage.startsWith(cmd.getLabel())) {
                    cmd.onCall(strippedMessage);
                    return false;
                }
            }
            client.player.sendMessage(
                    Text.literal("Invalid command!").formatted(Formatting.RED), false);
            return false;
        }

        return true;
    }
}
