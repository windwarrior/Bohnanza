package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.Pile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

public class DefaultDrawTradeCommandTest {

    DefaultPlayer player;
    DefaultGame game;
    DefaultDrawTradeCommand drawTradeC;

    @Before
    public void setUp() throws Exception {
        player = new DefaultPlayer("TestPlayer");
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
        assertThat("Game pile removes two cards", game.getGamePile().pileSize(), is(gamePileSize - 2));
        assertThat("Trading pile gets two cards", player.getTrading().pileSize(), is(playerTradingSize + 2));
        //Test if correct cards are added to trading area
        assertThat("First card", ((HandPile) player.getTrading()).getCardType(0).get(), is(testPile.pop().get().getCardType()));
        assertThat("Second card", ((HandPile) player.getTrading()).getCardType(1).get(), is(testPile.pop().get().getCardType()));

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
