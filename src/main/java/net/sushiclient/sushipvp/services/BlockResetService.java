package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import java.util.HashMap;
import java.util.Map;

public class BlockResetService extends Service {

    private final HashMap<Location, Integer> blocks = new HashMap<>();

    @Override
    public void onDisabled() {
        for (Location location : blocks.keySet()) {
            location.getBlock().setType(Material.AIR);
        }
        blocks.clear();
    }

    @Override
    public void onTick(int tick) {
        for (Map.Entry<Location, Integer> entry : new HashMap<>(blocks).entrySet()) {
            Location loc = entry.getKey();
            int ticks = entry.getValue() - 1;
            if (ticks <= 0) {
                boolean canRemove = true;
                for (Entity entity : loc.getWorld().getNearbyEntities(loc, 10, 10, 10)) {
                    if (entity instanceof Player) {
                        canRemove = false;
                        break;
                    }
                }
                if (canRemove) {
                    blocks.remove(loc);
                    loc.getBlock().setType(Material.AIR);
                } else {
                    // reset timer
                    add(loc);
                }
            } else {
                blocks.put(loc, ticks);
            }
        }
    }

    private void add(Location loc) {
        double d = 1 - loc.getY() / getConfig().getBuildY();
        blocks.put(loc, (int) (Math.max(getConfig().getBlockResetDelay() * d, 1200)));
    }

    @EventHandler
    public void onBlockExtend(BlockPistonExtendEvent e) {
        for (Block block : e.getBlocks()) {
            add(block.getLocation());
        }
    }

    @EventHandler
    public void onBlockRetract(BlockPistonRetractEvent e) {
        for (Block block : e.getBlocks()) {
            add(block.getLocation());
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent e) {
        add(e.getBlock().getLocation());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        add(e.getBlock().getLocation());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        blocks.remove(e.getBlock().getLocation());
    }
}
