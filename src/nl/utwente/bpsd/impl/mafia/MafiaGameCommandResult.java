package nl.utwente.bpsd.impl.mafia;

import nl.utwente.bpsd.model.GameCommandResult;

public enum MafiaGameCommandResult implements GameCommandResult{
    DRAW_REVEAL,
    INVALID,
    PLANT_HAND_FIELD,
    PLANT_HAND_MAFIA,
    PLANT_REVEAL,
    GIVE_BEANS
}
