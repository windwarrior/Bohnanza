/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.impl.command;

import java.util.ArrayList;
import java.util.List;
import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.Command;
import nl.utwente.bpsd.model.GameCommandResult;

public class DefaultDrawHandCommand extends DefaultGameCommand {
    private DefaultPlayer player;
    
    public void setPlayer(DefaultPlayer p ) {
        this.player = p;
    }

    /**
     * Draw three cards from Game g gamePile into Player player hand
     * @requires this.player != null && g != null;
     */
    @Override
    public GameCommandResult execute(Game game) {
        super.execute(game); // force a check that this is indeed a defaultgame
        DefaultGame dg = (DefaultGame) game; // Cast it because it is now indeed a DefaultGame
        
        assert this.player != null;
        // TODO: Error handling in the case that the game pile is empty (and discard pile has been reshuffled twice)
        Pile gamePile = dg.getGamePile();        
        
        List<Card> toBeInserted = new ArrayList<Card>();

        for (int i = 0; i < 3; i++) {
            toBeInserted.add(gamePile.pop());
        }
            
        this.player.addAllHand(toBeInserted);
        
        return DefaultGameCommandResult.DRAWN_TO_HAND;
    }
    
}
