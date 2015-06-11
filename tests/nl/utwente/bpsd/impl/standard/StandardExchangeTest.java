package nl.utwente.bpsd.impl.standard;

import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Exchange;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class StandardExchangeTest {
    StandardExchange exchange;
    StandardPlayer first;
    StandardPlayer second;

    @Before
    public void setUp() throws Exception {
        first = new StandardPlayer("First");
        second = new StandardPlayer("Second");
        exchange = new StandardExchange(first, second);
    }

    @Test
    public void testIsPlayerInExchange() throws Exception {
        assertThat("Test if first player is in exchange:", exchange.isPlayerInExchange(first), is(true));
        assertThat("Test if second player is in exchange:", exchange.isPlayerInExchange(second), is(true));
        StandardPlayer third = new StandardPlayer("Third");
        assertThat("Test if there is wrong player in exchange:", exchange.isPlayerInExchange(third), is(false));
    }

    @Test
    public void testSetSideState() throws Exception {
        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.ACCEPTING);
        assertThat("Test first player state:", exchange.getFirstSideState(), is(Exchange.SideState.NEGOTIATING));
        assertThat("Test second player state:", exchange.getSecondSideState(), is(Exchange.SideState.ACCEPTING));
    }

    @Test
    public void testGetOfferedCards() throws Exception {

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

        assertThat("Test default number of offered cards for first player", exchange.getOfferedCards(first).size(), is(0));
        assertThat("Test default number of offered cards for second player", exchange.getOfferedCards(second).size(), is(0));

        Card chiliBeanCard = new Card(chiliBean, 1);
        Card cocoaBeanCard = new Card(cocoaBean, 2);
        Card redBeanCard = new Card(redBean, 3);
        exchange.getOfferedCards(first).add(chiliBeanCard);
        exchange.getOfferedCards(second).add(cocoaBeanCard);
        exchange.getOfferedCards(second).add(redBeanCard);
        assertThat("Test number of offered cards for first player", exchange.getOfferedCards(first).size(), is(1));
        assertThat("Test type of offered card of first player", exchange.getOfferedCards(first).get(0), is(equalTo(chiliBeanCard)));
        assertThat("Test number of offered cards for second player", exchange.getOfferedCards(second).size(), is(2));
        assertThat("Test type of 1st offered card of second player", exchange.getOfferedCards(second).get(0), is(equalTo(cocoaBeanCard)));
        assertThat("Test type of 2nd offered card of second player", exchange.getOfferedCards(second).get(1), is(equalTo(redBeanCard)));
    }

    @Test
    public void testIsStarted() throws Exception {
        assertThat("Test if exchange isStarted, default player's SideStates", exchange.isStarted(), is(false));
        exchange.setSideState(first, Exchange.SideState.NEGOTIATING);
        exchange.setSideState(second, Exchange.SideState.ACCEPTING);
        assertThat("Test if exchange isStarted, wrong player's SideStates", exchange.isStarted(), is(false));
        exchange.setSideState(second, Exchange.SideState.NEGOTIATING);
        assertThat("Test if exchange isStarted, correct player's SideStates", exchange.isStarted(), is(true));
    }

    @Test
    public void testIsStopped() throws Exception {
        assertThat("Test if exchange isStopped, default player's SideStates", exchange.isStopped(), is(false));
        exchange.setSideState(first, Exchange.SideState.DECLINING);
        exchange.setSideState(second, Exchange.SideState.DECLINING);
        assertThat("Test if exchange isStopped, wrong player's SideStates", exchange.isStopped(), is(false));
        exchange.setSideState(second, Exchange.SideState.IDLE);
        assertThat("Test if exchange isStopped, correct player's SideStates", exchange.isStopped(), is(true));
    }

    @Test
    public void testIsAccepted() throws Exception {
        assertThat("Test if exchange isAccepted, default player's SideStates", exchange.isAccepted(), is(false));
        exchange.setSideState(first, Exchange.SideState.ACCEPTING);
        exchange.setSideState(second, Exchange.SideState.DECLINING);
        assertThat("Test if exchange isAccepted, wrong player's SideStates", exchange.isAccepted(), is(false));
        exchange.setSideState(second, Exchange.SideState.ACCEPTING);
        assertThat("Test if exchange isAccepted, correct player's SideStates", exchange.isAccepted(), is(true));
    }

    @Test
    public void testIsDeclined() throws Exception {
        assertThat("Test if exchange isDeclined, default player's SideStates", exchange.isDeclined(), is(false));
        exchange.setSideState(first, Exchange.SideState.ACCEPTING);
        exchange.setSideState(second, Exchange.SideState.IDLE);
        assertThat("Test if exchange isDeclined, wrong player's SideStates", exchange.isDeclined(), is(false));
        exchange.setSideState(first, Exchange.SideState.DECLINING);
        assertThat("Test if exchange isDeclined, correct player's SideStates", exchange.isDeclined(), is(true));
    }

    @Test
    public void testIsFinished() throws Exception {
        assertThat("Test if exchange isFinished, default player's SideStates", exchange.isFinished(), is(false));
        exchange.setSideState(first, Exchange.SideState.ACCEPTING);
        exchange.setSideState(second, Exchange.SideState.NEGOTIATING);
        assertThat("Test if exchange isFinished, wrong player's SideStates", exchange.isFinished(), is(false));
        exchange.setSideState(second, Exchange.SideState.DECLINING);
        assertThat("Test if exchange isFinished, when isDeclined", exchange.isFinished(), is(true));
        exchange.setSideState(second, Exchange.SideState.ACCEPTING);
        assertThat("Test if exchange isFinished, when isAccepted", exchange.isFinished(), is(true));
    }
}