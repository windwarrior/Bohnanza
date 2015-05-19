package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.DiscardPile;

import java.util.List;

public class DefaultReshuffleCommand extends DefaultGameCommand {
    /**
     * Adds reshuffled list of cards from discardPile to gamePile.
     * Returns DefaultGameCommandResult.FINISHED in case of reshuffleCounter > 2
     */
    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p, g); // force a check that this is indeed a defaultGame
        DefaultGame game = (DefaultGame) g; // Cast it because it is now indeed a DefaultGame
        DefaultPlayer player = (DefaultPlayer) p;
        //game should end after gamePile being exhousted for the 3rd time
        if(game.getReshuffleCounter() > 2) {
            //or maybe should return INVALID?
            return DefaultGameCommandResult.FINISHED;
        }
        else
        {
            //should check if (!(game instanceof DiscardPile)) ?
            DiscardPile dp = (DiscardPile)game.getDiscardPile();
            List<Card> shuffled = dp.shuffleRemove();
            for (Card c : shuffled) {
                game.getGamePile().append(c);
            }
            return DefaultGameCommandResult.RESHUFFLED;
        }
    }
}
