package fr.nkw.zidemcore.Event;

import fr.nkw.zidemcore.Items.Pickaxe;
import fr.nkw.zidemcore.Worlds.Worlds;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PlayerHandler implements Listener {
    @EventHandler
    public static void Join(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setGameMode(GameMode.SURVIVAL);
        player.setFoodLevel(20);
        player.setHealth(20);

        UUID playerUUID = player.getUniqueId();
        if (!player.hasPlayedBefore()) { // New player
            World tutoWorld = Bukkit.getWorld(Worlds.WORLD_NAME_TUTO);
            if (tutoWorld != null) {
                Location spawnLocation = tutoWorld.getSpawnLocation();
                player.teleport(spawnLocation);
            }
            player.sendMessage("§aBienvenue à " + player.getDisplayName() + " sur le serveur !");
        } else {
            event.setJoinMessage("§8[§2+§8]§r §7" + player.getDisplayName());

            World mineWorld = Bukkit.getWorld(Worlds.WORLD_NAME_MINAGE);
            Location location = mineWorld.getSpawnLocation();
            player.teleport(location);
        }

        StringBuilder newsMessage = new StringBuilder("§l§7Les utilisateurs connectés: §r§7");
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        List<? extends Player> playerList = new ArrayList<>(players);

        for (int i = 0; i < playerList.size(); i++) {
            Player p = playerList.get(i);
            if (i > 0) {
                newsMessage.append(", ");
            }
            newsMessage.append(p.getDisplayName());
            if (p == player) {
                newsMessage.append(" (moi)");
            }
        }
        newsMessage.append(".");

        event.getPlayer().sendMessage(newsMessage.toString());

        Pickaxe pickaxe = new Pickaxe(player);
        pickaxe.generateDefaultPickaxe();
    }

    @EventHandler
    public void Quit(PlayerQuitEvent event) {
        event.setQuitMessage("[-] " + event.getPlayer().getDisplayName());
    }

    @EventHandler
    public static void Message(AsyncPlayerChatEvent event) {
        event.setFormat("§l§2Membre ● §r§l§7%s§r §l§0❘§r§4P0§l§0❘§r §l§0❘§r§2A§l§0❘§r§8: §7%s");
        String msg = event.getMessage().replaceAll("&", "§");

        if (msg.toLowerCase().contains("[item]")) {
            Player player = event.getPlayer();
            ItemMeta item = player.getInventory().getItemInHand().getItemMeta();
            if (item.hasDisplayName()) {
                msg = msg.replace("[item]", "§7" + item.getDisplayName());
            } else {
                msg = msg.replace("[item]", "§7" + player.getInventory().getItemInHand().getType().name());
            }
        }

        event.setMessage(msg);
    }

}
