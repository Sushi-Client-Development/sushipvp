package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class CombatRestrictionEnforceService extends Service {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getMaterial() != Material.END_CRYSTAL) return;
        if (e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getY() < getConfig().getCombatY() - 1) return;
        e.setCancelled(true);
    }
}
