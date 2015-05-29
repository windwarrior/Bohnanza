package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.DiscardPile;

import java.util.List;

public class StandardReshuffleCommand extends StandardGameCommand {
    /**
     * Adds reshuffled list of cards from discardPile to gamePile.
     * Returns StandardGameCommandResult.FINISHED in case of reshuffleCounter > 2
     */
    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p, g); // force a check that this is indeed a defaultGame
        StandardGame game = (StandardGame) g; // Cast it because it is now indeed a StandardGame
        StandardPlayer player = (StandardPlayer) p;
        //game should end after gamePile being exhousted for the 3rd time
        if(game.getReshuffleCounter() > 2) {
            //or maybe should return INVALID?
            return StandardGameCommandResult.FINISHED;
        }
        else
        {
            //should check if (!(game instanceof DiscardPile)) ?
            DiscardPile dp = (DiscardPile)game.getDiscardPile();
            List<Card> shuffled = dp.shuffleRemove();
            for (Card c : shuffled) {
                game.getGamePile().append(c);
            }
            return StandardGameCommandResult.RESHUFFLED;
        }
    }
}
