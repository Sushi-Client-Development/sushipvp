package net.sushiclient.sushipvp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SuicideEvent extends Event implements Cancellable {

    private final Player commandSender;
    private boolean cancelled;

    public SuicideEvent(Player commandSender) {
        this.commandSender = commandSender;
    }

    public Player getCommandSender() {
        return commandSender;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
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
