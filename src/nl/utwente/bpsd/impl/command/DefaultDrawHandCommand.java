package nl.utwente.bpsd.impl.command;

import java.util.ArrayList;
import java.util.List;
import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.pile.Pile;

public class DefaultDrawHandCommand extends DefaultGameCommand {

    /**
     * Draw three cards from Game g gamePile into Player player hand
     * @requires this.player != null && g != null;
     */
    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        DefaultGame game = (DefaultGame) g; // Cast it because it is now indeed a DefaultGame
        DefaultPlayer player = (DefaultPlayer) p;

        // TODO: Error handling in the case that the game pile is empty (and discard pile has been reshuffled twice)
        Pile gamePile = game.getGamePile();
        
        List<Card> toBeInserted = new ArrayList<Card>();

        for (int i = 0; i < 3; i++) {
            toBeInserted.add(gamePile.pop().get());
        }
            
        player.addAllHand(toBeInserted);

        
        return DefaultGameCommandResult.DRAWN_TO_HAND;
    }
    
}
