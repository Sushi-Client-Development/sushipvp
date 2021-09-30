package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class AntiHoleTpService extends Service {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE ||
                e.getPlayer().getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        double dist = e.getTo().distanceSquared(e.getFrom());
        if (dist < Math.pow(getConfig().getTeleportThreshold(), 2)) return;
        e.setCancelled(true);
    }
}
