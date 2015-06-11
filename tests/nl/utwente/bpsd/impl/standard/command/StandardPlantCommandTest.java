package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import junit.framework.TestCase;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;

public class StandardPlantCommandTest extends TestCase {

    StandardPlayer player;
    Game game;
    StandardPlantCommand plantC;

    @Before
    public void setUp() throws Exception {
        player = new StandardPlayer("TestPlayer");
        game = new StandardGame();
        game.addPlayers(player);
        game.initialize();

        //player's hand with 2 blackeyedBeans
        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 18);
        player.getHand().append(new Card(blackeyedBean, 1));
        player.getHand().append(new Card(blackeyedBean, 2));

        //player's trading area with  blackeyedBean, and redBean
        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType redBean = new CardType("Red Bean", redBeanOMeter, 18);
        player.getTrading().append(new Card(blackeyedBean, 3));
        player.getTrading().append(new Card(redBean, 4));

        plantC = new StandardPlantCommand();
        plantC.setFieldIndex(0);
    }

    @Test
    public void testPlantFromHandIncorrectFieldIndex() throws Exception {
        int oldHandSize = player.getHand().pileSize();
        int oldTradingSize = player.getTrading().pileSize();
        generateFieldCards(1, 2);
        plantC.setFieldIndex(2);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from hand, incorrect field index: command result checking", result, is(StandardGameCommandResult.INVALID));
        assertThat("Planting from hand, incorrect field index: 2nd field size checking", player.getAllFields().get(1).pileSize(), is(2));
        assertThat("Planting from hand, incorrect field index: 1st field size checking", player.getAllFields().get(0).pileSize(), is(0));
        assertThat("Planting from hand, incorrect field index: hand size checking", player.getHand().pileSize(), is(oldHandSize));
        assertThat("Planting from hand, incorrect field index: trading size checking", player.getTrading().pileSize(), is(oldTradingSize));
    }

    @Test
    public void testPlantFromHandOnNotEmptyField() throws Exception {
        int oldHandSize = player.getHand().pileSize();
        generateFieldCards(1, 2);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from hand on non empty field: command result checking", result, is(StandardGameCommandResult.PLANT));
        assertThat("Planting from hand on not empty field: field size checking", player.getAllFields().get(0).pileSize(), is(1));
        assertThat("Planting from hand on not empty field: hand size checking", player.getHand().pileSize(), is(oldHandSize - 1));
    }

    @Test
    public void testPlantFromHandOnEmptyField() throws Exception {
        int oldHandSize = player.getHand().pileSize();
        GameCommandResult status = plantC.execute(player,game);
        assertThat("Planting from hand on empty field: command result checking", status, is(StandardGameCommandResult.PLANT));
        assertThat("Planting from hand on empty field: field size checking ", player.getAllFields().get(0).pileSize(), is(1));
        assertThat("Planting from hand on empty field: hand size checking", player.getHand().pileSize(), is(oldHandSize - 1));
    }

    @Test
    public void testPlantFromHandWrongCardType() throws Exception {
        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType redBean = new CardType("Red Bean", redBeanOMeter, 18);
        //remove two blackeyedBeans from hand
        player.getHand().pop();
        player.getHand().pop();
        //add redBean to player's hand
        player.getHand().append(new Card(redBean, 6));
        generateFieldCards(1, 3);

        plantC.setFieldIndex(1);
        int oldHandSize = player.getHand().pileSize();
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from hand on field with wrong cards' type: command result checking", result, is(StandardGameCommandResult.INVALID));
        assertThat("Planting from hand on field with wrong cards' type: field size checking ", player.getAllFields().get(1).pileSize(), is(3));
        assertThat("Planting from hand on empty field: hand size checking", player.getHand().pileSize(), is(oldHandSize));
    }

    @Test
    public void testPlantFromTradingOnNotEmptyField() throws Exception {
        int oldTradingSize = player.getTrading().pileSize();
        generateFieldCards(1, 2);
        //blackeyedBean planting
        plantC.setCardIndex(0);
        plantC.setFieldIndex(1);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from trading on non empty field: command result checking", result, is(StandardGameCommandResult.PLANT_TRADED));
        assertThat("Planting from trading on not empty field: field size checking", player.getAllFields().get(1).pileSize(), is(3));
        assertThat("Planting from trading on not empty field: trading size checking", player.getTrading().pileSize(), is(oldTradingSize - 1));
        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType redBean = new CardType("Red Bean", redBeanOMeter, 18);
        assertThat("Planting from trading on not empty field: trading remaining card type checking", player.getTrading().peek().get(), is(equalTo(redBean)));
    }

    @Test
    public void testPlantFromTradingOnEmptyField() throws Exception {
        int oldTradingSize = player.getTrading().pileSize();
        //redBean planting
        plantC.setCardIndex(1);
        plantC.setFieldIndex(1);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from trading on empty field: command result checking", result, is(StandardGameCommandResult.PLANT_TRADED));
        assertThat("Planting from trading on empty field: field size checking", player.getAllFields().get(1).pileSize(), is(1));
        assertThat("Planting from trading on empty field: trading size checking", player.getTrading().pileSize(), is(oldTradingSize - 1));
        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 18);
        assertThat("Planting from trading on not empty field: trading remaining card type checking", player.getTrading().peek().get(), is(equalTo(blackeyedBean)));
    }

    @Test
    public void testPlantFromTradingWrongCardType() throws Exception {
        int oldTradingSize = player.getTrading().pileSize();
        generateFieldCards(0, 2);
        //redBean planting
        plantC.setCardIndex(1);
        plantC.setFieldIndex(0);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from trading on field with wrong cards' type: command result checking", result, is(StandardGameCommandResult.INVALID));
        assertThat("Planting from trading on field with wrong cards' type: field size checking", player.getAllFields().get(0).pileSize(), is(2));
        assertThat("Planting from trading on field with wrong cards' type: trading size checking", player.getTrading().pileSize(), is(oldTradingSize));
    }

    @Test
    public void testPlantFromEmptyTrading() throws Exception {
        //clear player's trading pile
        player.getTrading().pop();
        player.getTrading().pop();
        int oldHandSize = player.getHand().pileSize();
        int oldTradingSize = player.getTrading().pileSize();
        generateFieldCards(0, 2);
        plantC.setCardIndex(1);
        plantC.setFieldIndex(0);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from empty trading: command result checking", result, is(StandardGameCommandResult.INVALID));
        assertThat("Planting from empty trading: 1st field size checking", player.getAllFields().get(0).pileSize(), is(2));
        assertThat("Planting from empty trading: 2nd field size checking", player.getAllFields().get(1).pileSize(), is(0));
        assertThat("Planting from empty trading: hand size checking", player.getHand().pileSize(), is(oldHandSize));
        assertThat("Planting from empty trading: trading size checking", player.getTrading().pileSize(), is(oldTradingSize));
    }

    @Test
    public void testPlantFromTradingIncorrectTradingIndex() throws Exception {
        int oldHandSize = player.getHand().pileSize();
        int oldTradingSize = player.getTrading().pileSize();
        generateFieldCards(1, 1);
        plantC.setFieldIndex(0);
        plantC.setCardIndex(2);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from trading, incorrect trading index: command result checking", result, is(StandardGameCommandResult.INVALID));
        assertThat("Planting from trading, incorrect trading index: 2nd field size checking", player.getAllFields().get(1).pileSize(), is(1));
        assertThat("Planting from trading, incorrect trading index: 1st field size checking", player.getAllFields().get(0).pileSize(), is(0));
        assertThat("Planting from trading, incorrect trading index: hand size checking", player.getHand().pileSize(), is(oldHandSize));
        assertThat("Planting from trading, incorrect trading index: trading size checking", player.getTrading().pileSize(), is(oldTradingSize));
    }

    /**
     * adds Black-eyed Bean cards to player's field
     *
     * @param index index of the field to be filled with cards
     * @param num number of additional Black-eyed Beans to be placed in the field
     */
    private void generateFieldCards(int index, int num) {

        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 18);
        for (int i = 0; i < num; ++i) {
            Card c = new Card(blackeyedBean, i);
            this.player.getAllFields().get(index).append(c);
        }
    }
}
