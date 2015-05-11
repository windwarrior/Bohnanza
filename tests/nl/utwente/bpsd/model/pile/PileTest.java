package nl.utwente.bpsd.model.pile;

import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Jochem on 5/10/2015.
 * Note: a Pile is FIFO. Take for example a Pile representing a players hand.
 */
public class PileTest {

    Pile noCards;
    Pile testPile;

    @Before
    public void setUp() throws Exception {
        Card c1 = new Card(new CardType("TestCT",new HashMap<>(),1));
        Card c2 = new Card(new CardType("TestCT2",new HashMap<>(),2));
        List<Card> cards = new ArrayList<>();
        cards.add(c1);
        cards.add(c2);
        testPile = new Pile(cards);
        noCards = new Pile();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAppend() throws Exception {
        Card c3 = new Card(new CardType("TestCT",new HashMap<>(),1));
        testPile.append(c3);
        assertEquals("Added card to a pile with 2 cards", 3, testPile.pileSize());
        noCards.append(c3);
        assertEquals("Added card to a pile with no cards",1,noCards.pileSize());
    }

    @Test
    public void testPop() throws Exception {
        Card expected = new Card(new CardType("TestCT",new HashMap<>(),1));
        Card result = testPile.pop();
        assertEquals("Get correct card from pile with cards",expected,result);
        assertEquals("One cards less in pile after pop()",1,testPile.pileSize());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testPopEmptyPile() throws Exception {
        noCards.pop();
    }

    @Test
    public void testPeek() throws Exception {
        Card expected = new Card(new CardType("TestCT",new HashMap<>(),1));
        Card result = testPile.peek();
        assertEquals("See correct card from pile with cards",expected,result);
        assertEquals("No cards lost after peek()",2, testPile.pileSize());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testPeekEmptyPile() throws Exception {
        noCards.peek();
    }

    @Test
    public void testPileSize() throws Exception {
        assertEquals("Number of cards in pile with 2 cards",2,testPile.pileSize());
        assertEquals("Number of cards in pile with no cards",0,noCards.pileSize());
    }
}