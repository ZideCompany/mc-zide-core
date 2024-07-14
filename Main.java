package fr.nkw.zidemcore;

import fr.nkw.zidemcore.Cmd.Mine;
import fr.nkw.zidemcore.Cmd.Warp;
import fr.nkw.zidemcore.Event.PlayerHandler;
import fr.nkw.zidemcore.Event.MineHandler;
import fr.nkw.zidemcore.Event.PlayerDataHandler;
import fr.nkw.zidemcore.Worlds.Worlds;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();

        Worlds.init();

        getCommand("warp").setExecutor(new Warp());
        getCommand("rm").setExecutor(new Mine());

        getServer().getPluginManager().registerEvents(new PlayerHandler(), this);
        getServer().getPluginManager().registerEvents(new MineHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerDataHandler(), this);

        System.out.println("ZideMCore: fully loaded");

        MineHandler.resetMine();
    }

    @Override
    public void onDisable() {
        System.out.println("ZideMCore: unloaded");
    }
}
