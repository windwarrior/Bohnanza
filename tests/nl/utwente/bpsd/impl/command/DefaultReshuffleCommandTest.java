package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.standard.command.StandardReshuffleCommand;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.pile.DiscardPile;
import nl.utwente.bpsd.model.pile.Pile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class DefaultReshuffleCommandTest {

    @Mock StandardGame game;
    StandardPlayer player;
    StandardReshuffleCommand reshuffleC;
    Pile gamePile;
    Pile discardPile;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        player = new StandardPlayer("TestPlayer1");
        reshuffleC = new StandardReshuffleCommand();
        gamePile = new Pile();
        discardPile = new DiscardPile();
        generateGamePile();
        generateDiscardPile();
    }

    @Test
    public void testInvalidGameReshuffleCounter() throws Exception {
        when(game.getReshuffleCounter()).thenReturn(3);
        GameCommandResult result = reshuffleC.execute(player,game);
        assertThat("Trying to reshuffle deck for the 4rd time: result", result, is(StandardGameCommandResult.FINISHED));
    }

    @Test
    public void testTooValidGameReshuffleCounter() throws Exception {
        when(game.getReshuffleCounter()).thenReturn(2);
        when(game.getGamePile()).thenReturn(gamePile);
        when(game.getDiscardPile()).thenReturn(discardPile);
        int oldGamePileSize = gamePile.pileSize();
        int oldDiscardPileSize = discardPile.pileSize();
        GameCommandResult result = reshuffleC.execute(player,game);
        assertThat("Trying to reshuffle deck for the 3rd time: result", result, is(StandardGameCommandResult.RESHUFFLED));
        assertThat("Size of gamePile after reshuffling", gamePile.pileSize(), is(oldGamePileSize+oldDiscardPileSize));
        assertThat("Size of discardPile after reshuffling", discardPile.pileSize(), is(0));
    }

    //ads one ChiliBean card to this.gamePile
    private void generateGamePile() {
        Map<Integer, Integer> chiliBeanOMeter = new HashMap<>();
        chiliBeanOMeter.put(3, 1);
        chiliBeanOMeter.put(6, 2);
        chiliBeanOMeter.put(8, 3);
        chiliBeanOMeter.put(9, 4);
        CardType chiliBean = new CardType("Chili Bean", chiliBeanOMeter, 18);
        Card c = new Card(chiliBean);
        this.gamePile.append(c);
    }

    /* adds 10 cards to this.discardPile
     * (chiliBean, chiliBean, cocoaBean, redBean, gardenBean, blackeyedBean, gardenBean, greenBean, soyBean, stinkBean)
     */
    private void generateDiscardPile() {
        List<Card> cardsToAdd = new ArrayList<>();
        Map<Integer, Integer> chiliBeanOMeter = new HashMap<>();
        chiliBeanOMeter.put(3, 1);
        chiliBeanOMeter.put(6, 2);
        chiliBeanOMeter.put(8, 3);
        chiliBeanOMeter.put(9, 4);
        CardType chiliBean = new CardType("Chili Bean", chiliBeanOMeter, 18);

        Map<Integer, Integer> cocoaBeanOMeter = new HashMap<>();
        cocoaBeanOMeter.put(2, 2);
        cocoaBeanOMeter.put(3, 3);
        cocoaBeanOMeter.put(4, 4);
        CardType cocoaBean = new CardType("Cocoa Bean", cocoaBeanOMeter, 4);

        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType redBean = new CardType("Red Bean", redBeanOMeter, 8);

        Map<Integer, Integer> gardenBeanOMeter = new HashMap<>();
        gardenBeanOMeter.put(2, 2);
        gardenBeanOMeter.put(3, 3);
        CardType gardenBean = new CardType("Garden Bean", gardenBeanOMeter, 6);

        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 10);

        Map<Integer, Integer> greenBeanOMeter = new HashMap<>();
        greenBeanOMeter.put(3, 1);
        greenBeanOMeter.put(5, 2);
        greenBeanOMeter.put(6, 3);
        greenBeanOMeter.put(7, 4);
        CardType greenBean = new CardType("Green Bean", greenBeanOMeter, 14);

        Map<Integer, Integer> soyBeanOMeter = new HashMap<>();
        soyBeanOMeter.put(2, 1);
        soyBeanOMeter.put(4, 2);
        soyBeanOMeter.put(6, 3);
        soyBeanOMeter.put(7, 4);
        CardType soyBean = new CardType("Soy Bean", soyBeanOMeter, 12);

        Map<Integer, Integer> stinkBeanOMeter = new HashMap<>();
        stinkBeanOMeter.put(3, 1);
        stinkBeanOMeter.put(5, 2);
        stinkBeanOMeter.put(7, 3);
        stinkBeanOMeter.put(8, 4);
        CardType stinkBean = new CardType("Stink Bean", stinkBeanOMeter, 16);

        cardsToAdd.add(new Card(chiliBean));
        cardsToAdd.add(new Card(chiliBean));
        cardsToAdd.add(new Card(cocoaBean));
        cardsToAdd.add(new Card(redBean));
        cardsToAdd.add(new Card(gardenBean));
        cardsToAdd.add(new Card(blackeyedBean));
        cardsToAdd.add(new Card(gardenBean));
        cardsToAdd.add(new Card(greenBean));
        cardsToAdd.add(new Card(soyBean));
        cardsToAdd.add(new Card(stinkBean));

        for (Card c : cardsToAdd) {
            discardPile.append(c);
        }
    }

}