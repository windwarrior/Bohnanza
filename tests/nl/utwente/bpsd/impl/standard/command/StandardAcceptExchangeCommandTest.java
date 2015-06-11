package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.StandardExchange;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Exchange;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.pile.HandPile;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class StandardAcceptExchangeCommandTest {

    StandardPlayer first;
    StandardPlayer second;
    StandardGame game;
    StandardAcceptExchangeCommand acceptExchangeC;
    GameCommandResult result;

    @Before
    public void setUp() throws Exception {
        first = new StandardPlayer("First");
        second = new StandardPlayer("Second");
        game = new StandardGame();
        StandardPlayer t[] = new StandardPlayer[]{first, second};
        game.addPlayers(t);
        acceptExchangeC = new StandardAcceptExchangeCommand();
    }

    @Test
    public void testSAcceptExchangeNoExchangeBetweenSides() throws Exception {
        StandardPlayer third = new StandardPlayer("Third");
        acceptExchangeC.setOpponent(third);
        result = acceptExchangeC.execute(first,game);
        assertThat("AcceptExchange: no exchanges in game, command result checking", result, is(StandardGameCommandResult.INVALID));

        game.getExchanges().add(new StandardExchange(first, second));
        acceptExchangeC.setOpponent(third);
        result = acceptExchangeC.execute(first,game);
        assertThat("AcceptExchange: no exchange between indicated players, command result checking", result, is(StandardGameCommandResult.INVALID));
        assertThat("AcceptExchange: no exchange between indicated players, size of game exchanges list check", game.getExchanges().size(), is(1));

    }

    @Test
    public void testSAcceptExchangeWrongExchangeState() throws Exception {
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);

        exchange.setSideState(first, Exchange.SideState.ACCEPTING );
        exchange.setSideState(second, Exchange.SideState.ACCEPTING );
        acceptExchangeC.setOpponent(second);
        result = acceptExchangeC.execute(first,game);
        assertThat("AcceptExchange: both players already accepted exchange, command result checking", result, is(StandardGameCommandResult.INVALID));

        exchange.setSideState(first, Exchange.SideState.DECLINING);
        acceptExchangeC.setOpponent(first);
        result = acceptExchangeC.execute(second,game);
        assertThat("AcceptExchange: exchange declined, command result checking", result, is(StandardGameCommandResult.INVALID));

        exchange.setSideState(first, Exchange.SideState.ACCEPTING);
        exchange.setSideState(second, Exchange.SideState.NEGOTIATING);
        acceptExchangeC.setOpponent(second);
        result = acceptExchangeC.execute(first,game);
        assertThat("AcceptExchange: player already accepted the exchange, command result checking", result, is(StandardGameCommandResult.INVALID));
    }

    @Test
    public void testSAcceptExchangeCorrectExchnageState() throws Exception {
        game.getExchanges().add(new StandardExchange(first, second));
        StandardExchange exchange = (StandardExchange)game.getExchanges().get(0);

        //player as a first one accepted the exchange
        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.NEGOTIATING);
        acceptExchangeC.setOpponent(first);
        result = acceptExchangeC.execute(second,game);
        assertThat("AcceptExchange: player as a first one accepted the exchange, result check", result, is(StandardGameCommandResult.TRADE));
        assertThat("AcceptExchange: player as a first one accepted the exchange, players state check", exchange.getSideState(second), is(Exchange.SideState.ACCEPTING));

        //player as a second one accepted the exchange
        Map<Integer, Integer> chiliBeanOMeter = new HashMap<>();
        chiliBeanOMeter.put(3, 1);
        chiliBeanOMeter.put(6, 2);
        chiliBeanOMeter.put(8, 3);
        chiliBeanOMeter.put(9, 4);
        CardType chiliBean = new CardType("Chili Bean", chiliBeanOMeter, 18);

        Map<Integer, Integer> cocoaBeanOMeter = new HashMap<>();
        cocoaBeanOMeter.put(2, 2);
        cocoaBeanOMeter.put(3, 3);
        cocoaBeanOMeter.put(4, 4);
        CardType cocoaBean = new CardType("Cocoa Bean", cocoaBeanOMeter, 4);

        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType redBean = new CardType("Red Bean", redBeanOMeter, 8);

        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 18);

        Card chiliBeanCard = new Card(chiliBean, 1);
        Card cocoaBeanCard = new Card(cocoaBean, 2);
        Card redBeanCard = new Card(redBean, 3);
        Card blackeyedBeanCard = new Card(blackeyedBean, 4);
        Card chiliBeanCard2 = new Card(chiliBean, 5);
        //first player offer
        exchange.getOfferedCards(first).add(chiliBeanCard);
        //second player offer
        exchange.getOfferedCards(second).add(cocoaBeanCard);
        exchange.getOfferedCards(second).add(redBeanCard);
        //first player hand
        first.getHand().append(chiliBeanCard);
        first.getHand().append(blackeyedBeanCard);
        //second player hand
        second.getHand().append(cocoaBeanCard);
        second.getHand().append(chiliBeanCard2);
        second.getHand().append(redBeanCard);

        acceptExchangeC.setOpponent(second);
        result = acceptExchangeC.execute(first,game);
        assertThat("AcceptExchange: both players accepted exchange, size of game exchanges list check", game.getExchanges().size(), is(0));
        assertThat("AcceptExchange: both players accepted exchange, result check", result, is(StandardGameCommandResult.TRADE));
        assertThat("AcceptExchange: both players accepted exchange, first player hand size check", first.getHand().pileSize(), is(1));
        assertThat("AcceptExchange: both players accepted exchange, second player hand size check", second.getHand().pileSize(), is(1));
        assertThat("AcceptExchange: both players accepted exchange, first player trading size check", first.getTrading().pileSize(), is(2));
        assertThat("AcceptExchange: both players accepted exchange, second player trading size check", second.getTrading().pileSize(), is(1));
        Optional<Card> card1 = ((HandPile)first.getHand()).getCardCopy(0);
        Optional<Card> card2 = ((HandPile)first.getTrading()).getCardCopy(0);
        Optional<Card> card3 = ((HandPile)first.getTrading()).getCardCopy(1);
        Optional<Card> card4 = ((HandPile)second.getHand()).getCardCopy(0);
        Optional<Card> card5 = ((HandPile)second.getTrading()).getCardCopy(0);
        assertThat("AcceptExchange: both players accepted exchange, first player hand card check", card1.get(), is(equalTo(blackeyedBeanCard)));
        assertThat("AcceptExchange: both players accepted exchange, first player trading 1st card check", card2.get(), is(equalTo(cocoaBeanCard)));
        assertThat("AcceptExchange: both players accepted exchange, first player trading 2nd card check", card3.get(), is(equalTo(redBeanCard)));
        assertThat("AcceptExchange: both players accepted exchange, second player hand card check", card4.get(), is(equalTo(chiliBeanCard2)));
        assertThat("AcceptExchange: both players accepted exchange, second player trading card check", card5.get(), is(equalTo(chiliBeanCard)));

        //donation
        StandardPlayer third = new StandardPlayer("Third");
        game.getExchanges().add(new StandardExchange(second, third));
        exchange = (StandardExchange)game.getExchanges().get(0);
        exchange.setSideState(second, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(third, Exchange.SideState.ACCEPTING);
        exchange.getOfferedCards(second).add(chiliBeanCard2);
        acceptExchangeC.setOpponent(third);
        result = acceptExchangeC.execute(second,game);
        assertThat("AcceptExchange: both players accepted donation, size of game exchanges list check", game.getExchanges().size(), is(0));
        assertThat("AcceptExchange: both players accepted donation, result check", result, is(StandardGameCommandResult.TRADE));
        assertThat("AcceptExchange: both players accepted donation, second player hand size check", second.getHand().pileSize(), is(0));
        assertThat("AcceptExchange: both players accepted donation, third player hand size check", third.getHand().pileSize(), is(0));
        assertThat("AcceptExchange: both players accepted donation, second player trading size check", second.getTrading().pileSize(), is(1));
        assertThat("AcceptExchange: both players accepted donation, third player trading size check", third.getTrading().pileSize(), is(1));
        card1 = ((HandPile)third.getTrading()).getCardCopy(0);
        assertThat("AcceptExchange: both players accepted donation, third player trading card check", card1.get(), is(equalTo(chiliBeanCard2)));
    }

}