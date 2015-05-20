package nl.utwente.bpsd.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Jochem on 5/20/2015.
 */
public class DefaultPlayerTest {

    DefaultPlayer player;
    @Mock DefaultGame game;

    @Before
    public void setUp() throws Exception {
        player = new DefaultPlayer("Test Player");
        game = mock(DefaultGame.class);
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