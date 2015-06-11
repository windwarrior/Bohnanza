package nl.utwente.bpsd.impl.standard;

import nl.utwente.bpsd.model.GameCommandResult;

public enum StandardGameCommandResult implements GameCommandResult {

    BOUGHT_FIELD, 
    DRAWN_TO_HAND, 
    DRAWN_TO_TRADING, 
    FINISHED,
    HARVEST, 
    INVALID, 
    PLANT, 
    PLANT_TRADED,
    PROGRESS,
    SKIP, 
    TRADE,
    RESHUFFLE, //when need to invoke reshuffle command
    RESHUFFLED,  //result of reshuffle command if succeded
}
