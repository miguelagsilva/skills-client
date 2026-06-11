package com.miguelagsilva.skills.command;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class FakeEnchantCommand extends AbstractCommand {
    protected FakeEnchantCommand() {
        super("fakeenchant");
    }

    @Override
    public void onCall(String message) {
        if (client.player == null || client.world == null) return;

        ItemStack itemStack = client.player.getStackInHand(client.player.getActiveHand());
        if (itemStack == null) return;

        String action = message.split(" ")[1];
        if (action.equalsIgnoreCase("add")) {
            String id = message.split(" ")[2];
            String level = message.split(" ")[3];

            RegistryKey<Enchantment> enchantmentKey =
                    RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.ofVanilla(id));
            if (enchantmentKey == null) {
                client.player.sendMessage(
                        Text.literal("Invalid enchantment!").formatted(Formatting.RED), false);
                return;
            }
            RegistryEntry<Enchantment> enchantmentEntry =
                    client.world
                            .getRegistryManager()
                            .getOrThrow(RegistryKeys.ENCHANTMENT)
                            .getOrThrow(enchantmentKey);
            if (enchantmentEntry == null) {
                client.player.sendMessage(
                        Text.literal("It wasn't possible to find that enchantment!")
                                .formatted(Formatting.RED),
                        false);
                return;
            }
            itemStack.addEnchantment(enchantmentEntry, Integer.parseInt(level));
            client.player.sendMessage(
                    Text.literal("Enchantment applied!").formatted(Formatting.GREEN), false);
        }
    }
}
