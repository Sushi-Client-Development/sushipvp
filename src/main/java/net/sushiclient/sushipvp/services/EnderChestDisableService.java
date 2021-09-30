package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class EnderChestDisableService extends Service {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (getConfig().isEnderChestEnabled()) return;
        if (e.getInventory().getType() != InventoryType.ENDER_CHEST) return;
        e.setCancelled(true);
    }
}
