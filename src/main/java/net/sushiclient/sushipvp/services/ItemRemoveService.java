package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.WeakHashMap;

public class ItemRemoveService extends Service {
    private final WeakHashMap<Item, Integer> removeTicks = new WeakHashMap<>();

    private boolean isPlayerNearby(Location loc) {
        for (Player player : loc.getWorld().getPlayers()) {
            double distSq = player.getLocation().distanceSquared(loc);
            if (distSq < getConfig().getKeepRadius()) return true;
        }
        return false;
    }

    @Override
    public void onTick(int tick) {
        if (tick % 20 != 0) return;
        for (World world : Bukkit.getWorlds()) {
            for (Item item : world.getEntitiesByClass(Item.class)) {
                int ticks = removeTicks.getOrDefault(item, getConfig().getRemoveTicks()) - 20;
                if (ticks <= 0 || !isPlayerNearby(item.getLocation())) {
                    removeTicks.remove(item);
                    item.remove();
                } else {
                    removeTicks.put(item, ticks);
                }
            }
        }
    }
}
