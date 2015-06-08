package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.command.StandardHarvestCommand;
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

public class StandardHarvestCommandTest extends TestCase {

    StandardPlayer player;
    StandardGame game;
    StandardHarvestCommand harvestC;

    @Before
    public void setUp() throws Exception {
        player = new StandardPlayer("TestPlayer1");
        game = new StandardGame();
        game.addPlayers(player);
        game.initialize();

        harvestC = new StandardHarvestCommand();
        harvestC.setFieldIndex(0);
    }

    @Test
    public void testHarvestFromEmptyField() throws Exception{
        GameCommandResult result = harvestC.execute(player,game);
        assertThat("Harvesting from field without any cards: result", result, is(StandardGameCommandResult.INVALID));
        assertThat("Player's treasury - no cards added", player.getTreasury().pileSize(), is(0));
        assertThat("Game's discardPile - no cards added", game.getDiscardPile().pileSize(), is(0));
    }
    @Test
    public void testHarvestFromFieldWithOneCard() throws Exception{
        generateFieldCards(0, 1);
        GameCommandResult result = harvestC.execute(player,game);
        assertThat("Harvesting from field with only one card, other fields are empty: result", result, is(StandardGameCommandResult.INVALID));
        assertThat("Player's treasury - no cards added", player.getTreasury().pileSize(), is(0));
        assertThat("Game's discardPile - no cards added", game.getDiscardPile().pileSize(), is(0));
        generateFieldCards(1, 1);
        result = harvestC.execute(player,game);
        assertThat("Harvesting from field with only one card, all player's fields consist of one card", result, is(StandardGameCommandResult.HARVEST));
        assertThat("Player's treasury - no cards added", player.getTreasury().pileSize(), is(0));
        assertThat("Game's discardPile - 1 card added", game.getDiscardPile().pileSize(), is(1));
    }

    @Test
    public void testHarvestEqualNumberOfCardsAsInBeanometer() throws Exception{
        generateFieldCards(0, 3);
        GameCommandResult result = harvestC.execute(player,game);
        assertThat("Harvesting with the equal number of cards to those required in beanOMeter: result", result, is(StandardGameCommandResult.HARVEST));
        assertThat("Player's field after harvesting should be empty", player.getAllFields().get(0).pileSize(), is(0));
        assertThat("Player's treasury - 1 card added", player.getTreasury().pileSize(), is(1));
        assertThat("Game's discardPile - 2 cards added", game.getDiscardPile().pileSize(), is(2));
    }
    @Test
    public void testHarvestNotEqualNumberOfCardsAsInBeanometer() throws Exception{
        generateFieldCards(1, 7);
        generateFieldCards(0, 1);
        harvestC.setFieldIndex(1);
        GameCommandResult result = harvestC.execute(player,game);
        assertThat("Harvesting from field with not equal number of cards to those required in beanOMeter: result ", result, is(StandardGameCommandResult.HARVEST));
        assertThat("Player's field after harvesting should be empty", player.getAllFields().get(1).pileSize(), is(0));
        assertThat("Number of cards on other fields should not be changed", player.getAllFields().get(0).pileSize(), is(1));
        assertThat("Player's treasury - 2 cards added", player.getTreasury().pileSize(), is(2));
        assertThat("Game's discardPile - 5 cards added", game.getDiscardPile().pileSize(), is(5));
    }

    @Test
    public void testHarvestLowerNumberOfCardsThanInLowestBeanometer() throws Exception{
        generateFieldCards(1, 1);
        generateFieldCards(0, 2);
        harvestC.setFieldIndex(0);
        GameCommandResult result = harvestC.execute(player,game);
        assertThat("Harvesting from field with not equal number of cards to those required in beanOMeter: result ", result, is(StandardGameCommandResult.HARVEST));
        assertThat("Player's field after harvesting should be empty", player.getAllFields().get(0).pileSize(), is(0));
        assertThat("Number of cards on other fields should not be changed", player.getAllFields().get(1).pileSize(), is(1));
        assertThat("Player's treasury - no cards added", player.getTreasury().pileSize(), is(0));
        assertThat("Game's discardPile - 2 cards added", game.getDiscardPile().pileSize(), is(2));
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
            Card c = new Card(chiliBean, i);
            this.player.getAllFields().get(index).append(c);
        }
    }
}
