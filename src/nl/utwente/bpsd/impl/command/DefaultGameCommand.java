package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.exceptions.ImproperlyConfiguredException;
import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.Command;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;

public class DefaultGameCommand implements Command {

    @Override
    public GameCommandResult execute(Player player, Game game) {
        if (!(game instanceof DefaultGame)) {
            throw new ImproperlyConfiguredException("DefaultGameCommands that don't have an instance of DefaultGame, that's badly configured!");
        }
        if (!(player instanceof DefaultPlayer)) {
            throw new ImproperlyConfiguredException("DefaultGameCommands that don't have an instance of DefaultPlayer, that's badly configured!");
        }
        // If this happens to be fed to the state machine it is invalid
        return DefaultGameCommandResult.INVALID;
    }
    
}
