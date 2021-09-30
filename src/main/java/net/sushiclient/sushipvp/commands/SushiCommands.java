package net.sushiclient.sushipvp.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import net.sushiclient.sushipvp.SushiConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

@CommandAlias("sushipvp")
public class SushiCommands extends BaseCommand {

    private final Plugin plugin;
    private final SushiConfig sushiConfig;

    public SushiCommands(Plugin plugin, SushiConfig sushiConfig) {
        this.plugin = plugin;
        this.sushiConfig = sushiConfig;
    }

    @Subcommand("reload")
    @CommandPermission("sushipvp.reload")
    public void onReload() {
        plugin.reloadConfig();
        sushiConfig.load(plugin.getConfig());
        plugin.saveConfig();
    }

    @Subcommand("blocklist")
    @CommandPermission("sushipvp.blocklist")
    public void onBlockList(CommandSender sender) {
        sender.sendMessage("Blocked messages:");
        for (String str : sushiConfig.getBlockedMessages()) {
            sender.sendMessage(ChatColor.GOLD + "  " + str);
        }
    }

    @Subcommand("block")
    @CommandPermission("sushipvp.block")
    public void onBlock(String reg) {
        ArrayList<String> messages = new ArrayList<>(sushiConfig.getBlockedMessages());
        messages.add(reg);
        sushiConfig.setBlockedMessages(messages);
        sushiConfig.save(plugin.getConfig());
        plugin.saveConfig();
    }

    @Subcommand("unblock")
    @CommandPermission("sushipvp.unblock")
    public void onUnblock(String reg) {
        ArrayList<String> messages = new ArrayList<>(sushiConfig.getBlockedMessages());
        messages.remove(reg);
        sushiConfig.setBlockedMessages(messages);
        sushiConfig.save(plugin.getConfig());
        plugin.saveConfig();
    }
}
