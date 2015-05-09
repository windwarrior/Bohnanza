package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.pile.HandPile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jochem Elsinga on 5/9/2015.
 */
public class DefaultDrawHandCommandTest {

    Player player;
    Game game;
    DefaultDrawHandCommand drawHandC;

    @Before
    public void setUp() throws Exception {
        player = new Player("TestPlayer");
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
     * Three cards removed from game gamePile
     * Three cards added to player hand
     */
    @Test
    public void testStandardExecute() throws Exception {
        Pile testPile = game.getGamePile();
        int gamePileSize = testPile.pileSize();
        int handPileSize = player.getHand().pileSize();
        drawHandC.execute(game);
        assertEquals("Game pile size decreased by three", gamePileSize - 3, game.getGamePile().pileSize());
        assertEquals("Hand pile size increased by three", handPileSize + 3, player.getHand().pileSize());
        //Test if last three hand pile cards have types of the top of the game pile (in order)
        assertEquals("first added card", (testPile.pop().getCardType()), ((HandPile) player.getHand()).getCardType(player.getHand().pileSize() - 3).get());
        assertEquals("second added card", (testPile.pop().getCardType()), ((HandPile) player.getHand()).getCardType(player.getHand().pileSize() - 2).get());
        assertEquals("third added card", (testPile.pop().getCardType()), ((HandPile) player.getHand()).getCardType(player.getHand().pileSize() - 1).get());
    }

    @Test
    public void testEdgeCaseExecute() throws Exception {

    }
}