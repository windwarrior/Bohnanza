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

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StandardRemoveCardFromExchangeCommandTest {
    StandardPlayer first;
    StandardPlayer second;
    StandardPlayer third;
    StandardGame game;
    StandardRemoveCardFromExchangeCommand removeCardC;
    GameCommandResult result;

    @Before
    public void setUp() throws Exception {
        first = new StandardPlayer("First");
        second = new StandardPlayer("Second");
        third = new StandardPlayer("Third");
        game = new StandardGame();
        StandardPlayer t[] = new StandardPlayer[]{first, second, third};
        game.initialize();
        game.addPlayers(t);
        removeCardC = new StandardRemoveCardFromExchangeCommand();
    }

    @Test
    public void testSRemoveCardNoExchangeBetweenSides() throws Exception {
        removeCardC.setOpponent(third);
        result = removeCardC.execute(first,game);
        removeCardC.setCardIndex(0);
        assertThat("RemoveCardFromExchange: no exchanges in game, command result checking", result, is(StandardGameCommandResult.INVALID));

        Card card = Mockito.mock(Card.class);
        game.getExchanges().add(new StandardExchange(first, second));
        Optional<Exchange> ex = game.getExchange(first, second);
        StandardExchange exchange = (StandardExchange)ex.get();
        exchange.getOfferedCards(first).add(card);
        removeCardC.setOpponent(third);
        removeCardC.setCardIndex(0);
        result = removeCardC.execute(first,game);
        assertThat("RemoveCardFromExchange: no exchange between indicated players, size of game exchanges list check", game.getExchanges().size(), is(1));
        assertThat("RemoveCardFromExchange: no exchange between indicated players, command result checking", result, is(StandardGameCommandResult.INVALID));
    }

    @Test
    public void testSRemoveCardWrongCardIndex() throws Exception {
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);

        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.NEGOTIATING);
        removeCardC.setOpponent(second);
        result = removeCardC.execute(first,game);
        assertThat("(1)RemoveCardFromExchange: wrong card index, command result checking", result, is(StandardGameCommandResult.INVALID));

        Card card = Mockito.mock(Card.class);
        exchange.getOfferedCards(first).add(card);
        removeCardC.setCardIndex(1);
        result = removeCardC.execute(first,game);
        assertThat("(2)RemoveCardFromExchange: wrong card index, command result checking", result, is(StandardGameCommandResult.INVALID));
    }

    @Test
    public void testSRemoveCardWrongExchangeState() throws Exception {
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);
        Card card = Mockito.mock(Card.class);
        exchange.getOfferedCards(first).add(card);

        exchange.setSideState(first, Exchange.SideState.ACCEPTING );
        exchange.setSideState(second, Exchange.SideState.ACCEPTING );
        removeCardC.setOpponent(second);
        removeCardC.setCardIndex(0);
        result = removeCardC.execute(first,game);
        assertThat("RemoveCardFromExchange: both players already accepted exchange, command result checking", result, is(StandardGameCommandResult.INVALID));

        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.IDLE);
        result = removeCardC.execute(first,game);
        assertThat("RemoveCardFromExchange: wrong state, command result checking", result, is(StandardGameCommandResult.INVALID));
    }

    @Test
    public void testSRemoveCardFromExchangeCorrect() throws Exception {
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);
        Card card1 = Mockito.mock(Card.class);
        Card card2 = Mockito.mock(Card.class);
        exchange.getOfferedCards(first).add(card1);
        exchange.getOfferedCards(second).add(card2);
        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.NEGOTIATING);
        int oldFirstOfferedSize = exchange.getOfferedCards(first).size();
        int oldSecondOfferedSize = exchange.getOfferedCards(second).size();

        removeCardC.setOpponent(second);
        removeCardC.setCardIndex(0);
        result = removeCardC.execute(first,game);
        assertThat("RemoveCardFromExchange: command result checking", result, is(StandardGameCommandResult.TRADE));
        assertThat("RemoveCardFromExchange: first player's offered list size check", exchange.getOfferedCards(first).size(), is(oldFirstOfferedSize-1));
        assertThat("RemoveCardFromExchange: second player's offered list size check", exchange.getOfferedCards(second).size(), is(oldSecondOfferedSize));
    }
}