package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaGameCommandResult;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MafiaPlantFromHandToFieldCommandTest {
    MafiaPlayer player;
    Game game;
    MafiaPlantFromHandToFieldCommand plantC;

    @Before
    public void setUp() throws Exception {
        player = new MafiaPlayer("TestPlayer");
        game = new MafiaGame();
        game.addPlayers(player);
        game.initialize();
        //delete player's cards added through initialization
        for (int i = 0; i < StandardGame.number_start_cards; ++i)
            player.getHand().pop();
        //player's hand with 2 blackeyedBeans
        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 18);
        player.getHand().append(new Card(blackeyedBean, 1));
        player.getHand().append(new Card(blackeyedBean, 2));

        plantC = new MafiaPlantFromHandToFieldCommand();
        plantC.setFieldIndex(0);
    }

    @Test
    public void testPlantFromHandToFieldIncorrectFieldIndex() throws Exception {
        int oldHandSize = player.getHand().pileSize();
        generateFieldCards(1, 2);
        plantC.setFieldIndex(2);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from hand to field, incorrect field index: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from hand to field, incorrect field index: 2nd field size checking", player.getAllFields().get(1).pileSize(), is(2));
        assertThat("Planting from hand to field, incorrect field index: 1st field size checking", player.getAllFields().get(0).pileSize(), is(0));
        assertThat("Planting from hand to field, incorrect field index: hand size checking", player.getHand().pileSize(), is(oldHandSize));
    }

    @Test
    public void testPlantFromHandToFieldWrongCardType() throws Exception {
        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType redBean = new CardType("Red Bean", redBeanOMeter, 18);
        //remove cards from player's hand
        int size = player.getHand().pileSize();
        for(int i=0; i<size; ++i)
            player.getHand().pop();
        //add redBean to player's hand
        player.getHand().append(new Card(redBean, 6));
        generateFieldCards(1, 3);

        plantC.setFieldIndex(1);
        int oldHandSize = player.getHand().pileSize();
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from hand to field with wrong cards' type: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from hand to field with wrong cards' type: field size checking ", player.getAllFields().get(1).pileSize(), is(3));
        assertThat("Planting from hand to field with wrong cards' type: hand size checking", player.getHand().pileSize(), is(oldHandSize));
    }

    @Test
    public void testPlantFromHandToFieldNoCardInHand() throws Exception {
        //remove cards from player's hand
        int size = player.getHand().pileSize();
        for(int i=0; i<size; ++i)
            player.getHand().pop();
        generateFieldCards(1, 3);
        plantC.setFieldIndex(1);
        int oldHandSize = player.getHand().pileSize();
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from hand to field, empty hand: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from hand to field, empty hand: field size checking ", player.getAllFields().get(1).pileSize(), is(3));
        assertThat("Planting from hand to field, empty hand: hand size checking", player.getHand().pileSize(), is(oldHandSize));
    }

    @Test
    public void testPlantFromHandToFieldIncorrectField() throws Exception {
        generateFieldCards(1, 3);

        plantC.setFieldIndex(0);
        int oldHandSize = player.getHand().pileSize();
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from hand to field, other field has already this card type: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from hand to field, other field has already this card type: field size checking", player.getAllFields().get(1).pileSize(), is(3));
        assertThat("Planting from hand to field, other field has already this card type: field size checking", player.getAllFields().get(0).pileSize(), is(0));
        assertThat("Planting from hand to field, other field has already this card type: hand size checking", player.getHand().pileSize(), is(oldHandSize));
    }

    @Test
    public void testPlantFromHandToFieldNotEmpty() throws Exception {
        int oldHandSize = player.getHand().pileSize();
        generateFieldCards(1, 2);
        plantC.setFieldIndex(1);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from hand on non empty field: command result checking", result, is(MafiaGameCommandResult.PLANT_HAND_FIELD));
        assertThat("Planting from hand on not empty field: field size checking", player.getAllFields().get(1).pileSize(), is(3));
        assertThat("Planting from hand on not empty field: hand size checking", player.getHand().pileSize(), is(oldHandSize - 1));
    }

    @Test
    public void testPlantFromHandToFieldOnEmpty() throws Exception {
        int oldHandSize = player.getHand().pileSize();
        GameCommandResult status = plantC.execute(player,game);
        assertThat("Planting from hand on empty field: command result checking", status, is(MafiaGameCommandResult.PLANT_HAND_FIELD));
        assertThat("Planting from hand on empty field: field size checking ", player.getAllFields().get(0).pileSize(), is(1));
        assertThat("Planting from hand on empty field: hand size checking", player.getHand().pileSize(), is(oldHandSize - 1));
    }

    /**
     * adds Black-eyed Bean cards to player's field
     *
     * @param index index of the field to be filled with cards
     * @param num number of additional Black-eyed Beans to be placed in the field
     */
    private void generateFieldCards(int index, int num) {

        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 18);
        for (int i = 0; i < num; ++i) {
            Card c = new Card(blackeyedBean, i);
            this.player.getAllFields().get(index).append(c);
        }
    }

}