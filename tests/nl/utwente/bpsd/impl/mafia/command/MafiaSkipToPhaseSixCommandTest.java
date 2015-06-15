package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaBoss;
import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaGameCommandResult;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.pile.Pile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MafiaSkipToPhaseSixCommandTest {
    MafiaPlayer player;
    Game game;
    List<MafiaBoss> bosses;
    MafiaSkipToPhaseSixCommand skipC;

    @Before
    public void setUp() throws Exception {
        player = new MafiaPlayer("TestPlayer");
        game = new MafiaGame();
        game.addPlayers(player);
        game.initialize();
        //delete player's cards added through initialization
        for (int i = 0; i < StandardGame.number_start_cards; ++i)
            player.getHand().pop();
        //delete Mafia's cards
        bosses = ((MafiaGame)game).getMafia();
        int bossPileSize;
        for (MafiaBoss boss : bosses) {
            bossPileSize = boss.getPile().pileSize();
            for (int j = 0; j < bossPileSize; ++j)
                boss.getPile().pop();
        }
        //player's hand with 2 redBeans
        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType redBean = new CardType("Black-eyed Bean", redBeanOMeter, 18);
        player.getHand().append(new Card(redBean, 1));
        player.getHand().append(new Card(redBean, 2));
        skipC = new MafiaSkipToPhaseSixCommand();
    }

    @Test
    public void testSkipToPhaseSixEmptyHand() throws Exception {
        generateMafiaBlueCards(1, 2);
        int handsize = player.getHand().pileSize();
        for(int i=0; i<handsize; ++i)
            player.getHand().pop();
        GameCommandResult result = skipC.execute(player,game);
        assertThat("Skip to phase six, empty hand: command result checking", result, is(MafiaGameCommandResult.SKIP_TO_PHASE_SIX));
    }

    @Test
    public void testSkipToPhaseSixNoEmptyMafiaFields() throws Exception {
        generateMafiaBlueCards(1, 1);
        generateMafiaBlueCards(0, 1);
        generateMafiaBlueCards(2, 1);
        GameCommandResult result = skipC.execute(player,game);
        assertThat("Skip to phase six, no empty mafia fields: command result checking", result, is(MafiaGameCommandResult.SKIP_TO_PHASE_SIX));
    }

    @Test
    public void testSkipToPhaseSixTheSameCardsAsMafia() throws Exception {
        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 18);
        player.getHand().append(new Card(blackeyedBean, 1));

        generateMafiaBlueCards(1, 1);
        generateMafiaRedCards(0, 2);
        generateMafiaBlueCards(2, 1);
        GameCommandResult result = skipC.execute(player,game);
        assertThat("Skip to phase six, player has the same cards as mafia: command result checking", result, is(MafiaGameCommandResult.SKIP_TO_PHASE_SIX));
    }

    @Test
    public void testSkipToPhaseSixRevealNotEmpty() throws Exception {
        generateMafiaRedCards(1, 1);
        ArrayList<Pile> reveals = ((MafiaGame)game).getRevealArray();
        Card card = Mockito.mock(Card.class);
        reveals.get(0).append(card);
        GameCommandResult result = skipC.execute(player,game);
        assertThat("Skip to phase six, reveal not empty: command result checking", result, is(MafiaGameCommandResult.INVALID));
    }

    @Test
    public void testSkipToPhaseSixNeedToGiveCard() throws Exception {
        generateMafiaBlueCards(1, 1);
        GameCommandResult result = skipC.execute(player,game);
        assertThat("Skip to phase six, player needs to give card to mafia: command result checking", result, is(MafiaGameCommandResult.INVALID));
    }

    /**
     * adds Black-eyed Bean cards to mafia boss's field
     *
     * @param index index of mafia boss in game list
     * @param num number of additional Black-eyed Beans to be placed in the boss's field
     */
    private void generateMafiaBlueCards(int index, int num) {

        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 18);
        for (int i = 0; i < num; ++i) {
            Card c = new Card(blackeyedBean, i);
            this.bosses.get(index).getPile().append(c);
        }
    }

    /**
     * adds Red Bean cards to mafia boss's field
     *
     * @param index index of mafia boss in game list
     * @param num number of additional Red Beans to be placed in the boss's field
     */
    private void generateMafiaRedCards(int index, int num) {

        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType redBean = new CardType("Black-eyed Bean", redBeanOMeter, 18);
        for (int i = 0; i < num; ++i) {
            Card c = new Card(redBean, i);
            this.bosses.get(index).getPile().append(c);
        }
    }
}