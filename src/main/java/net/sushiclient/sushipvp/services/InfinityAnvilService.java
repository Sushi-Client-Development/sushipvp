package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InfinityAnvilService extends Service {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!getConfig().isInfinityAnvilEnabled()) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block block = e.getClickedBlock();
        if (block.getType() != Material.ANVIL) return;
        block.setType(Material.ANVIL);
    }
}
