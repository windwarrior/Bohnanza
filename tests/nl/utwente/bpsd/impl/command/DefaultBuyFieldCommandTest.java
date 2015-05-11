package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.Pile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

/**
 * Created by Jochem Elsinga on 5/9/2015.
 */
public class DefaultBuyFieldCommandTest {

    Player player;
    Game game;
    DefaultBuyFieldCommand buyFieldC;

    @Before
    public void setUp() throws Exception {
        player = new Player("TestPlayer");
        game = new DefaultGame();
        game.addPlayers(player);
        game.initialize();
        buyFieldC = new DefaultBuyFieldCommand();
        buyFieldC.setPlayer(player);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testExecute() throws Exception {
        assertThat("Test number start of fields", 2, is(player.getAllFields().size()));
        buyFieldC.execute(game);
        assertThat("Not enough money for a field", 2, is(player.getAllFields().size()));
        //Setup treasury to buy a third field (should be allowed, so treasury will be empty after buying field
        fillTreasury();
        buyFieldC.execute(game);
        assertThat("Buy a third field", 3, is(player.getAllFields().size()));
        assertThat("Check if treasury is used", 0, is(player.getTreasury().pileSize()));
        //Setup treasury to buy a fourth field (shouldn't be allowed, treasury should still be full)
        fillTreasury();
        buyFieldC.execute(game);
        assertThat("Buy a fourth field", 3, is(player.getAllFields().size()));
        assertThat("Check if treasury is used", 3, is(player.getTreasury().pileSize()));
    }

    //This test should check the integrity of the player fields after a new field has been added
    //4 cases:
    //  1. 2 fields + enough money,
    //  2. 2 fields + non enough money,
    //  3. 3 fields + enough money,
    //  4. 3 fields + not enough money
    // TODO: How should we compare lists of piles??
    @Test
    public void testFieldConsistency() throws Exception{
        List<Pile> testField = new ArrayList<>();
        testField.add(new Pile());
        testField.add(new Pile());
        buyFieldC.execute(game);
        assertThat("Field integrity case 1", testField, is(player.getAllFields()));
        fillTreasury();
        buyFieldC.execute(game);
        testField.add(new Pile());
        assertThat("Field integrity case 2", testField, is(player.getAllFields()));
        buyFieldC.execute(game);
        assertThat("Field integrity case 3", testField, is(player.getAllFields()));
        fillTreasury();
        buyFieldC.execute(game);
        assertThat("Field integrity cate 4", testField, is(player.getAllFields()));
    }

    //Put some coins (cards) from the game pill into the players treasury
    private void fillTreasury(){
        player.getTreasury().append(game.getGamePile().pop());
        player.getTreasury().append(game.getGamePile().pop());
        player.getTreasury().append(game.getGamePile().pop());
    }
}