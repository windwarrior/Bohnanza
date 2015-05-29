package nl.utwente.bpsd.impl.standard;

import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class StandardPlayerTest {

    StandardPlayer player;
    @Mock StandardGame game;

    @Before
    public void setUp() throws Exception {
        player = new StandardPlayer("Test Player");
        game = mock(StandardGame.class);
    }

    @Test
    public void testDrawIntoHand() throws Exception {

    }

    @Test
    public void testDrawIntoTrading() throws Exception {

    }

    @Test
    public void testPlantFromTrading() throws Exception {

    }

    @Test
    public void testPlantFromHand() throws Exception {

    }

    @Test
    public void testTrade() throws Exception {

    }

    @Test
    public void testHarvest() throws Exception {

    }

    @Test
    public void testBuyField() throws Exception {

    }

    @Test
    public void testGetHand() throws Exception {

    }

    @Test
    public void testGetTreasury() throws Exception {

    }

    @Test
    public void testGetTrading() throws Exception {

    }

    @Test
    public void testGetAllFields() throws Exception {

    }

    @Test
    public void testGetAdditionalPileByName() throws Exception {

    }
}