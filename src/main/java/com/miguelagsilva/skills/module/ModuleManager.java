package com.miguelagsilva.skills.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {
    private Map<String, AbstractModule> modules = new HashMap<>();

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
