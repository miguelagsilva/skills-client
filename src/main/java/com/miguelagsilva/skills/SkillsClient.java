package com.miguelagsilva.skills;

import com.google.common.eventbus.EventBus;
import com.miguelagsilva.skills.command.CommandManager;
import com.miguelagsilva.skills.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkillsClient implements ClientModInitializer {
    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static Logger Logger = LoggerFactory.getLogger("SkillsClient");
    public static final EventBus EVENT_BUS = new EventBus("SkillsBus");

    @Override
    public void onInitializeClient() {
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
    }
}
