package fr.nkw.zidemcore.Items;

import fr.nkw.zidemcore.Lib.RomanNumber;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class Pickaxe {
    private static final String PICKAXE_NAME = "§7§lPioche du débutant";
    private static final String LEVEL_PREFIX = "§1Niveau: §2";
    private static final String XP_PREFIX = "§1XP: §2";
    private static final int MAX_LEVEL = 10;
    private static final int XP_PER_LEVEL = 50;

    private final Player owner;
    private int level;
    private int xp;

    public Pickaxe(Player owner) {
        this.owner = owner;
        this.level = 1;
        this.xp = 0;
    }

    public static Pickaxe parseItem(Player owner, ItemStack item) {
        if (item == null || !item.getType().name().contains("PICKAXE")) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName() || !meta.getDisplayName().equals(PICKAXE_NAME)) {
            return null;
        }

        Pickaxe pickaxe = new Pickaxe(owner);
        List<String> lore = meta.getLore();
        if (lore != null) {
            for (String line : lore) {
                if (line.startsWith(LEVEL_PREFIX)) {
                    try {
                        pickaxe.level = RomanNumber.fromRoman(line.substring(LEVEL_PREFIX.length()));
                    } catch (IllegalArgumentException e) {
                        pickaxe.level = 1;
                    }
                } else if (line.startsWith(XP_PREFIX)) {
                    try {
                        pickaxe.xp = Integer.parseInt(line.substring(XP_PREFIX.length()));
                    } catch (NumberFormatException e) {
                        pickaxe.xp = 0;
                    }
                }
            }
        }

        return pickaxe;
    }

    // TODO return Pickaxe
    public void generateDefaultPickaxe() {
        PlayerInventory inv = owner.getInventory();
        boolean hasPickaxe = false;

        for (ItemStack item : inv.getContents()) {
            if (item != null && item.getType().name().contains("PICKAXE")) {
                hasPickaxe = true;
                break;
            }
        }

        if (!hasPickaxe) {
            ItemStack newPickaxe = new ItemStack(Material.WOOD_PICKAXE);

            ItemMeta meta = newPickaxe.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.setDisplayName(PICKAXE_NAME);
            List<String> lore = new ArrayList<>();
            lore.add(LEVEL_PREFIX + RomanNumber.toRoman(level));
            lore.add(XP_PREFIX + xp);
            meta.setLore(lore);
            newPickaxe.setItemMeta(meta);

            inv.addItem(newPickaxe);
        }
    }

    public static void updateItemMeta(ItemStack item, int level, int xp) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            List<String> lore = new ArrayList<>();
            lore.add(LEVEL_PREFIX + RomanNumber.toRoman(level));
            lore.add(XP_PREFIX + xp);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
    }

    public int getLevel() {
        return level;
    }

    public int getXP() {
        return xp;
    }

    public void addXP(int amount) {
        xp += amount;
        int xpRequired = XP_PER_LEVEL * level;
        while (xp >= xpRequired && level < MAX_LEVEL) {
            xp -= xpRequired;
            level++;
            xpRequired = XP_PER_LEVEL * level;
            owner.sendMessage("§aVotre pioche a atteint le niveau " + level + "!");
        }
    }

    public void saveData(ItemStack item) {
        updateItemMeta(item, level, xp);
    }
}
