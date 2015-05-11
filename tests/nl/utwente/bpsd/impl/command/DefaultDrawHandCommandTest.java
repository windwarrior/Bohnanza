package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.pile.HandPile;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultDrawHandCommandTest {

    DefaultPlayer player;
    DefaultGame game;
    DefaultDrawHandCommand drawHandC;

    @Before
    public void setUp() throws Exception {
        player = new DefaultPlayer("TestPlayer");
        game = new DefaultGame();
        game.addPlayers(player);
        game.initialize();
        drawHandC = new DefaultDrawHandCommand();
        drawHandC.setPlayer(player);
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Three cards removed from game gamePile Three cards added to player hand
     */
    @Test
    public void testStandardExecute() throws Exception {
        Pile testPile = new Pile(game.getGamePile());
        int gamePileSize = testPile.pileSize();
        int handPileSize = player.getHand().pileSize();
        drawHandC.execute(game);
        assertThat("Game pile size decreased by three", game.getGamePile().pileSize(), is(gamePileSize - 3));
        assertThat("Hand pile size increased by three", player.getHand().pileSize(), is(handPileSize + 3));
        //Test if last three hand pile cards have types of the top of the game pile (in order)
        assertThat("first added card", ((HandPile) player.getHand()).getCardType(player.getHand().pileSize() - 3).get(), is((testPile.pop().getCardType())));
        assertThat("second added card", ((HandPile) player.getHand()).getCardType(player.getHand().pileSize() - 2).get(), is((testPile.pop().getCardType())));
        assertThat("third added card", ((HandPile) player.getHand()).getCardType(player.getHand().pileSize() - 1).get(), is((testPile.pop().getCardType())));
    }

    @Test
    public void testEdgeCaseExecute() throws Exception {

    }
}
