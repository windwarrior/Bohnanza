package nl.utwente.bpsd.impl.standard.command;


import nl.utwente.bpsd.impl.standard.StandardExchange;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.model.pile.HandPile;

import java.util.Optional;

public class StandardAddTradingAreaCardToExchangeCommand extends StandardGameCommand{
    Player opponent;
    int cardIndex = -1;

    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        StandardGame game = (StandardGame) g; // Cast it because it is now indeed a StandardGame
        StandardPlayer player = (StandardPlayer) p;

        if(player.getTrading().pileSize() <= cardIndex || cardIndex < 0)
            return StandardGameCommandResult.INVALID;

        /* Trading area cards can exchange only currentPlayer
         * there must be already created exchange between indicated two players
         */
        Optional<Exchange> ex = game.getExchange(player, opponent);
        String currentPlayerName = ((StandardPlayer)game.getCurrentPlayer()).getName();
        if(!(player.getName().equals(currentPlayerName)) || !ex.isPresent() )
            return StandardGameCommandResult.INVALID;

        StandardExchange exchange = (StandardExchange)ex.get();
        //exchange cannot be finished and must be already started by both player sides
        if(exchange.isFinished() || !exchange.isStarted())
            return StandardGameCommandResult.INVALID;

        //the same card can be added to exchange only once
        StandardGameCommandResult result = ((HandPile)(player.getTrading())).getCard(cardIndex).map((Card c) -> {
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
