package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.StandardExchange;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Exchange;
import nl.utwente.bpsd.model.GameCommandResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StandardAddHandCardToExchangeCommandTest {
    StandardPlayer first;
    StandardPlayer second;
    StandardPlayer third;
    StandardGame game;
    StandardAddHandCardToExchangeCommand addHandCardC;
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
        addHandCardC = new StandardAddHandCardToExchangeCommand();
    }

    @Test
    public void testSAddCardNoExchangeBetweenSides() throws Exception {
        Card card = Mockito.mock(Card.class);
        first.getHand().append(card);
        addHandCardC.setOpponent(third);
        result = addHandCardC.execute(first,game);
        addHandCardC.setCardIndex(0);
        assertThat("AddHandCardToExchange: no exchanges in game, command result checking", result, is(StandardGameCommandResult.INVALID));

        game.getExchanges().add(new StandardExchange(first, second));
        addHandCardC.setOpponent(third);
        addHandCardC.setCardIndex(0);
        result = addHandCardC.execute(first,game);
        assertThat("AddHandCardToExchange: no exchange between indicated players, size of game exchanges list check", game.getExchanges().size(), is(1));
        assertThat("AddHandCardToExchange: no exchange between indicated players, command result checking", result, is(StandardGameCommandResult.INVALID));
    }

    @Test
    public void testSAddHandCardWrongCardIndex() throws Exception {
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);

        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.NEGOTIATING);
        addHandCardC.setOpponent(second);
        result = addHandCardC.execute(first,game);
        assertThat("(1)AddHandCardToExchange: wrong card index, command result checking", result, is(StandardGameCommandResult.INVALID));

        Card card = Mockito.mock(Card.class);
        first.getHand().append(card);
        addHandCardC.setCardIndex(2);
        result = addHandCardC.execute(first,game);
        assertThat("(2)AddHandCardToExchange: wrong card index, command result checking", result, is(StandardGameCommandResult.INVALID));
    }

    @Test
    public void testSAddHandCardWrongExchangeState() throws Exception {
        Card card = Mockito.mock(Card.class);
        first.getHand().append(card);
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);

        exchange.setSideState(first, Exchange.SideState.ACCEPTING );
        exchange.setSideState(second, Exchange.SideState.ACCEPTING );
        addHandCardC.setOpponent(second);
        addHandCardC.setCardIndex(0);
        result = addHandCardC.execute(first,game);
        assertThat("AddHandCardToExchange: both players already accepted exchange, command result checking", result, is(StandardGameCommandResult.INVALID));

        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.DECLINING);
        result = addHandCardC.execute(first,game);
        assertThat("AddHandCardToExchange: exchange declined, command result checking", result, is(StandardGameCommandResult.INVALID));

        exchange.setSideState(second, Exchange.SideState.ACCEPTING);
        result = addHandCardC.execute(first,game);
        assertThat("AddHandCardToExchange: one player already accepted, command result checking", result, is(StandardGameCommandResult.INVALID));
    }

    @Test
    public void testSAddHandCardWrongCardType() throws Exception {
        Map<Integer, Integer> chiliBeanOMeter = new HashMap<>();
        chiliBeanOMeter.put(3, 1);
        chiliBeanOMeter.put(6, 2);
        chiliBeanOMeter.put(8, 3);
        chiliBeanOMeter.put(9, 4);
        CardType chiliBean = new CardType("Chili Bean", chiliBeanOMeter, 18);
        Card chiliBeanCard = new Card(chiliBean, 1);

        second.getHand().append(chiliBeanCard);
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);
        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.NEGOTIATING);
        exchange.getOfferedCards(second).add(chiliBeanCard);

        addHandCardC.setOpponent(first);
        addHandCardC.setCardIndex(0);
        result = addHandCardC.execute(second,game);
        assertThat("AddHandCardToExchange: player already added indicated card to exchange, command result checking", result, is(StandardGameCommandResult.INVALID));
        assertThat("AddHandCardToExchange: player already added indicated card to exchange, second player offered card list size check", exchange.getOfferedCards(second).size(), is(1));
    }

    @Test
    public void testSAddHandCardCorrect() throws Exception {
        Map<Integer, Integer> chiliBeanOMeter = new HashMap<>();
        chiliBeanOMeter.put(3, 1);
        chiliBeanOMeter.put(6, 2);
        chiliBeanOMeter.put(8, 3);
        chiliBeanOMeter.put(9, 4);
        CardType chiliBean = new CardType("Chili Bean", chiliBeanOMeter, 18);
        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType redBean = new CardType("Red Bean", redBeanOMeter, 8);
        Card chiliBeanCard = new Card(chiliBean, 1);
        Card redBeanCard = new Card(redBean, 3);

        second.getHand().append(chiliBeanCard);
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);
        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.NEGOTIATING);
        exchange.getOfferedCards(second).add(redBeanCard);

        int oldHandSize = second.getHand().pileSize();

        addHandCardC.setOpponent(first);
        addHandCardC.setCardIndex(0);
        result = addHandCardC.execute(second,game);
        assertThat("AddHandCardToExchange: player added card to exchange, command result checking", result, is(StandardGameCommandResult.TRADE));
        assertThat("AddHandCardToExchange: player added card to exchange, second player offered card list size check", exchange.getOfferedCards(second).size(), is(2));
        assertThat("AddHandCardToExchange: player added card to exchange, second player hand size check", second.getHand().pileSize(), is(oldHandSize));
        assertThat("AddHandCardToExchange: player added card to exchange, second player offered card check", exchange.getOfferedCards(second).get(1), is(equalTo(chiliBeanCard)));
    }
}