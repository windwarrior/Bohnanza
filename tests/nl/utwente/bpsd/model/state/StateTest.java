/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.model.state;

import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StateTest {
    private State s1;
    private State s2;
    private State s3;
    private State s4;
    
    public StateTest() {
    }
    
    @Before
    public void setUp() {
        /*
            S1 ---a--> S2
             |      /
             |     /
             b    c
             |   /
             V  /
             S3V---c-->S4
        */
        s1 = new State("1");
        s2 = new State("2");
        s3 = new State("3");
        s4 = new State("4");
        
        // Transitions from S1
        s1.addTransition("a", s2);
        s1.addTransition("b", s3);
        // Transitions from S2
        s2.addTransition("c", s3);
        // Transitions from S3
        s3.addTransition("c", s4);
    }

    /**
     * Test of addTransition method, of class State.
     */
    @Test
    public void testAddTransition() {
        // First assert that the transition is not yet there
        assertThat(s2.getTransition("b"), is(Optional.empty()));
        
        // Then add the transition and expect it to be there
        // Adds a transition between s2 and s4
        s2.addTransition("b", s4);
        assertThat(s2.getTransition("b"), is(Optional.of(s4)));
        
        // Add a selfloop at S1
        s1.addTransition("c", s1);
        assertThat(s1.getTransition("c"), is(Optional.of(s1)));
        
        // Overwrite the transition between s2 and s4 for one to s3.
        // Test that the old transition is no longer there and the new one is.
        // Required by the contract of addTransition
        s2.addTransition("b", s3);
        assertThat(s2.getTransition("b"), not(Optional.of(s4)));
        assertThat(s2.getTransition("b"), is(Optional.of(s3)));
    }
    
    @Test
    public void testRemoveTransition() {
        // First assert that the transition is still there
        assertThat(s1.getTransition("a"), is(Optional.of(s2)));
        // Then remove that transition
        s1.removeTransition("a");
        assertThat(s1.getTransition("a"), is(Optional.empty()));
        // Also check that that transition can be added back (also a source of bugs)
        s1.addTransition("a", s2);
        assertThat(s1.getTransition("a"), is(Optional.of(s2)));
        // And finally test that a transition that doesnt exist can be removed.
        // Should not throw errors or make the internal state inconsistent.
        assertThat(s1.getTransition("c"), is(Optional.empty()));
        s1.removeTransition("c");
        assertThat(s1.getTransition("c"), is(Optional.empty()));
    }

    /**
     * Test of getTransition method, of class State.
     */
    @Test
    public void testGetTransition() {
        // Test all transition
        assertThat(s1.getTransition("a"), is(Optional.of(s2)));
        assertThat(s1.getTransition("b"), is(Optional.of(s3)));
        assertThat(s2.getTransition("c"), is(Optional.of(s3)));
        assertThat(s3.getTransition("c"), is(Optional.of(s4)));
        
        // Test all labels that don't have a transition in a certain state
        assertThat(s1.getTransition("c"), is(Optional.empty()));
        
        assertThat(s2.getTransition("a"), is(Optional.empty()));
        assertThat(s2.getTransition("b"), is(Optional.empty()));
        
        assertThat(s3.getTransition("a"), is(Optional.empty()));
        assertThat(s3.getTransition("b"), is(Optional.empty()));
        
        assertThat(s4.getTransition("a"), is(Optional.empty()));
        assertThat(s4.getTransition("b"), is(Optional.empty()));
        assertThat(s4.getTransition("c"), is(Optional.empty()));
        
        // Test a label that is not part of the alphabet of this statemachine
        assertThat(s1.getTransition("NaN"), is(Optional.empty()));
        assertThat(s2.getTransition("NaN"), is(Optional.empty()));
        assertThat(s3.getTransition("NaN"), is(Optional.empty()));
        assertThat(s4.getTransition("NaN"), is(Optional.empty()));
    }

    /**
     * Test of isAllowedCommand method, of class State.
     */
    @Test
    public void testIsAllowedCommand() {
    }
    
}
