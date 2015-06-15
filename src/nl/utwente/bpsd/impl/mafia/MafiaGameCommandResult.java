package nl.utwente.bpsd.impl.mafia;

import nl.utwente.bpsd.model.GameCommandResult;

public enum MafiaGameCommandResult implements GameCommandResult{
    DRAW_REVEAL,
    GIVE_BEANS,
    INVALID,
    PLANT_HAND_FIELD,
    PLANT_HAND_MAFIA,
    PLANT_REVEAL,
    SKIP_TO_PHASE_SIX
}
