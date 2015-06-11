package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.StandardExchange;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Exchange;
import nl.utwente.bpsd.model.GameCommandResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StandardStartExchangeCommandTest {
    StandardPlayer first;
    StandardPlayer second;
    StandardPlayer third;
    StandardGame game;
    StandardStartExchangeCommand startExchangeC;
    GameCommandResult result;

    @Before
    public void setUp() throws Exception {
        first = new StandardPlayer("First");
        second = new StandardPlayer("Second");
        third = new StandardPlayer("Third");
        game = new StandardGame();
        game.initialize();
        StandardPlayer t[] = new StandardPlayer[]{first, second, third};
        game.addPlayers(t);
        startExchangeC = new StandardStartExchangeCommand();
    }

    @Test
    public void testSStartExchangeNoCurrentPlayer() throws Exception {
        startExchangeC.setOpponent(third);
        result = startExchangeC.execute(second,game);
        assertThat("StartExchange: no current player in exchange, command result checking", result, is(StandardGameCommandResult.INVALID));
    }

    @Test
    public void testSStartExchangeCorrect() throws Exception {
        //no exchange in game between indicated players
        startExchangeC.setOpponent(first);
        int oldExchangesSize = game.getExchanges().size();
        result = startExchangeC.execute(second,game);
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);
        Exchange.SideState fState = exchange.getSideState(first);
        Exchange.SideState sState = exchange.getSideState(second);
        int newExchangesSize = game.getExchanges().size();
        assertThat("StartExchange: there is no exchange in game between indicated players, command result checking", result, is(StandardGameCommandResult.TRADE));
        assertThat("StartExchange: there is no exchange in game between indicated players, command result checking", newExchangesSize, is(oldExchangesSize+1));
        assertThat("StartExchange: there is no exchange in game between indicated players, first player state check", fState, is(Exchange.SideState.IDLE));
        assertThat("StartExchange: there is no exchange in game between indicated players, second player state check", sState, is(Exchange.SideState.NEGOTIATING));

        //there is already exchange in game between indicated players
        startExchangeC.setOpponent(second);
        oldExchangesSize = game.getExchanges().size();
        result = startExchangeC.execute(first,game);
        fState = exchange.getSideState(first);
        sState = exchange.getSideState(second);
        newExchangesSize = game.getExchanges().size();
        assertThat("StartExchange: there is already present in game exchange between indicated players, command result checking", result, is(StandardGameCommandResult.TRADE));
        assertThat("StartExchange: there is already present in game exchange between indicated players, command result checking", newExchangesSize, is(oldExchangesSize));
        assertThat("StartExchange: there is already present in game exchange between indicated players, first player state check", fState, is(Exchange.SideState.NEGOTIATING));
        assertThat("StartExchange: is already present in game exchange between indicated players, second player state check", sState, is(Exchange.SideState.NEGOTIATING));
    }
}