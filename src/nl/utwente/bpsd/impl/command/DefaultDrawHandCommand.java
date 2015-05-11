/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.impl.command;

import java.util.ArrayList;
import java.util.List;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.DefaultGameCommandResult;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.command.Command;

public class DefaultDrawHandCommand implements Command {
    private Player player;
    
    public void setPlayer(Player p ) {
        this.player = p;
    }

    /**
     * Draw three cards from Game g gamePile into Player player hand
     * @requires this.player != null && g != null;
     */
    @Override
    public DefaultGameCommandResult execute(Game g) {
        assert this.player != null;
        // TODO: Error handling in the case that the game pile is empty (and discard pile has been reshuffled twice)
        Pile gamePile = g.getGamePile();        
        
        List<Card> toBeInserted = new ArrayList<Card>();

        for (int i = 0; i < 3; i++) {
            toBeInserted.add(gamePile.pop());
        }
            
        this.player.addAllHand(toBeInserted);
        
        return DefaultGameCommandResult.GAME_PROGRESS;
    }
    
}
