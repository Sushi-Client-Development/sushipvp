package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.util.Vector;

import java.util.WeakHashMap;

public class VehicleMoveDisableService extends Service {
    private final WeakHashMap<Entity, Location> locations = new WeakHashMap<>();

    @Override
    public void onTick(int tick) {
        if (getConfig().isVehicleMoveEnabled()) return;
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof Minecart)) continue;
                Location from = locations.get(entity);
                if (from != null) {
                    if (entity.getLocation().distanceSquared(from) > 0.3) {
                        entity.eject();
                        entity.teleport(from);
                        entity.setVelocity(new Vector(0, 0, 0));
                    }
                } else {
                    locations.put(entity, entity.getLocation());
                }
            }
        }
    }

    @EventHandler
    public void onAttack(VehicleDamageEvent e) {
        if (getConfig().isVehicleMoveEnabled()) return;
        if (!(e.getAttacker() instanceof Player)) return;
        Player attacker = (Player) e.getAttacker();
        if (attacker.getGameMode() == GameMode.CREATIVE) return;
        e.setCancelled(true);
    }
}
