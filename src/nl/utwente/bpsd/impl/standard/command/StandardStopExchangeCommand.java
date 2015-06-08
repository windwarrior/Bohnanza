package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.StandardExchange;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.Exchange;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;

import java.util.Optional;

/**
 * Command which allows player taking part in exchange to stop it.
 * It results in deleting exchange from game.
 */
public class StandardStopExchangeCommand extends StandardGameCommand {

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
        //can not stop when exchange is in isStarted phase (when exchange isStarted only decline can be invoked)
        if(exchange.isStarted())
            return StandardGameCommandResult.INVALID;
        exchange.setSideState(player, Exchange.SideState.IDLE);
        //remove exchange from game
        game.getExchanges().remove(exchange);
        return StandardGameCommandResult.TRADE;
    }

    public void setOpponent(Player opp) {
        this.opponent = opp;
    }
}
