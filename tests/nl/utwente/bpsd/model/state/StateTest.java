/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.model.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.Command;
import nl.utwente.bpsd.model.Player;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StateTest {
    private State<String, Class<? extends Command>> s1;
    private State<String, Class<? extends Command>> s2;
    private State<String, Class<? extends Command>> s3;
    private State<String, Class<? extends Command>> s4;
    
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
        s1 = new State<>("1", CCommand.class);
        s2 = new State<>("2", BCommand.class);
        s3 = new State<>("3", ACommand.class, BCommand.class);
        s4 = new State<>("4", ACommand.class, BCommand.class, CCommand.class);
        
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
    public void testIsAllowedClass() {
        // Everything in state 1
        assertThat(s1.isAllowed(CCommand.class), is(true));

        assertThat(s1.isAllowed(ACommand.class), is(false));
        assertThat(s1.isAllowed(BCommand.class), is(false));
        
        // Everything in state 2
        assertThat(s2.isAllowed(BCommand.class), is(true));

        assertThat(s2.isAllowed(ACommand.class), is(false));
        assertThat(s2.isAllowed(CCommand.class), is(false));
        
        // Everything in state 3
        assertThat(s3.isAllowed(ACommand.class), is(true));
        assertThat(s3.isAllowed(BCommand.class), is(true));
        
        assertThat(s3.isAllowed(CCommand.class), is(false));
        
        // Everything in state 4
        assertThat(s4.isAllowed(ACommand.class), is(true));
        assertThat(s4.isAllowed(BCommand.class), is(true));
        assertThat(s4.isAllowed(CCommand.class), is(true));
    }
    
    @Test
    public void testAlphabet() {
        assertThat(s1.alphabet(new ArrayList<>()), is(new HashSet<>(Arrays.asList("a", "b", "c"))));
        
        State<String, Command> sFirst = new State("S1");
        State<String, Command> sSecond = new State("S2");
        
        sFirst.addTransition("a", sSecond);
        sSecond.addTransition("b", sFirst);
        
        assertThat(sFirst.alphabet(new ArrayList<>()), is(new HashSet<>(Arrays.asList("a", "b"))));
    }
    
    @Test
    public void testReachable() {
        // TODO: this also tested on size, but that somehow broke *hamcrest*
        // the result is correct tho.
        assertThat(s1.reachable(new HashSet<>()), containsInAnyOrder(s1, s2, s3, s4));
    }
    
    class ACommand implements Command {
        @Override
        public StandardGameCommandResult execute(Player p,Game g) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }        
    }
    
    class BCommand implements Command {
        @Override
        public StandardGameCommandResult execute(Player p,Game g) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }        
    }
    
    class CCommand implements Command {
        @Override
        public StandardGameCommandResult execute(Player p,Game g) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }        
    }
    
    
    
}
