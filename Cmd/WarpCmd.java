package fr.nkw.zidemcore.Cmd;

import fr.nkw.zidemcore.Worlds.Worlds;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (!(sender instanceof Player player) || args.length < 1) {
            return false;
        }

        String warpName = args[0].toLowerCase();

        if (warpName.equals(Worlds.WORLD_NAME_LIBRE)) { // TODO condition
            player.teleport(Bukkit.getWorld(Worlds.WORLD_NAME_LIBRE).getSpawnLocation());
            return true;
        } else if (warpName.equals(Worlds.WORLD_NAME_MINAGE) || warpName.equals("mine")) {
            player.teleport(Bukkit.getWorld(Worlds.WORLD_NAME_MINAGE).getSpawnLocation());
            return true;
        }

        World world = Bukkit.getWorld(warpName);
        if (world != null) {
            player.teleport(world.getSpawnLocation());
            return true;
        }
        player.sendMessage("§cCe warp n'existe pas");

        return true;
    }
}
