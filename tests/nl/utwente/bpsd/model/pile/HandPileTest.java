package nl.utwente.bpsd.model.pile;

import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class HandPileTest {

    HandPile pile;
    //Using Mockito so we don't have it instantiate all these cards and card types
    @Mock Card card1;
    @Mock Card card2;
    @Mock Card card3;
    @Mock CardType ct;


    @Before
    public void setUp() throws Exception {
        pile = new HandPile();
        card1 = Mockito.mock(Card.class);
        card2 = Mockito.mock(Card.class);
        card3 = Mockito.mock(Card.class);
        ct = Mockito.mock(CardType.class);
        when(card2.getCardType()).thenReturn(ct);
        pile.append(card1);
        pile.append(card2);
        pile.append(card3);
    }

    @Test
    public void testGetCard() throws Exception {
        //Correct behavior
        int pileSize = pile.pileSize();
        assertThat("Valid index", pile.getCard(2), is(Optional.of(card3)));
        assertThat("Size decrease by one", pile.pileSize(), is(pileSize - 1));
        //Bad index range
        pileSize = pile.pileSize();
        assertThat("Index to large", pile.getCard(10), is(Optional.empty()));
        assertThat("Size remains the same", pile.pileSize(), is(pileSize));
        assertThat("Index to small", pile.getCard(-1), is(Optional.empty()));
        assertThat("Size remains the same", pile.pileSize(), is(pileSize));
    }

    @Test
    public void testGetCardType() throws Exception {
        int pileSize = pile.pileSize();
        assertThat("Valid index", pile.getCardType(1), is(Optional.of(ct)));
        assertThat("Index to large", pile.getCardType(3), is(Optional.empty()));
        assertThat("Index to small", pile.getCardType(-1), is(Optional.empty()));
        assertThat("No size chance", pile.pileSize(), is(pileSize));
    }

    @Test
    public void testGetCardCopy() throws Exception {
        //Correct behavior
        int pileSize = pile.pileSize();
        assertThat("Valid index", pile.getCardCopy(2), is(Optional.of(card3)));
        assertThat("Size remains the same", pile.pileSize(), is(pileSize));
        //Bad index range
        pileSize = pile.pileSize();
        assertThat("Index to large", pile.getCardCopy(7), is(Optional.empty()));
        assertThat("Size remains the same", pile.pileSize(), is(pileSize));
        assertThat("Index to small", pile.getCardCopy(-1), is(Optional.empty()));
        assertThat("Size remains the same", pile.pileSize(), is(pileSize));
    }

    @Test
    public void removeAll() throws Exception {
        /*
         * adding 5 cards to pile
         * (chiliBean-1, chiliBean-2, cocoaBean-3, redBean-4, gardenBean-5)
         * and 3 cards to removeList (chiliBean-1, cocoaBean-3, redBean-7)
         */
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

        cardsToAdd.add(new Card(chiliBean, 1));
        cardsToAdd.add(new Card(chiliBean, 2));
        cardsToAdd.add(new Card(cocoaBean, 3));
        cardsToAdd.add(new Card(redBean, 4));
        cardsToAdd.add(new Card(gardenBean, 5));

        pile = new HandPile();
        for (Card c : cardsToAdd) {
            pile.append(c);
        }
        List<Card> removeList = new ArrayList<>();
        removeList.add(new Card(chiliBean, 1));
        removeList.add(new Card(cocoaBean, 3));
        removeList.add(new Card(redBean, 7));

        int oldPileSize = pile.pileSize();
        pile.removeAll(removeList);
        assertThat("Two cards should be removed", pile.pileSize(), is(oldPileSize - 2));
        //checking remaining cards
        assertThat("1st card checking", pile.getCardCopy(0).get(), CoreMatchers.is(equalTo(new Card(chiliBean, 2))));
        assertThat("2nd card checking", pile.getCardCopy(1).get(), CoreMatchers.is(equalTo(new Card(redBean, 4))));
        assertThat("3rd card checking", pile.getCardCopy(2).get(), CoreMatchers.is(equalTo(new Card(gardenBean, 5))));

        //removing empty list
        oldPileSize = pile.pileSize();
        removeList.clear();
        pile.removeAll(removeList);
        assertThat("No cards should be removed", pile.pileSize(), is(oldPileSize));

        //removing from empty pile
        pile = new HandPile();
        removeList.add(new Card(chiliBean, 1));
        pile.removeAll(removeList);
        assertThat("No cards should be removed", pile.pileSize(), is(0));
    }

}