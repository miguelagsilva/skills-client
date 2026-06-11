package com.miguelagsilva.skills.module;

import com.miguelagsilva.skills.module.combat.NoKnockbackModule;
import com.miguelagsilva.skills.module.misc.BlinkModule;
import com.miguelagsilva.skills.module.misc.ClickGUIModule;
import com.miguelagsilva.skills.module.misc.FakeEnchantModule;
import com.miguelagsilva.skills.module.movement.*;
import com.miguelagsilva.skills.module.player.CapeModule;
import com.miguelagsilva.skills.module.player.NoHandSwingModule;
import com.miguelagsilva.skills.module.render.EntityESP2DModule;
import com.miguelagsilva.skills.module.render.FreecamModule;
import com.miguelagsilva.skills.module.render.FullBrightModule;
import com.miguelagsilva.skills.module.render.PlayerESPModule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {
    private final Map<String, AbstractModule> modules = new HashMap<>();

    public ModuleManager() {
        registerModule(new NoFallModule());
        registerModule(new NoKnockbackModule());
        registerModule(new AutoSprintModule());
        registerModule(new ClickGUIModule());
        registerModule(new CapeModule());
        registerModule(new FullBrightModule());
        registerModule(new MoonModule());
        registerModule(new StepModule());
        registerModule(new SpiderModule());
        registerModule(new SlowPhasingModule());
        registerModule(new FreecamModule());
        registerModule(new FlightModule());
        registerModule(new NoHandSwingModule());
        registerModule(new EntityESP2DModule());
        registerModule(new BlinkModule());
        registerModule(new PlayerESPModule());
        registerModule(new FakeEnchantModule());
    }

    void registerModule(AbstractModule module) {
        modules.put(module.getName().toLowerCase(), module);
    }

    public AbstractModule getModule(String name) {
        return modules.get(name.toLowerCase());
    }

    public Map<String, AbstractModule> getModules() {
        return modules;
    }

    public Map<ModuleCategory, List<AbstractModule>> getModulesInCategories() {
        Map<ModuleCategory, List<AbstractModule>> categoryMap = new HashMap<>();
        for (AbstractModule module : modules.values()) {
            categoryMap.computeIfAbsent(module.getCategory(), k -> new ArrayList<>()).add(module);
        }
        return categoryMap;
    }

    public void HandleKeyPress(int key) {
        for (AbstractModule module : modules.values()) {
            if (module.getKeybind() == key) {
                module.toggle();
            }
        }
    }
}
