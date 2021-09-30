package net.sushiclient.sushipvp.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.sushiclient.sushipvp.events.DeathMessagesToggleEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("toggledeathmsgs")
public class ToggleDeathMessagesCommand extends BaseCommand {

    @Default
    public void onDefault(CommandSender sender) {
        if (sender instanceof Player) {
            Bukkit.getPluginManager().callEvent(new DeathMessagesToggleEvent((Player) sender));
        }
    }
}
