package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.standard.command.StandardDrawTradeCommand;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.Pile;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

public class DefaultDrawTradeCommandTest {

    StandardPlayer player;
    StandardGame game;
    StandardDrawTradeCommand drawTradeC;

    @Before
    public void setUp() throws Exception {
        player = new StandardPlayer("TestPlayer");
        game = new StandardGame();
        game.addPlayers(player);
        game.initialize();
        drawTradeC = new StandardDrawTradeCommand();
    }

    /**
     * Execute should draw StandardDrawTradeCommand.DRAW_TRADING_AMOUNT cards from game pile into players trade pile
 NOTE: StandardDrawTradeCommand.DRAW_TRADING_AMOUNT = 2;
     */
    @Test
    public void testExecute() throws Exception {
        assertEquals("Trade area empty before execute", 0, player.getTrading().pileSize());
        Pile testPile = new Pile(game.getGamePile());
        int gamePileSize = testPile.pileSize();
        int playerTradingSize = player.getTrading().pileSize();
        GameCommandResult result = drawTradeC.execute(player, game);
        assertThat("Game pile removes two cards", game.getGamePile().pileSize(), is(gamePileSize - StandardDrawTradeCommand.DRAW_TRADING_AMOUNT));
        assertThat("Trading pile gets two cards", player.getTrading().pileSize(), is(playerTradingSize + StandardDrawTradeCommand.DRAW_TRADING_AMOUNT));
        //Test if correct cards are added to trading area
        //TODO: fix Optional .get() calls to something neater?
        assertThat("First card", ((HandPile) player.getTrading()).getCardType(0).get(), is(testPile.pop().get().getCardType()));
        assertThat("Second card", ((HandPile) player.getTrading()).getCardType(1).get(), is(testPile.pop().get().getCardType()));
        //Test if correct game command result is returned
        assertThat("DRAWN_TO_TRADING game command result",result,is(StandardGameCommandResult.DRAWN_TO_TRADING));
    }

    /**
     * Test to see if a draw still happens even if a reshuffle is needed
     */
    @Test
    public void testReshuffledDraw() throws Exception {
        while(game.getGamePile().pileSize() >= StandardDrawTradeCommand.DRAW_TRADING_AMOUNT) {
            game.getGamePile().pop();
        }
        int gamePileSize = game.getGamePile().pileSize();
        int playerTradingSize = player.getTrading().pileSize();
        assertThat("Pile is small enough",gamePileSize,is(StandardDrawTradeCommand.DRAW_TRADING_AMOUNT-1));
        GameCommandResult result = drawTradeC.execute(player,game);
        assertThat("No gamePile size chance",game.getGamePile().pileSize(),is(gamePileSize));
        assertThat("No tradingPile size chance",player.getTrading().pileSize(),is(playerTradingSize));
        assertThat("RESHUFFLE game command result",result,is(StandardGameCommandResult.RESHUFFLE));
    }
}
