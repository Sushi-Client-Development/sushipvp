package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class AntiOffhandCrashService extends Service {

    private final WeakHashMap<Player, Integer> players = new WeakHashMap<>();

    @Override
    public void onTick(int tick) {
        if (tick % 20 != 0) return;
        for (Map.Entry<Player, Integer> entry : new HashMap<>(players).entrySet()) {
            players.put(entry.getKey(), Math.max(0, entry.getValue() - 1));
        }
    }

    @EventHandler
    public void onSwapHandItems(PlayerSwapHandItemsEvent e) {
        Player player = e.getPlayer();
        int count = players.getOrDefault(player, 0) + 1;
        if (count > getConfig().getOffhandThreshold()) {
            players.remove(player);
            player.kickPlayer(null);
        } else {
            players.put(player, count);
        }
    }
}
