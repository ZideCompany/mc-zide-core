package fr.nkw.zidemcore.Entity.Mine;

import fr.nkw.zidemcore.Entity.Material.MaterialConverter;
import fr.nkw.zidemcore.Event.PlayerHandler;
import fr.nkw.zidemcore.Main;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Mine {
    // Mine name
    public String name;

    // Mine size
    public int x1;
    public int x2;
    public int y1;
    public int y2;
    public int z1;
    public int z2;

    // Spawn point
    public int sx;
    public int sy;
    public int sz;

    public List<MineBlock> blocks = new ArrayList<MineBlock>();

    public Mine(String name) {
        this.name = name;

        switch (name) {
            case "a":
                loadMineA();
                break;
            default:
                x1 = 0;
                x2 = 0;
                y1 = 0;
                y2 = 0;
                z1 = 0;
                z2 = 0;
                sx = 0;
                sy = 0;
                sz = 0;
                break;
        }
    }

    public void resetMine() {
        World world = Main.getInstance().getServer().getWorld("minage");
        if (world == null) {
            return;
        }

        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    double randomNum = Math.random();

                    for (MineBlock mineBlock : blocks) {
                        randomNum -= mineBlock.probability;
                        if (randomNum <= 0) {
                            block.setType(MaterialConverter.stringToMaterial(mineBlock.blockName));
                            break;
                        }
                    }


                    PlayerHandler.updateBlockForPlayers(block);
                }
            }
        }
    }

    private boolean isAllDataLoaded() {
        return x1 != 0 && x2 != 0 && y1 != 0 && y2 != 0 && z1 != 0 && z2 != 0 && sx != 0 && sy != 0 && sz != 0;
    }

    private void loadMineA() {
        x1 = Main.getInstance().getConfig().getInt("worlds.minage.mines.a.x1");
        x2 = Main.getInstance().getConfig().getInt("worlds.minage.mines.a.x2");
        y1 = Main.getInstance().getConfig().getInt("worlds.minage.mines.a.y1");
        y2 = Main.getInstance().getConfig().getInt("worlds.minage.mines.a.y2");
        z1 = Main.getInstance().getConfig().getInt("worlds.minage.mines.a.z1");
        z2 = Main.getInstance().getConfig().getInt("worlds.minage.mines.a.z2");
        sx = Main.getInstance().getConfig().getInt("worlds.minage.mines.a.sx");
        sy = Main.getInstance().getConfig().getInt("worlds.minage.mines.a.sy");
        sz = Main.getInstance().getConfig().getInt("worlds.minage.mines.a.sz");

        if (!isAllDataLoaded()) {
            x1 = -1;
            x2 = 3;
            y1 = 1;
            y2 = 4;
            z1 = 5;
            z2 = 9;
            sx = 1;
            sy = 6;
            sz = 4;
        }

        blocks = (List<MineBlock>) Main.getInstance().getConfig().getList("worlds.minage.mines.a.blocks");

        if (blocks == null) {
            blocks = new ArrayList<MineBlock>();

            blocks.add(new MineBlock(MaterialConverter.materialToString(Material.COBBLESTONE), 0.95));
            blocks.add(new MineBlock(MaterialConverter.materialToString(Material.STONE), 0.05));
        }

        saveInConfig();
    }

    public void saveInConfig() {
        Main.getInstance().getConfig().set("worlds.minage.mines.a.blocks", blocks);

        Main.getInstance().getConfig().set("worlds.minage.mines.a.x1", x1);
        Main.getInstance().getConfig().set("worlds.minage.mines.a.x2", x2);
        Main.getInstance().getConfig().set("worlds.minage.mines.a.y1", y1);
        Main.getInstance().getConfig().set("worlds.minage.mines.a.y2", y2);
        Main.getInstance().getConfig().set("worlds.minage.mines.a.z1", z1);
        Main.getInstance().getConfig().set("worlds.minage.mines.a.z2", z2);
        Main.getInstance().getConfig().set("worlds.minage.mines.a.sx", sx);
        Main.getInstance().getConfig().set("worlds.minage.mines.a.sy", sy);
        Main.getInstance().getConfig().set("worlds.minage.mines.a.sz", sz);

        Main.getInstance().saveConfig();
    }
}
