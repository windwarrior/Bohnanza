package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.standard.command.StandardDrawHandCommand;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.pile.HandPile;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultDrawHandCommandTest {

    StandardPlayer player;
    StandardGame game;
    StandardDrawHandCommand drawHandC;
    int expectedGamePileSize;
    int expectedPlayerHandPileSize;

    @Before
    public void setUp() throws Exception {
        player = new StandardPlayer("TestPlayer");
        game = new StandardGame();
        game.addPlayers(player);
        game.initialize();
        drawHandC = new StandardDrawHandCommand();
    }


    /**
     * Three cards removed from game gamePile Three cards added to player hand
     */
    @Test
    public void testStandardExecute() throws Exception {
        Pile testPile = new Pile(game.getGamePile());
        this.setSizes();
        GameCommandResult result = drawHandC.execute(player, game);
        //TODO: Fix these Optional .get() calls?
        assertThat("Game pile size decreased by three", game.getGamePile().pileSize(), is(expectedGamePileSize - StandardDrawHandCommand.DRAW_HAND_AMOUNT));
        assertThat("Hand pile size increased by three", player.getHand().pileSize(), is(expectedPlayerHandPileSize + StandardDrawHandCommand.DRAW_HAND_AMOUNT));

        //Test if last three hand pile cards have types of the top of the game pile (in order)
        assertThat("first added card", ((HandPile) player.getHand()).getCardType(player.getHand().pileSize() - 3).get(), is((testPile.pop().get().getCardType())));
        assertThat("second added card", ((HandPile) player.getHand()).getCardType(player.getHand().pileSize() - 2).get(), is((testPile.pop().get().getCardType())));
        assertThat("third added card", ((HandPile) player.getHand()).getCardType(player.getHand().pileSize() - 1).get(), is((testPile.pop().get().getCardType())));

        //Test if the right game command result is returned
        assertThat("DRAWN_TO_HAND game command result",result, is(StandardGameCommandResult.DRAWN_TO_HAND));
    }

    /**
     * Test case where the deck should be reshuffled
     * Test 2,1,0 cards in gamePile respectively (exhaustive gamePile.pileSize below threshold test)
     */
    @Test
    public void testEdgeCaseExecute() throws Exception {
        while(game.getGamePile().pileSize() >= StandardDrawHandCommand.DRAW_HAND_AMOUNT) {
            game.getGamePile().pop();
        }
        this.setSizes();
        GameCommandResult result = drawHandC.execute(player,game);
        assertThat("Game pile size stayed the same", game.getGamePile().pileSize(), is(expectedGamePileSize));
        assertThat("Player pile size stayed the same", player.getHand().pileSize(), is(expectedPlayerHandPileSize));
        assertThat("RESHUFFLE game command result", result, is(StandardGameCommandResult.RESHUFFLE));

        game.getGamePile().pop();
        this.setSizes();
        result = drawHandC.execute(player,game);
        assertThat("Game pile size stayed the same", game.getGamePile().pileSize(),is(expectedGamePileSize));
        assertThat("Player pile size stayed the same", player.getHand().pileSize(), is(expectedPlayerHandPileSize));
        assertThat("RESHUFFLE game command result", result, is(StandardGameCommandResult.RESHUFFLE));

        game.getGamePile().pop();
        this.setSizes();
        result = drawHandC.execute(player,game);
        assertThat("Game pile size stayed the same", game.getGamePile().pileSize(),is(expectedGamePileSize));
        assertThat("Player pile size stayed the same",player.getHand().pileSize(),is(expectedPlayerHandPileSize));
        assertThat("RESHUFFLE game command result",result,is(StandardGameCommandResult.RESHUFFLE));
    }

    private void setSizes(){
        expectedGamePileSize = game.getGamePile().pileSize();
        expectedPlayerHandPileSize = player.getHand().pileSize();
    }
}
