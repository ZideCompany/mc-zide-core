package fr.nkw.zidemcore.Entity.Mine;

public class MineBlock {
    public String blockName;
    public double probability;

    public MineBlock(String blockName, double probability) {
        this.blockName = blockName;
        this.probability = probability;
    }
}
