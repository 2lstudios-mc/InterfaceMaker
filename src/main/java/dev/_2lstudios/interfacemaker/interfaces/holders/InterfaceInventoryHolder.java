package dev._2lstudios.interfacemaker.interfaces.holders;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;

public class InterfaceInventoryHolder extends InterfaceItemHolder implements InventoryHolder {
    private Inventory inventory;
    private String title;
    private int size;

    public String getTitle() {
        return title;
    }

    public InterfaceInventoryHolder setTitle(String title) {
        this.title = title;
        return this;
    }

    public InterfaceInventoryHolder(int inventorySize, String title) {
        this.size = inventorySize;
        this.title = title;
    }

    public InterfaceInventoryHolder setRows(int rows) {
        this.size = rows * 9;
        return this;
    }

    public int getSize() {
        return size;
    }

    public InterfaceInventoryHolder fill(int gap, InterfaceItem ...items) {
        int firstSlot = gap * 8 + gap * 2;
        int itemIndex = 1;

        for (int slot = firstSlot; slot < size; slot++) {
            if (itemIndex - 1 >= items.length) {
                break;
            }

            setItem(slot, items[itemIndex - 1]);

            if (itemIndex % (9 - gap * 2) == 0) {
                slot += (gap * 2);
            }

            itemIndex++;
        }
        return this;
    }

    public InterfaceInventoryHolder fillEmpty(InterfaceItem item) {
        for (int i = 0; i < size; i++) {
            if (!hasItem(i)) {
                setItem(i, item);
            }
        }
        return this;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
