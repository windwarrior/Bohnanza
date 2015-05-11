package nl.utwente.bpsd.impl;

import nl.utwente.bpsd.model.GameCommandResult;

public enum DefaultGameCommandResult implements GameCommandResult {

    BOUGHT_FIELD, 
    DRAWN_TO_HAND, 
    DRAWN_TO_TRADING, 
    FINISHED,
    HARVEST, 
    INVALID, 
    PLANT, 
    PLANT_TRADED, 
    SKIP, 
    TRADE

}
