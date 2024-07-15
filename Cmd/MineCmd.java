package fr.nkw.zidemcore.Cmd;

import fr.nkw.zidemcore.Entity.Mine.Mine;
import fr.nkw.zidemcore.Event.MineHandler;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MineCmd implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        // TODO cooldown system

        if (sender instanceof Player player) {
            if (cmd == null || !cmd.getName().equalsIgnoreCase("rm")) {
                return false;
            }

            World playerWorld = player.getWorld();
            if (!playerWorld.getName().equals("minage")) {
                return false;
            }

            Mine[] mines = MineHandler.getMines();
            boolean isPlayerInMine = false;

            for (Mine mine : mines) {
                if (
                    player.getLocation().getBlockX() >= mine.x1 - 1 && player.getLocation().getBlockX() <= mine.x2 + 1 &&
                    player.getLocation().getBlockY() >= mine.y1 - 1 && player.getLocation().getBlockY() <= mine.y2 + 1 &&
                    player.getLocation().getBlockZ() >= mine.z1 - 1 && player.getLocation().getBlockZ() <= mine.z2 + 1
                ) {
                isPlayerInMine = true;
                player.teleport(new Location(playerWorld, 1, 6, 4));
                mine.resetMine();
                }
            }

            if (!isPlayerInMine) {
                player.sendMessage("Vous n'êtes pas dans une mine");
            }
        }

        return false;
    }
}
