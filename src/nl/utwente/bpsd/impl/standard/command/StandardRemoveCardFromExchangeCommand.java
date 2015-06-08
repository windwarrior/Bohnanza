package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.StandardExchange;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.*;

import java.util.List;
import java.util.Optional;

public class StandardRemoveCardFromExchangeCommand extends StandardGameCommand{
    Player opponent;
    int cardIndex = -1;

    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        StandardGame game = (StandardGame) g; // Cast it because it is now indeed a StandardGame
        StandardPlayer player = (StandardPlayer) p;

        /*
         * There must be already created exchange between indicated two players
         */
        Optional<Exchange> ex = game.getExchange(player, opponent);
        if( !ex.isPresent() )
            return StandardGameCommandResult.INVALID;

        StandardExchange exchange = (StandardExchange)ex.get();
        /*
         * Exchange must be already started by two players and
         * not finished
         */
        if(exchange.isFinished() || !exchange.isStarted())
            return StandardGameCommandResult.INVALID;

        List<Card> offeredCards = exchange.getOfferedCards(player);
        if(cardIndex < 0 || cardIndex >= offeredCards.size())
            return StandardGameCommandResult.INVALID;

        exchange.getOfferedCards(player).remove(cardIndex);
        return StandardGameCommandResult.TRADE;
    }

    public void setOpponent(Player opp) {
        this.opponent = opp;
    }
    public void setCardIndex(int index){
        this.cardIndex = index;
    }
}
