package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
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

public class DefaultHarvestCommandTest extends TestCase {

    DefaultPlayer player;
    DefaultGame game;
    DefaultHarvestCommand harvestC;

    @Before
    public void setUp() throws Exception {
        player = new DefaultPlayer("TestPlayer1");
        game = new DefaultGame();
        game.addPlayers(player);
        game.initialize();

        harvestC = new DefaultHarvestCommand();
        harvestC.setPlayer(player);
        harvestC.setFieldIndex(0);
    }

    @Test
    public void testExecute() throws Exception {
        GameCommandResult status = harvestC.execute(game);
        assertThat("Harvesting from field without any cards", status, is(DefaultGameCommandResult.INVALID));
        generateFieldCards(0, 1);
        status = harvestC.execute(game);
        assertThat("Harvesting from field with only one card, other fields are empty", status, is(DefaultGameCommandResult.INVALID));
        generateFieldCards(1, 1);
        status = harvestC.execute(game);
        assertThat("Harvesting from field with only one card, all player's fields consist of one card", status, is(DefaultGameCommandResult.HARVEST));
        generateFieldCards(0, 3);
        status = harvestC.execute(game);
        assertThat("Harvesting with the equal number of cards to those required in beanOMeter", status, is(DefaultGameCommandResult.HARVEST));
        assertThat("(0) Player's field after harvesting should be empty", player.getAllFields().get(0).pileSize(), is(0));
        assertThat("Player's treasury - 1 card added", player.getTreasury().pileSize(), is(2));
        assertThat("Game's discardPile - 2 cards added", game.getDiscardPile().pileSize(), is(2));
        generateFieldCards(1, 6);
        harvestC.setFieldIndex(1);
        status = harvestC.execute(game);
        assertThat("Harvesting from field with not equal number of cards to those required in beanOMeter ", status, is(DefaultGameCommandResult.HARVEST));
        assertThat("(1) Player's field after harvesting should be empty", player.getAllFields().get(1).pileSize(), is(0));
        assertThat("Player's treasury - 2 cards added", player.getTreasury().pileSize(), is(4));
        assertThat("Game's discardPile - 5 cards added", game.getDiscardPile().pileSize(), is(7));
    }

    /**
     * adds Chili Bean cards to player's field
     *
     * @param index index of the field to be filled with cards
     * @param num number of additional ChilliCards to be placed in the field
     */
    private void generateFieldCards(int index, int num) {

        Map<Integer, Integer> chiliBeanOMeter = new HashMap<>();
        chiliBeanOMeter.put(3, 1);
        chiliBeanOMeter.put(6, 2);
        chiliBeanOMeter.put(8, 3);
        chiliBeanOMeter.put(9, 4);
        CardType chiliBean = new CardType("Chili Bean", chiliBeanOMeter, 18);
        for (int i = 0; i < num; ++i) {
            Card c = new Card(chiliBean);
            this.player.getAllFields().get(index).append(c);
        }
    }
}
