package nl.utwente.bpsd.impl.mafia;

import nl.utwente.bpsd.model.pile.HarvestablePile;


public class MafiaBoss {
    private final String name;
    private final HarvestablePile pile;
    private final int coinConditionToHarvest;
    
    public MafiaBoss(String name, HarvestablePile pile, int coinConditionToHarvest) {
        this.name = name;
        this.pile = pile;
        this.coinConditionToHarvest = coinConditionToHarvest;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the pile
     */
    public HarvestablePile getPile() {
        return pile;
    }

    /**
     * @return the coinConditionToHarvest
     */
    public int getCoinConditionToHarvest() {
        return coinConditionToHarvest;
    }
}
