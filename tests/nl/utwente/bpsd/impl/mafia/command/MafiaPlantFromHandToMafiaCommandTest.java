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
import nl.utwente.bpsd.model.pile.HandPile;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MafiaPlantFromHandToMafiaCommandTest {
    MafiaPlayer player;
    Game game;
    List<MafiaBoss> bosses;
    MafiaPlantFromHandToMafiaCommand plantC;

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
        int pileSize = bosses.get(0).getPile().pileSize();
        for (int i = 0; i < pileSize; ++i)
            bosses.get(0).getPile().pop();
        bosses.get(1).getPile().pop();
        //player's hand: (blackeyedBean, blackeyedBean and redBean)
        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 18);
        CardType redBean = new CardType("Red Bean", redBeanOMeter, 18);
        player.getHand().append(new Card(blackeyedBean, 1));
        player.getHand().append(new Card(blackeyedBean, 2));
        player.getHand().append(new Card(redBean, 6));

        plantC = new MafiaPlantFromHandToMafiaCommand();
        plantC.setFieldIndex(0);
        plantC.setHandIndex(0);
    }

    @Test
    public void testPlantFromHandToMafiaIncorrectMafiaFieldIndex() throws Exception {
        int oldHandSize = player.getHand().pileSize();
        generateMafiaCards(1, 2);
        plantC.setFieldIndex(2);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from hand to mafia, incorrect field index: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from hand to mafia, incorrect field index: 2nd mafia size checking", bosses.get(1).getPile().pileSize(), is(2));
        assertThat("Planting from hand to mafia, incorrect field index: 1st mafia field size checking", bosses.get(0).getPile().pileSize(), is(0));
        assertThat("Planting from hand to mafia, incorrect field index: hand size checking", player.getHand().pileSize(), is(oldHandSize));
    }

    @Test
    public void testPlantFromHandToMafiaIncorrectHandIndex() throws Exception {
        int oldHandSize = player.getHand().pileSize();
        generateMafiaCards(1, 2);
        plantC.setHandIndex(2);
        plantC.setFieldIndex(1);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from hand to mafia, incorrect hand index: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from hand to mafia, incorrect hand index: 2nd mafia size checking", bosses.get(1).getPile().pileSize(), is(2));
        assertThat("Planting from hand to mafia, incorrect hand index: 1st mafia field size checking", bosses.get(0).getPile().pileSize(), is(0));
        assertThat("Planting from hand to mafia, incorrect hand index: hand size checking", player.getHand().pileSize(), is(oldHandSize));
    }

    @Test
    public void testPlantFromHandToMafiaWrongCardType() throws Exception {
        generateMafiaCards(1, 3);

        plantC.setHandIndex(2);
        plantC.setFieldIndex(1);
        int oldHandSize = player.getHand().pileSize();
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from hand to mafia with wrong cards' type: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from hand to mafia with wrong cards' type: mafia field size checking ", bosses.get(1).getPile().pileSize(), is(3));
        assertThat("Planting from hand to mafia with wrong cards' type: hand size checking", player.getHand().pileSize(), is(oldHandSize));
    }

    @Test
    public void testPlantFromHandToMafiaNoCardInHand() throws Exception {
        //remove cards from player's hand
        int size = player.getHand().pileSize();
        for(int i=0; i<size; ++i)
            player.getHand().pop();
        generateMafiaCards(1, 3);
        plantC.setFieldIndex(1);
        int oldHandSize = player.getHand().pileSize();
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from hand to mafia, empty hand: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from hand to mafia, empty hand: field size checking ", bosses.get(1).getPile().pileSize(), is(3));
        assertThat("Planting from hand to mafia, empty hand: hand size checking", player.getHand().pileSize(), is(oldHandSize));
    }

    @Test
    public void testPlantFromHandToMafiaIncorrectField() throws Exception {
        generateMafiaCards(1, 3);

        plantC.setFieldIndex(0);
        plantC.setHandIndex(1);
        int oldHandSize = player.getHand().pileSize();
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from hand to mafia, other mafia has already this card type: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from hand to mafia, other mafia has already this card type: field size checking", bosses.get(1).getPile().pileSize(), is(3));
        assertThat("Planting from hand to mafia, other mafia has already this card type: field size checking", bosses.get(0).getPile().pileSize(), is(0));
        assertThat("Planting from hand to mafia, other mafia has already this card type: hand size checking", player.getHand().pileSize(), is(oldHandSize));
    }

    @Test
         public void testPlantFromHandToMafiaNotEmpty() throws Exception {
        int oldHandSize = player.getHand().pileSize();
        generateMafiaCards(1, 2);
        plantC.setFieldIndex(1);
        int oldFieldSize = bosses.get(1).getPile().pileSize();
        Card oldCard1 = ((HandPile)player.getHand()).getCardCopy(1).get();
        Card oldCard2 = ((HandPile)player.getHand()).getCardCopy(2).get();
        GameCommandResult result = plantC.execute(player,game);

        Optional<Card> card1 = ((HandPile)player.getHand()).getCardCopy(0);
        Optional<Card> card2 = ((HandPile)player.getHand()).getCardCopy(1);
        assertThat("Planting from hand on non empty mafia's field: command result checking", result, is(MafiaGameCommandResult.PLANT_HAND_MAFIA));
        assertThat("Planting from hand on not empty mafia's field: field size checking", bosses.get(1).getPile().pileSize(), is(oldFieldSize+1));
        assertThat("Planting from hand on not empty mafia's field: hand size checking", player.getHand().pileSize(), is(oldHandSize - 1));
        assertThat("Planting from hand on not empty mafia's field: hand 1st card checking",card1.get() , is(equalTo(oldCard1)));
        assertThat("Planting from hand on not empty mafia's field: hand 2nd card checking",card2.get() , is(equalTo(oldCard2)));
    }

    @Test
    public void testPlantFromHandToMafiaOnEmpty() throws Exception {
        int oldHandSize = player.getHand().pileSize();
        plantC.setHandIndex(2);
        int oldFieldSize = bosses.get(0).getPile().pileSize();
        Card oldCard1 = ((HandPile)player.getHand()).getCardCopy(0).get();
        Card oldCard2 = ((HandPile)player.getHand()).getCardCopy(1).get();
        GameCommandResult result = plantC.execute(player,game);

        Optional<Card> card1 = ((HandPile)player.getHand()).getCardCopy(0);
        Optional<Card> card2 = ((HandPile)player.getHand()).getCardCopy(1);
        assertThat("Planting from hand on non empty mafia's field: command result checking", result, is(MafiaGameCommandResult.PLANT_HAND_MAFIA));
        assertThat("Planting from hand on not empty mafia's field: field size checking", bosses.get(0).getPile().pileSize(), is(oldFieldSize+1));
        assertThat("Planting from hand on not empty mafia's field: hand size checking", player.getHand().pileSize(), is(oldHandSize - 1));
        assertThat("Planting from hand on not empty mafia's field: hand 1st card checking",card1.get() , is(equalTo(oldCard1)));
        assertThat("Planting from hand on not empty mafia's field: hand 2nd card checking",card2.get() , is(equalTo(oldCard2)));
    }

    /**
     * adds Black-eyed Bean cards to mafia boss's field
     *
     * @param index index of mafia boss in game list
     * @param num number of additional Black-eyed Beans to be placed in the boss's field
     */
    private void generateMafiaCards(int index, int num) {

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
}