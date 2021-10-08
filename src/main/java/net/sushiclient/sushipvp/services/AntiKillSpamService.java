package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import net.sushiclient.sushipvp.events.SuicideEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class AntiKillSpamService extends Service {

    private final WeakHashMap<Player, Integer> players = new WeakHashMap<>();

    @Override
    public void onTick(int tick) {
        if (tick % 20 != 0) return;
        for (Map.Entry<Player, Integer> entry : new HashMap<>(players).entrySet()) {
            players.put(entry.getKey(), Math.max(0, entry.getValue() - 20));
        }
    }

    @EventHandler
    public void onSuicide(SuicideEvent e) {
        if (players.getOrDefault(e.getCommandSender(), 0) == 0) {
            players.put(e.getCommandSender(), getConfig().getKillCommandThreshold());
        } else {
            e.setCancelled(true);
        }
    }
}
