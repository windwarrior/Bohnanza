package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.DefaultGameCommandResult;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.command.Command;

public class DefaultSkipCommand implements Command {

    /**
     * Command that models skipping the current action.
     * 
     * Used when turns are optional.
     * @param g
     * @return 
     */
    @Override
    public DefaultGameCommandResult execute(Game g) {
        // Indicates to the statemachine that it should try to skip to the next
        // possible state.
        return DefaultGameCommandResult.SKIP;
    }
    
}
