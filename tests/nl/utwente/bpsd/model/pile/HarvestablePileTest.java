package nl.utwente.bpsd.model.pile;

import java.util.HashMap;
import java.util.Map;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lennart
 */
public class HarvestablePileTest {
    private HarvestablePile p0;
    private HarvestablePile p1;    
    private HarvestablePile p2;    
    private HarvestablePile p3;
    
    private Pile t0;
    private Pile d0;
    
    private Pile t1;
    private Pile d1;
    
    private Pile t2;
    private Pile d2;
    
    private Pile t3;
    private Pile d3;

    
    @Before
    public void setUp() {
        Map<Integer, Integer> beanMap = new HashMap<>();
        beanMap.put(3,1);
        beanMap.put(6,2);
        beanMap.put(8,3);
        beanMap.put(9,4);
        CardType chili = new CardType("Chili bean", beanMap, 100);
        
        // Zeroth harvest pile has 2 cards
        t0 = new Pile();
        d0 = new Pile();
        p0 = new HarvestablePile(t0, d0);
        for (int i = 0; i < 2; i++) {
            p0.append(new Card(chili, i));
        }
        
        // First harvest pile has 3 cards
        t1 = new Pile();
        d1 = new Pile();
        p1 = new HarvestablePile(t1, d1);
        for (int i = 0; i < 3; i++) {
            p1.append(new Card(chili, i));
        }
        
        // Second harvest pile has 10 cards (one more then max)
        t2 = new Pile();
        d2 = new Pile();
        p2 = new HarvestablePile(t2, d2);
        for (int i = 0; i < 10; i++) {
            p2.append(new Card(chili, i));
        }
        
        // Third pile has 7 cards (in between two variables
        t3 = new Pile();
        d3 = new Pile();
        p3 = new HarvestablePile(t3, d3);
        for (int i = 0; i < 7; i++) {
            p3.append(new Card(chili, i));
        }        
    }

    /**
     * Test of harvest method, of class HarvestablePile.
     */
    @Test
    public void testHarvest() {
        p0.harvest();
        assertThat(t0.pileSize(), is(0));
        assertThat(d0.pileSize(), is(2));
        
        p1.harvest();
        assertThat(t1.pileSize(), is(1));
        assertThat(d1.pileSize(), is(2));
        
        p2.harvest();
        assertThat(t2.pileSize(), is(4));
        assertThat(d2.pileSize(), is(6));
        
        p3.harvest();
        assertThat(t3.pileSize(), is(2));
        assertThat(d3.pileSize(), is(5));
    }

    /**
     * Test of isWorth method, of class HarvestablePile.
     */
    @Test
    public void testIsWorth() {        
        //assertThat(p0.isWorth(), is(0));
        //assertThat(p1.isWorth(), is(1));
        //assertThat(p2.isWorth(), is(4));
        //assertThat(p3.isWorth(), is(2));
    }
    
}
