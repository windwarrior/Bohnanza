package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.Command;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;

public class StandardSkipCommand extends StandardGameCommand {

    /**
     * Command that models skipping the current action.
     * 
     * Used when turns are optional.
     */
    @Override
    public GameCommandResult execute(Player p, Game g) {
        // Indicates to the statemachine that it should try to skip to the next
        // possible state.
        return StandardGameCommandResult.SKIP;
    }
    
}
