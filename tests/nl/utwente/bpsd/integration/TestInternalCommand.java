/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.integration;

import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.InternalCommand;
import nl.utwente.bpsd.model.Player;

/**
 *
 * @author Lennart
 */
public class TestInternalCommand implements InternalCommand {

    @Override
    public GameCommandResult execute(Player p, Game g) {
        return TestCommandResult.InternalProgress;
    }
    
}
