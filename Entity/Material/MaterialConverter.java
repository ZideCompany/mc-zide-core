package fr.nkw.zidemcore.Entity.Material;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class MaterialConverter {
    private static final Map<Material, String> materialToIdMap = new HashMap<>();
    private static final Map<String, Material> idToMaterialMap = new HashMap<>();

    static {
        for (Material material : Material.values()) {
            String id = String.valueOf(material.getId());
            materialToIdMap.put(material, id);
            idToMaterialMap.put(id, material);
        }
    }

    public static String materialToString(Material material) {
        return materialToIdMap.get(material);
    }

    public static Material stringToMaterial(String id) {
        return idToMaterialMap.get(id);
    }
}
