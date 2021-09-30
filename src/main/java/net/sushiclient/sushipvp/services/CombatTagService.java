package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.*;

public class CombatTagService extends Service {
    private final WeakHashMap<Player, Location> locations = new WeakHashMap<>();
    private final WeakHashMap<Player, Integer> taggedPlayers = new WeakHashMap<>();
    private final Set<Player> warned = Collections.newSetFromMap(new WeakHashMap<>());

    @Override
    public void onTick(int tick) {
        World world = getConfig().getCombatWorld();
        if (world == null) return;

        // update timer
        for (Map.Entry<Player, Integer> entry : new HashMap<>(taggedPlayers).entrySet()) {
            int ticks = entry.getValue() - 1;
            if (ticks <= 0) {
                taggedPlayers.remove(entry.getKey());
                locations.remove(entry.getKey());
            } else {
                taggedPlayers.put(entry.getKey(), ticks);
            }
        }

        // update locations
        for (Player player : world.getPlayers()) {
            if (!taggedPlayers.containsKey(player)) {
                locations.remove(player);
                continue;
            }

            Location loc = player.getLocation();
            if (loc.getY() < getConfig().getCombatY()) {
                if (!player.getLocation().equals(locations.get(player))) {
                    warned.remove(player);
                }
                locations.put(player, loc);
            } else if (locations.containsKey(player)) {
                Location lastLoc = locations.get(player);
                player.teleport(lastLoc);
                if (!warned.contains(player)) {
                    player.sendMessage(ChatColor.RED + "You are combat tagged for " + taggedPlayers.get(player) / 20 + " seconds");
                    warned.add(player);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        taggedPlayers.put((Player) e.getEntity(), getConfig().getCombatTicks());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        taggedPlayers.remove(e.getEntity());
        locations.remove(e.getEntity());
    }
}
