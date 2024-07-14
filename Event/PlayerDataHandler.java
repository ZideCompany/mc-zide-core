package fr.nkw.zidemcore.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerDataHandler implements Listener {
    private final Map<String, ItemStack[]> playerInventories = new HashMap<>();

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        this.playerInventories.put(playerName, player.getInventory().getContents());

        if (this.playerInventories.containsKey(playerName)) {
            player.getInventory().setContents(this.playerInventories.get(playerName));
        }
    }
}
