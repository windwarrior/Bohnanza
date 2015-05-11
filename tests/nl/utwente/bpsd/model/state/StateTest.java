/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.model.state;

import java.util.Optional;
import nl.utwente.bsd.model.command.Command;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lennart
 */
public class StateTest {
    private State s1;
    private State s2;
    private State s3;
    
    public StateTest() {
    }
    
    @Before
    public void setUp() {
        s1 = new State("1");
        s2 = new State("2");
        s3 = new State("3");
        
        s1.addTransition("a", s2);
        s1.addTransition("b", s3);
        
        s2.addTransition("c", s3);
    }

    /**
     * Test of addTransition method, of class State.
     */
    @Test
    public void testAddTransition() {
    }

    /**
     * Test of getTransition method, of class State.
     */
    @Test
    public void testGetTransition() {
    }

    /**
     * Test of isAllowedCommand method, of class State.
     */
    @Test
    public void testIsAllowedCommand() {
    }
    
}
