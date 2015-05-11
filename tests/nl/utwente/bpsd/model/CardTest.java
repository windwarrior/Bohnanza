package nl.utwente.bpsd.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import static org.junit.Assert.*;

/**
 * Created by Jochem on 5/10/2015.
 */
public class CardTest {

    Card testCard;

    @Before
    public void setUp() throws Exception {
        Map<Integer, Integer> beanOMeter = new HashMap<>();
        beanOMeter.put(4, 1);
        beanOMeter.put(6, 2);
        beanOMeter.put(8, 3);
        beanOMeter.put(10, 4);
        CardType cardType = new CardType("Blue Bean", beanOMeter, 20);
        testCard = new Card(cardType);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetCardType() throws Exception {
        Map<Integer, Integer> beanOMeterBlueBean = new HashMap<>();
        Map<Integer, Integer> beanOMeterWaxBean = new HashMap<>();
        beanOMeterBlueBean.put(4, 1);
        beanOMeterBlueBean.put(6, 2);
        beanOMeterBlueBean.put(8, 3);
        beanOMeterBlueBean.put(10, 4);
        CardType blueBean = new CardType("Blue Bean", beanOMeterBlueBean, 20);
        beanOMeterWaxBean.put(4, 1);
        beanOMeterWaxBean.put(7, 2);
        beanOMeterWaxBean.put(9, 3);
        beanOMeterWaxBean.put(11, 4);
        CardType waxBean = new CardType("Wax Bean", beanOMeterWaxBean, 22);
        assertThat("Right CardType", testCard.getCardType(), is(blueBean));
        assertThat("Wrong CardType", testCard.getCardType(), not(waxBean));

        CardType wrongNameBean = new CardType("Wax Bean", beanOMeterBlueBean, 20);
        CardType wrongBeanOMeterBean = new CardType("Blue Bean", beanOMeterWaxBean, 20);
        CardType wrongNumberBean = new CardType("Blue Bean", beanOMeterBlueBean, 0);
        assertThat("Wrong name", testCard.getCardType(), not(wrongNameBean));
        assertThat("Wrong beanOMeter", testCard.getCardType(), not(wrongBeanOMeterBean));
        assertThat("Wrong numberOfCardType", testCard.getCardType(), not(wrongNumberBean));
    }

}
