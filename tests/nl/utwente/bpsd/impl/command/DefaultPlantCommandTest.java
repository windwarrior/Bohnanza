package nl.utwente.bpsd.impl.command;

import junit.framework.TestCase;
import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;

public class DefaultPlantCommandTest extends TestCase {

    Player player;
    Game game;
    DefaultPlantCommand plantC;

    @Before
    public void setUp() throws Exception {
        player = new Player("TestPlayer");
        game = new DefaultGame();
        game.addPlayers(player);
        game.initialize();

        plantC = new DefaultPlantCommand();
        plantC.setPlayer(player);
        plantC.setFieldIndex(0);
        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 18);
        plantC.setCard(new Card(blackeyedBean));
    }

    @Test
    public void testExecute() throws Exception {
        GameStatus status = plantC.execute(game);
        assertThat("Planting on empty field: game status checking", status, is(GameStatus.GAME_PROGRESS));
        assertThat("Planting on empty field: field checking ", player.getAllFields().get(0).pileSize(), is(1));
        plantC.execute(game);
        assertThat("Planting on not empty field: field checking ", player.getAllFields().get(0).pileSize(), is(2));
        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType redBean = new CardType("Red Bean", redBeanOMeter, 18);
        plantC.setCard(new Card(redBean));
        generateFieldCards(1, 3);
        plantC.setFieldIndex(1);
        status = plantC.execute(game);
        assertThat("Planting on field with wrong cards' type: game status checking", status, is(GameStatus.GAME_PLANT_ERROR));
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
