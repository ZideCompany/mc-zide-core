package fr.nkw.zidemcore.Cmd;

import fr.nkw.zidemcore.Event.MineHandler;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Mine implements CommandExecutor {
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

            // TODO check if the player is in a mine, and which mine

            player.teleport(new Location(playerWorld, 1, 6, 4));

            MineHandler.resetMine();
        }

        return false;
    }
}
