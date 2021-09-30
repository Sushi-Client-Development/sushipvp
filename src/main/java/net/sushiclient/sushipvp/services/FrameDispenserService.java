package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FrameDispenserService extends Service {

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!getConfig().isFrameDispenserEnabled()) return;
        Entity attacker = e.getDamager();
        if (!(e.getEntity() instanceof ItemFrame)) return;
        if (!(attacker instanceof Player)) return;
        if (((Player) attacker).getGameMode() == GameMode.CREATIVE) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        if (!getConfig().isFrameDispenserEnabled()) return;
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;

        Entity target = e.getRightClicked();
        if (!(target instanceof ItemFrame)) return;
        e.setCancelled(true);

        ItemStack itemStack = ((ItemFrame) target).getItem();
        if (itemStack.getType() == Material.AIR) return;
        itemStack.setAmount(itemStack.getType().getMaxStackSize());

        Inventory inventory = Bukkit.createInventory(null, 9 * 3);
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, itemStack);
        }
        e.getPlayer().openInventory(inventory);
    }
}
