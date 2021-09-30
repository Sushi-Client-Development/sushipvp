package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVisionService extends Service {

    @Override
    public void onTick(int tick) {
        if (!getConfig().isNightVisionEnabled()) return;
        for (World world : Bukkit.getWorlds()) {
            for (Player player : world.getPlayers()) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000, 0, false, false));
            }
        }
    }
}
