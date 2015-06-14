package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.exceptions.ImproperlyConfiguredException;
import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.model.Command;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;

public class MafiaGameCommand implements Command {
    @Override
    public GameCommandResult execute(Player player, Game game) {
        if (!(game instanceof MafiaGame)) {
            throw new ImproperlyConfiguredException("MafiaGameCommands that don't have an instance of MafiaGame, that's badly configured!");
        }
        if (!(player instanceof MafiaPlayer)) {
                throw new ImproperlyConfiguredException("MafiaGameCommands that don't have an instance of MafiaGamePlayer, that's badly configured!");
        }
        // If this happens to be fed to the state machine it is invalid
        return StandardGameCommandResult.INVALID;
    }
}
