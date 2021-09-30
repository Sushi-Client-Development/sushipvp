package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class ClearInventoryService extends Service {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!(getConfig().isClearInventoryEnabled())) return;
        e.getPlayer().getInventory().clear();
    }
}
