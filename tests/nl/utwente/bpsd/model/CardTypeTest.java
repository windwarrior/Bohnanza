/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.model;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardTypeTest {

    private CardType cardType;

    public CardTypeTest() {
    }

    @Before
    public void setUp() {
        Map<Integer, Integer> beanOMeter = new HashMap<>();
        beanOMeter.put(4, 1);
        beanOMeter.put(6, 2);
        beanOMeter.put(8, 3);
        beanOMeter.put(10, 4);
        cardType = new CardType("Blue bean", beanOMeter, 1);
    }

    /**
     * Test of getTypeName method, of class CardType.
     */
    @Test
    public void testGetTypeName() {
        assertThat("The name of a bean is correctly returned", cardType.getTypeName(), is("Blue bean"));
    }

    /**
     * Test of getValueMap method, of class CardType.
     */
    @Test
    public void testGetBeanOMeter() {
        Map<Integer, Integer> beanOMeter = new HashMap<>();
        beanOMeter.put(4, 1);
        beanOMeter.put(6, 2);
        beanOMeter.put(10, 4);
        beanOMeter.put(8, 3);

        assertThat("The bean0Meter is correctly returned", cardType.getBeanOMeter(), is(beanOMeter));
    }

    /**
     * Test of getNumberOfType method, of class CardType.
     */
    @Test
    public void testGetNumberOfType() {
        assertThat("The number of cards of certain type is correctly returned", cardType.getNumberOfType(), is(1));
    }

}
