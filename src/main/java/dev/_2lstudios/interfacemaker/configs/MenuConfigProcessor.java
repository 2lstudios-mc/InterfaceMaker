package dev._2lstudios.interfacemaker.configs;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import dev._2lstudios.interfacemaker.interfaces.InterfaceInventory;
import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMakerAPI;

public class MenuConfigProcessor {
    private InterfaceMakerAPI api;
    private Server server;

    public MenuConfigProcessor(InterfaceMakerAPI api, Server server) {
        this.api = api;
        this.server = server;
    }

    public void process(String menuName, Configuration config) {
        InterfaceInventory interfaceInventory = new InterfaceInventory(api, server);
        ConfigurationSection menuSettings = config.getConfigurationSection("menu-settings");
        String title = menuSettings.getString("name");
        int rows = menuSettings.getInt("rows");
        List<String> commands = menuSettings.getStringList("commands");
        int autoRefresh = menuSettings.getInt("auto-refresh");
        List<String> openActions = menuSettings.getStringList("open-actions");

        interfaceInventory.setTitle(title);
        interfaceInventory.setRows(rows);
        interfaceInventory.setCommands(commands);
        interfaceInventory.setAutoRefresh(autoRefresh);
        interfaceInventory.setOpenActions(openActions);

        if (menuSettings.contains("open-with-item")) {
            ConfigurationSection openWithItem = menuSettings.getConfigurationSection("open-with-item");
            String materialName = openWithItem.getString("material");
            Material material = Material.getMaterial(materialName);
            boolean leftClick = openWithItem.getBoolean("left-click");
            boolean rightClick = openWithItem.getBoolean("right-click");

            if (material != null) {
                interfaceInventory.setOpenWithItem(material, leftClick, rightClick);
            }
        }

        for (String sectionName : menuSettings.getKeys(false)) {
            if (!sectionName.equals("open-with-item")) {
                if (menuSettings.isConfigurationSection(sectionName)) {
                    ConfigurationSection itemSection = menuSettings.getConfigurationSection(sectionName);
                    InterfaceItem interfaceItem = new InterfaceItem();
                    String materialName = itemSection.getString("material");
                    Material material = Material.getMaterial(materialName);
                    int durability = itemSection.getInt("durability");
                    int positionX = itemSection.getInt("position-x") - 1;
                    int positionY = itemSection.getInt("position-y") - 1;
                    int slot = positionX + (positionY * 8);
                    String name = itemSection.getString("name");
                    List<String> lore = itemSection.getStringList("lore");
                    List<String> enchantments = itemSection.getStringList("enchantments");
                    boolean keepOpen = itemSection.getBoolean("keep-open");
                    String permission = itemSection.getString("permission");
                    String viewPermission = itemSection.getString("view-permission");
                    String permissionMessage = itemSection.getString("permission-message");
                    List<String> requiredItems = itemSection.getStringList("required-items");
                    int levels = itemSection.getInt("levels");
                    int price = itemSection.getInt("price");
                    List<String> actions = itemSection.getStringList("actions");

                    if (material != null) {
                        interfaceItem.setType(material);
                        interfaceItem.setDurability(durability);
                        interfaceItem.setName(name);
                        interfaceItem.setLore(lore);
                        interfaceItem.setEnchantments(enchantments);
                        interfaceItem.setKeepOpen(keepOpen);
                        interfaceItem.setPermission(permission);
                        interfaceItem.setViewPermission(viewPermission);
                        interfaceItem.setPermissionMessage(permissionMessage);
                        interfaceItem.setRequiredItems(requiredItems);
                        interfaceItem.setLevels(levels);
                        interfaceItem.setPrice(price);
                        interfaceItem.setActions(actions);
                        interfaceInventory.setItem(slot, interfaceItem);
                    }
                }
            }
        }

        api.addConfiguredInventory(menuName, interfaceInventory);
    }
}