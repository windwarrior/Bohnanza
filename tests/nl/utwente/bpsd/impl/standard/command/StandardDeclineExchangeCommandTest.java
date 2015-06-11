package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.StandardExchange;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.Exchange;
import nl.utwente.bpsd.model.GameCommandResult;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StandardDeclineExchangeCommandTest {

    StandardPlayer first;
    StandardPlayer second;
    StandardPlayer third;
    StandardGame game;
    StandardDeclineExchangeCommand declineExchangeC;
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
        declineExchangeC = new StandardDeclineExchangeCommand();
    }

    @Test
    public void testSDeclineExchangeNoExchangeBetweenSides() throws Exception {
        declineExchangeC.setOpponent(third);
        result = declineExchangeC.execute(first,game);
        assertThat("DeclineExchange: no exchanges in game, command result checking", result, is(StandardGameCommandResult.INVALID));

        game.getExchanges().add(new StandardExchange(first, second));
        declineExchangeC.setOpponent(third);
        result = declineExchangeC.execute(first,game);
        assertThat("DeclineExchange: no exchange between indicated players, command result checking", result, is(StandardGameCommandResult.INVALID));
        assertThat("DeclineExchange: no exchange between indicated players, size of game exchanges list check", game.getExchanges().size(), is(1));
    }

    @Test
    public void testSDeclineExchangeWrongExchangeState() throws Exception {
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);

        exchange.setSideState(first, Exchange.SideState.ACCEPTING );
        exchange.setSideState(second, Exchange.SideState.ACCEPTING );
        declineExchangeC.setOpponent(second);
        result = declineExchangeC.execute(first,game);
        assertThat("DeclineExchange: both players already accepted exchange, command result checking", result, is(StandardGameCommandResult.INVALID));

        exchange.setSideState(first, Exchange.SideState.DECLINING);
        declineExchangeC.setOpponent(first);
        result = declineExchangeC.execute(second,game);
        assertThat("DeclineExchange: exchange declined, command result checking", result, is(StandardGameCommandResult.INVALID));
    }

    @Test
    public void testSDeclineExchangeCorrect() throws Exception {
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);

        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.ACCEPTING);
        declineExchangeC.setOpponent(second);
        int oldListSize = game.getExchanges().size();
        result = declineExchangeC.execute(first,game);
        assertThat("DeclineExchange: both players already accepted exchange, command result checking", result, is(StandardGameCommandResult.TRADE));
        assertThat("DeclineExchange: both players accepted exchange, size of game exchanges list check", game.getExchanges().size(), is(oldListSize-1));
    }

}