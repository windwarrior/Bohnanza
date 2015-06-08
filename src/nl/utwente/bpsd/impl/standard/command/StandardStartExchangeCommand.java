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
 * Command which should be invoked by player p who would like to start a card exchange
 * with other player indicated by this.opponent
 */
public class StandardStartExchangeCommand extends StandardGameCommand {
    Player opponent;

    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        StandardGame game = (StandardGame) g; // Cast it because it is now indeed a StandardGame
        StandardPlayer player = (StandardPlayer) p;

        // Only exchange with the currentPlayer is allowed
        String currentPlayerName = ((StandardPlayer)game.getCurrentPlayer()).getName();
        if(!(player.getName().equals(currentPlayerName) || ((StandardPlayer)opponent).getName().equals(currentPlayerName)))
            return StandardGameCommandResult.INVALID;

        // Only one exchange per player pair is allowed.
        for (Exchange exchange : game.getExchanges()) {
            if (exchange.isPlayerInExchange(opponent) && exchange.isPlayerInExchange(player))
                return StandardGameCommandResult.INVALID;
        }

        StandardExchange exchange;
        //if there is no exchange between this players, add new one to list
        if (!game.getExchange(opponent, p).isPresent()) {
            exchange = new StandardExchange(player, opponent);
            game.getExchanges().add(exchange);
        }
        Optional<Exchange> ex = game.getExchange(player, opponent);
        exchange = (StandardExchange)ex.get();
        //boolean wasStarted = exchange.isStarted();
        exchange.setSideState(player, Exchange.SideState.NEGOTIATING);

        //Consider following if statement. What to do when exchange have already started (both players have started it)? return invalid?
        //next exchange state only if both players started it
        //if(!wasStarted && exchange.isStarted())
        return StandardGameCommandResult.TRADE;
    }

    public void setOpponent(Player opp) {
        this.opponent = opp;
    }
}
