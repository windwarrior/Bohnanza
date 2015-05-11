package nl.utwente.bpsd.impl.command;

import junit.framework.TestCase;
import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.model.pile.Pile;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;

/**
 * Created by Kasia on 2015-05-10.
 */
public class DefaultHarvestCommandTest extends TestCase {

    Player player;
    Game game;
    DefaultHarvestCommand harvestC;

    @Before
    public void setUp() throws Exception {
        player = new Player("TestPlayer1");
        game = new DefaultGame();
        game.addPlayers(player);
        game.initialize();

        harvestC = new DefaultHarvestCommand();
        harvestC.setPlayer(player);
        harvestC.setFieldIndex(0);
    }

    @Test
    public void testExecute() throws Exception {
        GameStatus status = harvestC.execute(game);
        assertThat("Harvesting from field without any cards", GameStatus.GAME_HARVEST_ERROR, is(is(status)));
        generateFieldCards(0, 1);
        status = harvestC.execute(game);
        assertThat("Harvesting from field with only one card, other fields are empty", GameStatus.GAME_HARVEST_ERROR, is(status));
        generateFieldCards(1, 1);
        status = harvestC.execute(game);
        assertThat("Harvesting from field with only one card, all player's fields consist of one card", GameStatus.GAME_PROGRESS, is(status));
        generateFieldCards(0, 3);
        status = harvestC.execute(game);
        assertThat("Harvesting with the equal number of cards to those required in beanOMeter", GameStatus.GAME_PROGRESS, is(status));
        assertThat("(0) Player's field after harvesting should be empty", 0, is(player.getAllFields().get(0).pileSize()));
        assertThat("Player's treasury - 1 card added", 2, is(player.getTreasury().pileSize()));
        assertThat("Game's discardPile - 2 cards added", 2, is(game.getDiscardPile().pileSize()));
        generateFieldCards(1, 6);
        harvestC.setFieldIndex(1);
        status = harvestC.execute(game);
        assertThat("Harvesting from field with not equal number of cards to those required in beanOMeter ", GameStatus.GAME_PROGRESS, is(status));
        assertThat("(1) Player's field after harvesting should be empty", 0, is(player.getAllFields().get(1).pileSize()));
        assertThat("Player's treasury - 2 cards added", 4, is(player.getTreasury().pileSize()));
        assertThat("Game's discardPile - 5 cards added", 7, is(game.getDiscardPile().pileSize()));
    }

    /**
     * adds Chili Bean cards to player's field
     * @param index index of the field to be filled with cards
     * @param num number of additional ChilliCards to be placed in the field
     */
    private void generateFieldCards(int index, int num) {

        Map<Integer, Integer> chiliBeanOMeter = new HashMap<>();
        chiliBeanOMeter.put(3, 1);
        chiliBeanOMeter.put(6, 2);
        chiliBeanOMeter.put(8, 3);
        chiliBeanOMeter.put(9, 4);
        CardType chiliBean = new CardType("Chili Bean",chiliBeanOMeter,18);
        for (int i=0; i<num; ++i) {
            Card c = new Card(chiliBean);
            this.player.getAllFields().get(index).append(c);
        }
    }
}