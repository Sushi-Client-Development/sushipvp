package net.sushiclient.sushipvp;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Service implements Listener {

    private Plugin plugin;
    private SushiConfig sushiConfig;
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean b) {
        if (enabled == b) return;
        enabled = b;
        if (enabled) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
            onEnabled();
        } else {
            HandlerList.unregisterAll(this);
            onDisabled();
        }
    }

    public void onEnabled() {

    }

    public void onDisabled() {

    }

    public void onTick(int tick) {

    }

    public Plugin getPlugin() {
        return plugin;
    }

    public SushiConfig getConfig() {
        return sushiConfig;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public void setConfig(SushiConfig sushiConfig) {
        this.sushiConfig = sushiConfig;
    }
}
