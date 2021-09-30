package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BedrockProtectService extends Service {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!(getConfig().isBedrockProtectionEnabled())) return;
        if (e.getBlock().getType() != Material.BEDROCK) return;
        e.setCancelled(true);
    }
}
