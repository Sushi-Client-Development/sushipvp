package net.sushiclient.sushipvp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DeathMessagesToggleEvent extends Event {
    private final Player commandSender;

    public DeathMessagesToggleEvent(Player commandSender) {
        this.commandSender = commandSender;
    }

    public Player getCommandSender() {
        return commandSender;
    }

    private static final HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
