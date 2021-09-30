package net.sushiclient.sushipvp.services;

import net.sushiclient.sushipvp.Service;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Pattern;

public class MessageBlockService extends Service {

    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent e) {
        for (String reg : getConfig().getBlockedMessages()) {
            Pattern pattern = Pattern.compile(reg);
            if (pattern.matcher(e.getMessage()).find()) e.setCancelled(true);
        }
    }
}
