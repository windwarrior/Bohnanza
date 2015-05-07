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
import nl.utwente.bpsd.model.GameStatus;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bsd.model.command.Command;

/**
 *
 * @author lennart
 */
public class DefaultDrawHandCommand implements Command {
    private Player player;
    
    public void setPlayer(Player p ) {
        this.player = p;
    }

    /**
     * @requires this.player != null && g != null;
     */
    @Override
    public GameStatus execute(Game g) {
        assert this.player != null;
        
        Pile gamePile = g.getGamePile();        
        
        List<Card> toBeInserted = new ArrayList<Card>();

        for (int i = 0; i < 3; i++) {
            toBeInserted.add(gamePile.pop());
        }
            
        this.player.addAllHand(toBeInserted);
        
        return GameStatus.GAME_PROGRESS;
    }
    
}
