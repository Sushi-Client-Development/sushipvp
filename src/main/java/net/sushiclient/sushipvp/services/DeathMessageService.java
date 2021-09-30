package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import net.sushiclient.sushipvp.events.DeathMessagesToggleEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class DeathMessageService extends Service {

    private final Set<UUID> hidden = Collections.synchronizedSet(new HashSet<>());

    private File getConfigFile() {
        return new File(getPlugin().getDataFolder(), "deathmsgs.txt");
    }

    private synchronized void loadAll() {
        hidden.clear();
        File configFile = getConfigFile();
        try {
            List<String> lines = Files.readAllLines(configFile.toPath());
            for (String line : lines) {
                hidden.add(UUID.fromString(line));
            }
        } catch (IOException e) {
            configFile.delete();
        }
    }

    public synchronized void saveAll() {
        Set<String> lines = new HashSet<>(hidden).stream()
                .map(UUID::toString)
                .collect(Collectors.toSet());
        File configFile = getConfigFile();
        try {
            Files.write(configFile.toPath(), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            configFile.delete();
        }
    }

    @Override
    public void onEnabled() {
        loadAll();
    }

    @EventHandler
    public void onDeathMessagesToggleEvent(DeathMessagesToggleEvent e) {
        if (hidden.contains(e.getCommandSender().getUniqueId())) {
            hidden.remove(e.getCommandSender().getUniqueId());
            e.getCommandSender().sendMessage(ChatColor.GOLD + "Death messages are now shown");
        } else {
            hidden.add(e.getCommandSender().getUniqueId());
            e.getCommandSender().sendMessage(ChatColor.GOLD + "Death messages are now hidden");
        }
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), this::saveAll);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        String deathMessage = e.getDeathMessage();
        deathMessage = ChatColor.DARK_RED + deathMessage;
        Player victim = e.getEntity();
        Player killer = victim.getKiller();
        deathMessage = deathMessage.replace(victim.getName(), ChatColor.DARK_AQUA + victim.getName() + ChatColor.DARK_RED);
        if (killer != null) {
            deathMessage = deathMessage.replace(killer.getName(), ChatColor.DARK_AQUA + killer.getName() + ChatColor.DARK_RED);
        }

        e.setDeathMessage(null);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!hidden.contains(player.getUniqueId())) {
                player.sendMessage(deathMessage);
            }
        }
    }
}
