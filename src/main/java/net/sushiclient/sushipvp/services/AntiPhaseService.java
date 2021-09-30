package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.WeakHashMap;

public class AntiPhaseService extends Service {

    private final WeakHashMap<Player, Integer> ticks = new WeakHashMap<>();
    private static final Vector Y = new Vector(0, 0.8, 0);
    private static final Vector Y_2 = new Vector(0, 1.6, 0);

    private boolean isInBlock(Location loc) {

        Vector origin = loc.toVector();
        Vector[] check = new Vector[]{origin, origin.clone().add(Y), origin.clone().add(Y_2)};

        for (Vector vec : check) {
            Block block = loc.getWorld().getBlockAt(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ());
            if (block.getType() == Material.BEDROCK || block.getType() == Material.OBSIDIAN) return true;
        }
        return false;
    }

    @Override
    public void onTick(int tick) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() == GameMode.CREATIVE) continue;
            if (player.getGameMode() == GameMode.SPECTATOR) continue;
            int count = ticks.getOrDefault(player, 0);
            if (isInBlock(player.getLocation())) {
                ticks.put(player, count + 2);
                if (count > getConfig().getAntiPhaseDelay() * 2) {
                    for (int y = 0; y < 10; y++) {
                        Location delta = player.getLocation().add(0, y, 0);
                        if (!isInBlock(delta)) {
                            player.teleport(delta);
                            break;
                        }
                    }
                    ticks.remove(player);
                }
            } else if (count == 0) {
                ticks.remove(player);
            } else {
                ticks.put(player, count - 1);
            }
        }
    }
}
