package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnLocationService extends Service {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Location spawnLocation = getConfig().getSpawnLocation();
        if (spawnLocation == null) return;
        e.getPlayer().teleport(spawnLocation);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Location spawnLocation = getConfig().getSpawnLocation();
        if (spawnLocation == null) return;
        e.setRespawnLocation(spawnLocation);
    }
}
