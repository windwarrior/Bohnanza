package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.command.StandardPlantCommand;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import junit.framework.TestCase;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;

public class StandardPlantCommandTest extends TestCase {

    // TODO: alter tests to now use setCardIndex(int index) instead of setCard(Card c)
    // TODO: create different tests for result StandardGameCommandResult.PLANT_TRADING instead of StandardGameCommandResult.PLANT

    StandardPlayer player;
    Game game;
    StandardPlantCommand plantC;

    @Before
    public void setUp() throws Exception {
        player = new StandardPlayer("TestPlayer");
        game = new StandardGame();
        game.addPlayers(player);
        game.initialize();

        plantC = new StandardPlantCommand();
        plantC.setFieldIndex(0);
        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 18);
        //plantC.setCard(new Card(blackeyedBean));
    }

    @Test
    public void testPlantOnNotEmptyField() throws Exception {
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting on non empty field: command result checking", result, is(StandardGameCommandResult.PLANT));
        assertThat("Planting on not empty field: field checking ", player.getAllFields().get(0).pileSize(), is(1));
    }

    @Test
    public void testPlantOnEmptyField() throws Exception {
        GameCommandResult status = plantC.execute(player,game);
        assertThat("Planting on empty field: command result checking", status, is(StandardGameCommandResult.PLANT));
        assertThat("Planting on empty field: field checking ", player.getAllFields().get(0).pileSize(), is(1));
    }

    @Test
    public void testPlantWrongCardType() throws Exception {
        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType redBean = new CardType("Red Bean", redBeanOMeter, 18);
        //plantC.setCard(new Card(redBean));
        generateFieldCards(1, 3);
        plantC.setFieldIndex(1);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting on field with wrong cards' type: command result checking", result, is(StandardGameCommandResult.INVALID));
        assertThat("Planting on field with wrong cards' type: field checking ", player.getAllFields().get(1).pileSize(), is(3));
    }

    /**
     * adds Black-eyed Bean cards to player's field
     *
     * @param index index of the field to be filled with cards
     * @param num number of additional Black-eyed Beans to be placed in the
     * field
     */
    private void generateFieldCards(int index, int num) {

        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 18);
        for (int i = 0; i < num; ++i) {
            Card c = new Card(blackeyedBean);
            this.player.getAllFields().get(index).append(c);
        }
    }
}
