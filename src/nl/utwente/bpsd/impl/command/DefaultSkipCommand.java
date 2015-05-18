package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.Command;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;

public class DefaultSkipCommand extends DefaultGameCommand {

    /**
     * Command that models skipping the current action.
     * 
     * Used when turns are optional.
     */
    @Override
    public GameCommandResult execute(Player p, Game g) {
        // Indicates to the statemachine that it should try to skip to the next
        // possible state.
        return DefaultGameCommandResult.SKIP;
    }
    
}
