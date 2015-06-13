package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaGameCommandResult;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.pile.Pile;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MafiaPlantFromRevealCommandTest {
    MafiaPlayer player;
    Game game;
    MafiaPlantFromRevealCommand plantC;

    @Before
    public void setUp() throws Exception {
        player = new MafiaPlayer("TestPlayer");
        game = new MafiaGame();
        game.addPlayers(player);
        game.initialize();
        //reveal Pile with 2 blackeyedBeans
        ((MafiaGame)game).getRevealArray().add(new Pile());
        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 18);
        ((MafiaGame)game).getRevealArray().get(0).append(new Card(blackeyedBean, 1));
        ((MafiaGame)game).getRevealArray().get(0).append(new Card(blackeyedBean, 2));

        plantC = new MafiaPlantFromRevealCommand();
        plantC.setFieldIndex(0);
        plantC.setRevealIndex(0);
    }

    @Test
    public void testPlantFromRevealToFieldIncorrectFieldIndex() throws Exception {
        int oldRevealPileSize = ((MafiaGame)game).getRevealArray().get(0).pileSize();
        generateFieldCards(1, 2);
        plantC.setFieldIndex(2);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from reveal to field, incorrect field index: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from reveal to field, incorrect field index: 2nd field size checking", player.getAllFields().get(1).pileSize(), is(2));
        assertThat("Planting from reveal to field, incorrect field index: 1st field size checking", player.getAllFields().get(0).pileSize(), is(0));
        assertThat("Planting from reveal to field, incorrect field index: reveal pile size checking", ((MafiaGame)game).getRevealArray().get(0).pileSize(), is(oldRevealPileSize));
    }

    @Test
    public void testPlantFromRevealToFieldIncorrectRevealPileIndex() throws Exception {
        int oldRevealPileSize = ((MafiaGame)game).getRevealArray().get(0).pileSize();
        generateFieldCards(1, 2);
        plantC.setRevealIndex(1);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from reveal to field, incorrect reveal pile index: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from reveal to field, incorrect reveal pile index: 2nd field size checking", player.getAllFields().get(1).pileSize(), is(2));
        assertThat("Planting from reveal to field, incorrect reveal pile index: 1st field size checking", player.getAllFields().get(0).pileSize(), is(0));
        assertThat("Planting from reveal to field, incorrect reveal pile index: reveal pile size checking", ((MafiaGame)game).getRevealArray().get(0).pileSize(), is(oldRevealPileSize));
    }

    @Test
    public void testPlantFromRevealToFieldWrongCardType() throws Exception {
        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType redBean = new CardType("Red Bean", redBeanOMeter, 18);
        //add redBean to second Reveal Pile
        ((MafiaGame)game).getRevealArray().add(new Pile());
        ((MafiaGame)game).getRevealArray().get(1).append(new Card(redBean, 1));
        generateFieldCards(1, 3);

        plantC.setFieldIndex(1);
        plantC.setRevealIndex(1);
        int oldRevealPileSize = ((MafiaGame)game).getRevealArray().get(1).pileSize();
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from reveal to field with wrong cards' type: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from reveal to field with wrong cards' type: field size checking ", player.getAllFields().get(1).pileSize(), is(3));
        assertThat("Planting from reveal to field with wrong cards' type: reveal pile size checking", ((MafiaGame)game).getRevealArray().get(1).pileSize(), is(oldRevealPileSize));
    }

    @Test
    public void testPlantFromRevealToFieldNoCardInRevealPile() throws Exception {
        ((MafiaGame)game).getRevealArray().add(new Pile());
        generateFieldCards(1, 3);
        plantC.setFieldIndex(1);
        plantC.setRevealIndex(1);
        int oldRevealPileSize = ((MafiaGame)game).getRevealArray().get(1).pileSize();
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from reveal to field, empty hand: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from reveal to field, empty hand: field size checking ", player.getAllFields().get(1).pileSize(), is(3));
        assertThat("Planting from reveal to field, empty hand: reveal pile size checking", ((MafiaGame)game).getRevealArray().get(1).pileSize(), is(oldRevealPileSize));
    }

    @Test
    public void testPlantFromRevealToFieldIncorrectField() throws Exception {
        generateFieldCards(1, 3);

        int oldRevealPileSize = ((MafiaGame)game).getRevealArray().get(0).pileSize();
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from reveal to field, other field has already this card type: command result checking", result, is(MafiaGameCommandResult.INVALID));
        assertThat("Planting from reveal to field, other field has already this card type: field size checking", player.getAllFields().get(1).pileSize(), is(3));
        assertThat("Planting from reveal to field, other field has already this card type: field size checking", player.getAllFields().get(0).pileSize(), is(0));
        assertThat("Planting from reveal to field, other field has already this card type: reveal pile size checking", ((MafiaGame)game).getRevealArray().get(0).pileSize(), is(oldRevealPileSize));
    }

    @Test
    public void testPlantFromRevealToFieldNotEmpty() throws Exception {
        int oldRevealPileSize = ((MafiaGame)game).getRevealArray().get(0).pileSize();
        generateFieldCards(1, 2);
        plantC.setFieldIndex(1);
        GameCommandResult result = plantC.execute(player,game);
        assertThat("Planting from reveal on non empty field: command result checking", result, is(MafiaGameCommandResult.PLANT_REVEAL_FIELD));
        assertThat("Planting from reveal on not empty field: field size checking", player.getAllFields().get(1).pileSize(), is(3));
        assertThat("Planting from reveal on not empty field: reveal pile size checking", ((MafiaGame)game).getRevealArray().get(0).pileSize(), is(oldRevealPileSize - 1));
    }

    @Test
    public void testPlantFromRevealToFieldOnEmpty() throws Exception {
        int oldRevealPileSize = ((MafiaGame)game).getRevealArray().get(0).pileSize();
        GameCommandResult status = plantC.execute(player,game);
        assertThat("Planting from reveal on empty field: command result checking", status, is(MafiaGameCommandResult.PLANT_REVEAL_FIELD));
        assertThat("Planting from reveal on empty field: field size checking ", player.getAllFields().get(0).pileSize(), is(1));
        assertThat("Planting from reveal on empty field: reveal pile size checking", ((MafiaGame)game).getRevealArray().get(0).pileSize(), is(oldRevealPileSize - 1));
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