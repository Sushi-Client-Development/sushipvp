package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AntiVoidService extends Service {
    @Override
    public void onTick(int tick) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().getY() > 0) continue;
            player.damage(5000);
        }
    }
}
