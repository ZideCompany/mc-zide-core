package fr.nkw.zidemcore.Worlds;

import fr.nkw.zidemcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class Worlds {
    public static final String WORLD_NAME_OLD = "world";
    public static final String WORLD_NAME_NETHER_OLD = "world_nether";
    public static final String WORLD_NAME_THE_END_OLD = "world_the_end";

    public static final String WORLD_NAME_TUTO = "tuto";
    public static final String WORLD_NAME_MINAGE = "minage";
    public static final String WORLD_NAME_LIBRE = "libre";

    private static void SaveWorldInConfig (World w) {
        String name = w.getName();
        Main.getInstance().getConfig().set("worlds." + name + ".uuid", w.getUID().toString());
        Main.getInstance().getConfig().set("worlds." + name + ".name", name);
    }

    public static void init() {
        // create worlds
        if (Bukkit.getWorld(WORLD_NAME_TUTO) == null) {
            // flat world with no structures
            SaveWorldInConfig(Bukkit.createWorld(
                    WorldCreator
                            .name(WORLD_NAME_TUTO)
                            .type(WorldType.FLAT)
                            .generateStructures(false)
                            .environment(org.bukkit.World.Environment.NORMAL)
            ));
        }
        if (Bukkit.getWorld(WORLD_NAME_MINAGE) == null) {
            SaveWorldInConfig(Bukkit.createWorld(WorldCreator
                    .name(WORLD_NAME_MINAGE)
                    .type(WorldType.FLAT)
                    .generateStructures(false)
                    .environment(org.bukkit.World.Environment.NORMAL)
            ));
        }
        if (Bukkit.getWorld(WORLD_NAME_LIBRE) == null) {
            SaveWorldInConfig(Bukkit.createWorld(WorldCreator
                    .name(WORLD_NAME_LIBRE)
                    .type(WorldType.NORMAL)
                    .generateStructures(true)
                    .environment(World.Environment.NORMAL)
            ));
        }

        Main.getInstance().saveConfig();

        // unload old worlds
        if (Bukkit.getWorld(WORLD_NAME_OLD) != null) {
            Bukkit.unloadWorld(WORLD_NAME_OLD, false);
        }
        if (Bukkit.getWorld(WORLD_NAME_NETHER_OLD) != null) {
            Bukkit.unloadWorld(WORLD_NAME_NETHER_OLD, false);
        }
        if (Bukkit.getWorld(WORLD_NAME_THE_END_OLD) != null) {
            Bukkit.unloadWorld(WORLD_NAME_THE_END_OLD, false);
        }
    }


}
