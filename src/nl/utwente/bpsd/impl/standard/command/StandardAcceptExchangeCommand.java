package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.StandardExchange;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.model.pile.HandPile;

import java.util.List;
import java.util.Optional;

/**
 * Command invoked by player when he would like to accept current exchange.
 * If the player is the second in pair to accept the exchange,
 * actual cards exchange is performed.
 */
public class StandardAcceptExchangeCommand extends StandardGameCommand {

    Player opponent;

    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        StandardGame game = (StandardGame) g; // Cast it because it is now indeed a StandardGame
        StandardPlayer player = (StandardPlayer) p;

        Optional<Exchange> ex = game.getExchange(player, opponent);
        //there must be already created exchange between these two players
        if (!ex.isPresent())
            return StandardGameCommandResult.INVALID;
        StandardExchange exchange = (StandardExchange)ex.get();
        //exchange cannot be finished
        if(exchange.isFinished())
            return StandardGameCommandResult.INVALID;
        //exchange must be already started by both players, opponent could already accept exchange
        if(!(exchange.getSideState(player)== Exchange.SideState.NEGOTIATING && exchange.getSideState(opponent)== Exchange.SideState.ACCEPTING || exchange.isStarted()))
            return StandardGameCommandResult.INVALID;

        exchange.setSideState(player, Exchange.SideState.ACCEPTING);
        //if accepted by both sides, perform card exchange between sides
        if(exchange.isAccepted())
        {
            /* player's offered cards are added to opponent 's trading area
             * these offered cards are removed from player's hand
             * and trading area
             */
            List<Card> playerOfferedCards = exchange.getOfferedCards(player);
            int i;
            for(i = 0; i<playerOfferedCards.size(); ++i) {
                ((StandardPlayer) opponent).getTrading().append(playerOfferedCards.get(i));
            }
            ((HandPile)player.getTrading()).removeAll(playerOfferedCards);
            ((HandPile)player.getHand()).removeAll(playerOfferedCards);

            List<Card> opponentOfferedCards = exchange.getOfferedCards(opponent);
                for(i = 0; i<opponentOfferedCards.size(); ++i) {
                player.getTrading().append(opponentOfferedCards.get(i));
            }
            ((HandPile)((StandardPlayer)opponent).getTrading()).removeAll(opponentOfferedCards);
            ((HandPile)((StandardPlayer)opponent).getHand()).removeAll(opponentOfferedCards);

            //remove exchange from game?
            game.getExchanges().remove(exchange);
        }
        return StandardGameCommandResult.TRADE;
    }

    public void setOpponent(Player opp) {
        this.opponent = opp;
    }
}
