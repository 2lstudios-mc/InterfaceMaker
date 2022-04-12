package dev._2lstudios.interfacemaker.configs;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import dev._2lstudios.interfacemaker.interfaces.InterfaceHotbar;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class HotbarConfigProcessor {
    private InterfaceMakerAPI api;
    private ItemConfigProcessor itemConfigProcessor;

    public HotbarConfigProcessor(InterfaceMakerAPI api, ItemConfigProcessor itemConfigProcessor) {
        this.api = api;
        this.itemConfigProcessor = itemConfigProcessor;
    }

    public void process(String menuName, Configuration config) {
        InterfaceHotbar interfaceHotbar = new InterfaceHotbar();
        ConfigurationSection hotbarSettings = config.getConfigurationSection("hotbar-settings");
        int autoRefresh = hotbarSettings.getInt("auto-refresh");
        boolean giveOnSpawn = hotbarSettings.getBoolean("give-on-spawn");

        interfaceHotbar.setAutoRefresh(autoRefresh);
        interfaceHotbar.setGiveOnSpawn(giveOnSpawn);

        for (String sectionName : config.getKeys(false)) {
            if (!sectionName.equals("hotbar-settings")) {
                if (config.isConfigurationSection(sectionName)) {
                    ConfigurationSection itemSection = config.getConfigurationSection(sectionName);
                    
                    itemConfigProcessor.process(interfaceHotbar, itemSection);
                }
            }
        }

        api.addConfiguredHotbar(menuName, interfaceHotbar);
    }
}
