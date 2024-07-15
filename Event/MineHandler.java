package fr.nkw.zidemcore.Event;

import fr.nkw.zidemcore.Entity.Mine.Mine;
import fr.nkw.zidemcore.Items.Pickaxe;
import fr.nkw.zidemcore.Main;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MineHandler implements Listener {
    private static final Mine[] mines = new Mine[] {
            new Mine("a"),
    };

    public static void resetMines() {
        for (Mine mine : mines) {
            mine.resetMine();
        }
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        World world = player.getWorld();

        if (world.getName().equals("minage") || world.getName().equals("tuto")) {
            event.setCancelled(true);

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendBlockChange(block.getLocation(), block.getType(), block.getData());
                }
            }.runTaskLater(Main.getInstance(), 1L);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        World world = player.getWorld();

        if (world.getName().equals("minage")) {
            int x = block.getX();
            int y = block.getY();
            int z = block.getZ();

            if (x >= -1 && x <= 3 && y >= 1 && y <= 4 && z >= 5 && z <= 9) {
                Pickaxe pickaxe = Pickaxe.parseItem(player, player.getInventory().getItemInHand());
                if (pickaxe != null) {
                    pickaxe.addXP(1);
                    pickaxe.saveData(player.getInventory().getItemInHand());
                }

                return;
            }
        }

        event.setCancelled(true);
        player.sendBlockChange(block.getLocation(), block.getType(), block.getData());
        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendBlockChange(block.getLocation(), block.getType(), block.getData());
            }
        }.runTaskLater(Main.getInstance(), 1L);
    }

    public static void autoResetMines() {
        resetMines();

        new BukkitRunnable() {
            @Override
            public void run() {
                resetMines();
            }
        }.runTaskTimer(Main.getInstance(), 0L, 20 * 60 * 15);
    }

    public static Mine[] getMines() {
        return mines;
    }
}
