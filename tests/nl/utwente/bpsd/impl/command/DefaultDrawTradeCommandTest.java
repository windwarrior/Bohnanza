package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.Pile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

/**
 * Created by Jochem Elsinga on 5/9/2015.
 */
public class DefaultDrawTradeCommandTest {

    Player player;
    Game game;
    DefaultDrawTradeCommand drawTradeC;

    @Before
    public void setUp() throws Exception {
        player = new Player("TestPlayer");
        game = new DefaultGame();
        game.addPlayers(player);
        game.initialize();
        drawTradeC = new DefaultDrawTradeCommand();
        drawTradeC.setPlayer(player);
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Execute should draw two cards from game pile into players trade pile
     */
    @Test
    public void testExecute() throws Exception {
        assertEquals("Trade area empty before execute", 0, player.getTrading().pileSize());
        Pile testPile = new Pile(game.getGamePile());
        int gamePileSize = testPile.pileSize();
        int playerTradingSize = player.getTrading().pileSize();
        drawTradeC.execute(game);
        assertThat("Game pile removes two cards", gamePileSize - 2, is(game.getGamePile().pileSize()));
        assertThat("Trading pile gets two cards", playerTradingSize + 2, is(player.getTrading().pileSize()));
        //Test if correct cards are added to trading area
        assertThat("First card", testPile.pop().getCardType(), is(((HandPile) player.getTrading()).getCardType(0).get()));
        assertThat("Second card", testPile.pop().getCardType(), is(((HandPile) player.getTrading()).getCardType(1).get()));

    }

    /**
     * Should test to see if a draw still happens even after a reshuffle is done
     */
    @Test
    public void testReshuffledDraw() throws Exception {
    }

    /**
     * Should test what happens if we want to draw but the deck is finished and
     * has been reshuffled twice
     */
    @Test
    public void testEndGame() throws Exception {

    }
}
