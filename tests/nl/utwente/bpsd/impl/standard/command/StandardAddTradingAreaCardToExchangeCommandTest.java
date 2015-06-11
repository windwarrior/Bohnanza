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

public class StandardAddTradingAreaCardToExchangeCommandTest {
    StandardPlayer first;
    StandardPlayer second;
    StandardPlayer third;
    StandardGame game;
    StandardAddTradingAreaCardToExchangeCommand addTradingCardC;
    GameCommandResult result;

    @Before
    public void setUp() throws Exception {
        first = new StandardPlayer("First");
        second = new StandardPlayer("Second");
        third = new StandardPlayer("Third");
        game = new StandardGame();
        StandardPlayer t[] = new StandardPlayer[]{first, second, third};
        game.addPlayers(t);
        game.initialize();
        addTradingCardC = new StandardAddTradingAreaCardToExchangeCommand();
    }

   @Test
    public void testSAddTradingCardNoExchangeBetweenSides() throws Exception {
        Card card = Mockito.mock(Card.class);
        first.getTrading().append(card);
        addTradingCardC.setOpponent(third);
        addTradingCardC.setCardIndex(0);
        result = addTradingCardC.execute(first,game);
        assertThat("AddTradingCardToExchange: no exchanges in game, command result checking", result, is(StandardGameCommandResult.INVALID));

        game.getExchanges().add(new StandardExchange(first, second));
        addTradingCardC.setOpponent(third);
        addTradingCardC.setCardIndex(0);
        result = addTradingCardC.execute(first,game);
        assertThat("AddTradingCardToExchange: no exchange between indicated players, size of game exchanges list check", game.getExchanges().size(), is(1));
        assertThat("AddTradingCardToExchange: no exchange between indicated players, command result checking", result, is(StandardGameCommandResult.INVALID));
    }

    @Test
    public void testSAddTradingCardNotCurrentPlayer() throws Exception {
        Card card = Mockito.mock(Card.class);
        second.getTrading().append(card);
        addTradingCardC.setOpponent(first);
        addTradingCardC.setCardIndex(0);
        result = addTradingCardC.execute(second,game);
        assertThat("AddTradingCardToExchange: not current player, command result checking", result, is(StandardGameCommandResult.INVALID));
    }

    @Test
    public void testSAddTradingCardWrongCardIndex() throws Exception {
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);

        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.NEGOTIATING);
        addTradingCardC.setOpponent(second);
        result = addTradingCardC.execute(first,game);
        assertThat("(1)AddTradingCardToExchange: wrong card index, command result checking", result, is(StandardGameCommandResult.INVALID));

        Card card = Mockito.mock(Card.class);
        first.getTrading().append(card);
        addTradingCardC.setCardIndex(2);
        result = addTradingCardC.execute(first,game);
        assertThat("(2)AddTradingCardToExchange: wrong card index, command result checking", result, is(StandardGameCommandResult.INVALID));
    }

    @Test
    public void testSAddTradingCardWrongExchangeState() throws Exception {
        Card card = Mockito.mock(Card.class);
        first.getTrading().append(card);
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);

        exchange.setSideState(first, Exchange.SideState.ACCEPTING );
        exchange.setSideState(second, Exchange.SideState.ACCEPTING );
        addTradingCardC.setOpponent(second);
        addTradingCardC.setCardIndex(0);
        result = addTradingCardC.execute(first,game);
        assertThat("AddTradingCardToExchange: both players already accepted exchange, command result checking", result, is(StandardGameCommandResult.INVALID));

        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.DECLINING);
        result = addTradingCardC.execute(first,game);
        assertThat("AddTradingCardToExchange: exchange declined, command result checking", result, is(StandardGameCommandResult.INVALID));

        exchange.setSideState(second, Exchange.SideState.ACCEPTING);
        result = addTradingCardC.execute(first,game);
        assertThat("AddTradingCardToExchange: one player already accepted, command result checking", result, is(StandardGameCommandResult.INVALID));
    }

    @Test
    public void testSAddTradingCardWrongCardType() throws Exception {
        Map<Integer, Integer> chiliBeanOMeter = new HashMap<>();
        chiliBeanOMeter.put(3, 1);
        chiliBeanOMeter.put(6, 2);
        chiliBeanOMeter.put(8, 3);
        chiliBeanOMeter.put(9, 4);
        CardType chiliBean = new CardType("Chili Bean", chiliBeanOMeter, 18);
        Card chiliBeanCard = new Card(chiliBean, 1);

        second.getTrading().append(chiliBeanCard);
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);
        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.NEGOTIATING);
        exchange.getOfferedCards(first).add(chiliBeanCard);

        addTradingCardC.setOpponent(second);
        addTradingCardC.setCardIndex(0);
        result = addTradingCardC.execute(first,game);
        assertThat("AddTradingCardToExchange: player already added indicated card to exchange, command result checking", result, is(StandardGameCommandResult.INVALID));
        assertThat("AddTradingCardToExchange: player already added indicated card to exchange, first player offered card list size check", exchange.getOfferedCards(first).size(), is(1));
    }

    @Test
    public void testSAddTradingCardCorrect() throws Exception {
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

        first.getTrading().append(chiliBeanCard);
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);
        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.NEGOTIATING);
        exchange.getOfferedCards(first).add(redBeanCard);

        int oldTradingSize = first.getTrading().pileSize();

        addTradingCardC.setOpponent(second);
        addTradingCardC.setCardIndex(0);
        result = addTradingCardC.execute(first,game);
        assertThat("AddTradingCardToExchange: player added card to exchange, command result checking", result, is(StandardGameCommandResult.TRADE));
        assertThat("AddTradingCardToExchange: player added card to exchange, first player offered card list size check", exchange.getOfferedCards(first).size(), is(2));
        assertThat("AddTradingCardToExchange: player added card to exchange, first player trading size check", first.getTrading().pileSize(), is(oldTradingSize));
        assertThat("AddTradingCardToExchange: player added card to exchange, first player offered card check", exchange.getOfferedCards(first).get(1), is(equalTo(chiliBeanCard)));
    }
}