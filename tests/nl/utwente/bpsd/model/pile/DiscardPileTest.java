package nl.utwente.bpsd.model.pile;

import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DiscardPileTest{

    DiscardPile testPile;

    @Before
    public void setUp() throws Exception {
        testPile = new DiscardPile();
        Card c;
        for(int i=1; i<11; ++i) {
            c = new Card(new CardType(Integer.toString(i), new HashMap<>(), i), i);
            testPile.append(c);
        }
    }

    @Test
    public void testShuffleRemove() throws Exception {
        int testPileSize = testPile.pileSize();
        int shuffledListSize = testPile.shuffleRemove().size();
        assertThat("Size of the returned shuffled list", shuffledListSize, is(testPileSize));
        assertThat("DiscardPile should be empty after operation.", testPile.pileSize(), is(0));
    }
}