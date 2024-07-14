package fr.nkw.zidemcore.Event;

import fr.nkw.zidemcore.Items.Pickaxe;
import fr.nkw.zidemcore.Main;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MineHandler implements Listener {

    public static void updateBlockForPlayers(Block b) {
        for (Player player : Main.getInstance().getServer().getOnlinePlayers()) {
            player.sendBlockChange(b.getLocation(), b.getType(), b.getData());
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendBlockChange(b.getLocation(), b.getType(), b.getData());
                }
            }.runTaskLater(Main.getInstance(), 1L);
        }
    }

    public static void resetMine() {
        World world = Main.getInstance().getServer().getWorld("minage");
        if (world == null) {
            return;
        }

        // TODO store mine datas (x,y,z,x2,y2,z2,blocks,x3,y3,z3)
        for (int x = -1; x <= 3; x++) {
            for (int y = 1; y <= 4; y++) {
                for (int z = 5; z <= 9; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    int isCobblestone = (int) (Math.random() * 10);
                    block.setType(isCobblestone == 0 ? Material.STONE : Material.COBBLESTONE);
                    updateBlockForPlayers(block);
                }
            }
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
}
