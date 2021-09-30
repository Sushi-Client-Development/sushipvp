package net.sushiclient.sushipvp.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.sushiclient.sushipvp.events.SuicideEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("kill")
public class KillCommand extends BaseCommand {

    @Default
    public void onDefault(CommandSender sender) {
        if (!(sender instanceof Player)) return;
        SuicideEvent event = new SuicideEvent((Player) sender);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:kill " + sender.getName());
        }
    }
}
