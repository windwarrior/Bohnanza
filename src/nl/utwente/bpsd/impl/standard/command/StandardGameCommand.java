package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.exceptions.ImproperlyConfiguredException;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.Command;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;

public class StandardGameCommand implements Command {

    @Override
    public GameCommandResult execute(Player player, Game game) {
        if (!(game instanceof StandardGame)) {
            throw new ImproperlyConfiguredException("DefaultGameCommands that don't have an instance of DefaultGame, that's badly configured!");
        }
        if (!(player instanceof StandardPlayer)) {
            throw new ImproperlyConfiguredException("DefaultGameCommands that don't have an instance of DefaultPlayer, that's badly configured!");
        }
        // If this happens to be fed to the state machine it is invalid
        return StandardGameCommandResult.INVALID;
    }
    
}
