package com.miguelagsilva.skills.module.misc;

import com.miguelagsilva.skills.SkillsClient;
import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;
import net.minecraft.client.gui.screen.ChatScreen;

public class EnchantModule extends AbstractModule {
    public EnchantModule() {
        super("Enchant", "Adds client-side enchant to item in hand. (Creative Only)", ModuleCategory.MISC);
    }

    @Override
    public void onEnable() {
        if (SkillsClient.commandManager.getCommand("enchant") == null) return;
        if (client.player == null || client.world == null) return;
        this.toggle();
        client.setScreen(new ChatScreen(".enchant <add|remove> <id> [level]", false));
    }
}
