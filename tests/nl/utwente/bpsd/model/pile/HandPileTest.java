package nl.utwente.bpsd.model.pile;

import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

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
}