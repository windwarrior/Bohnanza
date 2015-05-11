package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.pile.Pile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

public class DefaultBuyFieldCommandTest {

    DefaultPlayer player;
    DefaultGame game;
    DefaultBuyFieldCommand buyFieldC;

    @Before
    public void setUp() throws Exception {
        player = new DefaultPlayer("TestPlayer");
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
        assertThat("Test number start of fields", player.getAllFields().size(), is(2));
        buyFieldC.execute(game);
        assertThat("Not enough money for a field", player.getAllFields().size(), is(2));
        //Setup treasury to buy a third field (should be allowed, so treasury will be empty after buying field
        fillTreasury();
        buyFieldC.execute(game);
        assertThat("Buy a third field", player.getAllFields().size(), is(3));
        assertThat("Check if treasury is used", player.getTreasury().pileSize(), is(0));
        //Setup treasury to buy a fourth field (shouldn't be allowed, treasury should still be full)
        fillTreasury();
        buyFieldC.execute(game);
        assertThat("Buy a fourth field", player.getAllFields().size(), is(3));
        assertThat("Check if treasury is used", player.getTreasury().pileSize(), is(3));
    }

    //This test should check the integrity of the player fields after a new field has been added
    //4 cases:
    //  1. 2 fields + enough money,
    //  2. 2 fields + non enough money,
    //  3. 3 fields + enough money,
    //  4. 3 fields + not enough money
    // TODO: How should we compare lists of piles??
    @Test
    public void testFieldConsistency() throws Exception {
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
    private void fillTreasury() {
        player.getTreasury().append(game.getGamePile().pop().get());
        player.getTreasury().append(game.getGamePile().pop().get());
        player.getTreasury().append(game.getGamePile().pop().get());
    }
}
