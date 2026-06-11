package com.miguelagsilva.skills.module.misc;

import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;

public class FakeEnchantModule extends AbstractModule {
    public FakeEnchantModule() {
        super("FakeEnchant", "Adds client-side enchant to item in hand", ModuleCategory.MISC);
    }

    @Override
    public void onEnable() {
        if (client.player == null || client.world == null) return;
    }
}
