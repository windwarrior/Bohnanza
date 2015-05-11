/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.exceptions.ImproperlyConfiguredException;
import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.model.Command;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;

/**
 *
 * @author lennart
 */
public class DefaultGameCommand implements Command {

    @Override
    public GameCommandResult execute(Game game) {
        if (!(game instanceof DefaultGame)) {
            throw new ImproperlyConfiguredException("DefaultGameCommands that don't have an instance of DefaultGame, thats badly configured!");
        }
        
        // If this happens to be fed to the state machine it is invalid
        return DefaultGameCommandResult.INVALID;
    }
    
}
