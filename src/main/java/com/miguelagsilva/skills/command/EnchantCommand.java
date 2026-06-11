package com.miguelagsilva.skills.command;

import com.miguelagsilva.skills.utils.PlayerUtils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class EnchantCommand extends AbstractCommand {
    protected EnchantCommand() {
        super("enchant");
    }

    @Override
    public void onCall(String message) {
        if (client.player == null || client.world == null) return;

        try {
            String[] parts = message.split(" ");
            String action = getAction(parts);

            if (action.equalsIgnoreCase("add")) {
                ItemStack item = getHeldItem();
                String id = getArg(parts, 2, "Enchantment ID");
                int level = getLevel(parts);
                RegistryEntry<Enchantment> enchantment = getEnchantment(id);
                item.addEnchantment(enchantment, level);
                PlayerUtils.sendMessage("Enchantment applied!", Formatting.GREEN);
            } else if (action.equalsIgnoreCase("remove")) {
                ItemStack item = getHeldItem();
                String id = getArg(parts, 2, "Enchantment ID");
                RegistryEntry<Enchantment> enchantment = getEnchantment(id);
                ItemEnchantmentsComponent current = item.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
                ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(current);
                builder.remove(e -> e.equals(enchantment));
                item.set(DataComponentTypes.ENCHANTMENTS, builder.build());
                PlayerUtils.sendMessage("Enchantment removed!", Formatting.GREEN);
            }
        } catch (CommandException e) {
            PlayerUtils.errorMessage(e.getMessage());
        }
    }

    private String getAction(String[] parts) throws CommandException {
        if (parts.length < 2) throw new CommandException("Usage: .enchant <add|remove> <id> [level]");
        return parts[1];
    }

    private String getArg(String[] parts, int index, String name) throws CommandException {
        if (parts.length <= index) throw new CommandException("Missing argument: " + name);
        return parts[index];
    }

    private int getLevel(String[] parts) throws CommandException {
        String raw = getArg(parts, 3, "level");
        try {
            int level = Integer.parseInt(raw);
            if (level < 1) throw new CommandException("Level must be at least 1.");
            return level;
        } catch (NumberFormatException e) {
            throw new CommandException("Invalid level: \"" + raw + "\" is not a number.");
        }
    }

    private ItemStack getHeldItem() throws CommandException {
        ItemStack main = client.player.getMainHandStack();
        if (main != null && !main.isEmpty()) return main;
        ItemStack off = client.player.getOffHandStack();
        if (off != null && !off.isEmpty()) return off;
        throw new CommandException("You must hold an item.");
    }

    private RegistryEntry<Enchantment> getEnchantment(String id) throws CommandException {
        RegistryKey<Enchantment> key = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.ofVanilla(id));
        try {
            return client.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(key);
        } catch (Exception e) {
            throw new CommandException("Unknown enchantment: \"" + id + "\".");
        }
    }
}
