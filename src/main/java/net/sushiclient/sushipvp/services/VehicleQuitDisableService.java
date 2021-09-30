package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class VehicleQuitDisableService extends Service {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (getConfig().isVehicleQuitEnabled()) return;
        Entity vehicle = e.getPlayer().getVehicle();
        if (vehicle == null) return;
        vehicle.eject();
    }
}
