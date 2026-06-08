package com.miguelagsilva.skills.module.movement;

import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;
import net.minecraft.entity.attribute.EntityAttributes;

public class StepModule extends AbstractModule {
    private double stepHeight = 1.5;

    public StepModule() {
        super("Step", "Increases step height of player", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEnable() {
        if (client.player == null) return;
        client.player.getAttributeInstance(EntityAttributes.STEP_HEIGHT).setBaseValue(stepHeight);
    }

    @Override
    public void onDisable() {
        if (client.player == null) return;
        client.player.getAttributeInstance(EntityAttributes.STEP_HEIGHT).setBaseValue(0.6);
    }
}
