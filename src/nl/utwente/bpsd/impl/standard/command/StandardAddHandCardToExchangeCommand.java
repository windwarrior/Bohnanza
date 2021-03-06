package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.StandardExchange;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.model.pile.HandPile;

import java.util.Optional;

/**
 * Command invoked by player if he/she would like to exchange any of hand cards.
 * Adds indicated card to player's offered cards in exchange.
 * Do not remove card from hand.
 */
public class StandardAddHandCardToExchangeCommand extends StandardGameCommand{
    Player opponent;
    int cardIndex = -1;

    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        StandardGame game = (StandardGame) g; // Cast it because it is now indeed a StandardGame
        StandardPlayer player = (StandardPlayer) p;

        if(player.getHand().pileSize() <= cardIndex || cardIndex < 0)
            return StandardGameCommandResult.INVALID;
        Optional<Exchange> ex = game.getExchange(player, opponent);
        //there must be already created exchange between these two players
        if (!ex.isPresent())
            return StandardGameCommandResult.INVALID;
        StandardExchange exchange = (StandardExchange)ex.get();
        //exchange cannot be finished and must be already started by both player sides
        if(exchange.isFinished() || !exchange.isStarted())
            return StandardGameCommandResult.INVALID;

        //the same card can be added to exchange only once
        StandardGameCommandResult result = ((HandPile)(player.getHand())).getCardCopy(cardIndex).map((Card c) -> {
            if (!exchange.getOfferedCards(player).contains(c)) {
                exchange.getOfferedCards(player).add(c);
                return StandardGameCommandResult.TRADE;
            } else
                return StandardGameCommandResult.INVALID;
        }).orElse(StandardGameCommandResult.INVALID);
        return result;
    }
    public void setOpponent(Player opp) {
        this.opponent = opp;
    }
    public void setCardIndex(int index){
        this.cardIndex = index;
    }
}