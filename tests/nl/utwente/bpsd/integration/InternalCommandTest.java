/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.integration;

import java.util.List;
import java.util.Optional;
import nl.utwente.bpsd.model.Command;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.state.State;
import nl.utwente.bpsd.model.state.StateManager;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;

/**
 * This is a bit of an integration test that tests whether the state machine 
 * advances correctly based on command output, and also whether internal 
 * commands are executed correctly
 * @author Lennart
 */
public class InternalCommandTest {
    private Game game;
    
    @Mock
    private Command mockedExternalCommand;
    
    @Mock
    private Player p;
    
    @Before
    public void setUp() {        
        // Now we need some commands, we mock the interface
        // The external command returns ExternalProgress
        mockedExternalCommand = Mockito.mock(Command.class);
        Mockito.when(mockedExternalCommand.execute(any(), any())).thenReturn(TestCommandResult.ExternalProgress);        
        
        // Also mock a player
        p = Mockito.mock(Player.class);
    }
    
    @Test
    public void testSimpleStateMachine() {
        // First we test a simple two state machine, one external one internal
        State<GameCommandResult, Class<? extends Command>>  s1 = new State<>("Accept External", mockedExternalCommand.getClass());
        State<GameCommandResult, Class<? extends Command>>  s2 = new State<>("Accept Internal", TestInternalCommand.class);
        
        // The external command results in a "ExternalProgress"
        s1.addTransition(TestCommandResult.ExternalProgress, s2);        
        // The internal command results in "InternalProgress"
        s2.addTransition(TestCommandResult.InternalProgress, s1);
        
        // now we mock the getStateManager call from the game to result in our
        // own statemanager
        StateManager<GameCommandResult, Class<? extends Command>> manager = new StateManager<>(s1);
        
        // now we create an anonymous instance of Game
        game = createGame(p, manager);
        
        
        // Okay now for the actual testing
        // First check that the current state is indeed s1.
        assertThat(manager.getCurrentState(), is(s1));
        
        // now we initiate action by calling the external command.
        game.executeCommand(p, mockedExternalCommand);
        
        // After calling an external command, we should see calls to both 
        // the execute of mockedExternalCommand and mockedInternalCommand
        verify(mockedExternalCommand).execute(any(), any());
        // and also assert that we are actually back in s1
        assertThat(manager.getCurrentState(), is(s1));       
    }
    
    @Test
    public void testInternalSled() {
        // This method will test that internals are accepted as long as they
        // are possible
        State<GameCommandResult, Class<? extends Command>>  s1 = new State<>("Accept External", mockedExternalCommand.getClass());
        State<GameCommandResult, Class<? extends Command>>  s2 = new State<>("Accept Internal", TestInternalCommand.class);
        State<GameCommandResult, Class<? extends Command>>  s3 = new State<>("Accept Internal Again", TestInternalCommand.class);

        
        // The external command results in a "ExternalProgress"
        s1.addTransition(TestCommandResult.ExternalProgress, s2);        
        // The internal command results in "InternalProgress"
        s2.addTransition(TestCommandResult.InternalProgress, s3);
        s2.addTransition(TestCommandResult.InternalProgress, s1);
        
        // now we mock the getStateManager call from the game to result in our
        // own statemanager
        StateManager<GameCommandResult, Class<? extends Command>> manager = new StateManager<>(s1);
        
        // now we create an anonymous instance of Game
        game = createGame(p, manager);
        game.executeCommand(p, mockedExternalCommand);
        
        // After calling an external command, we should see calls to both 
        // the execute of mockedExternalCommand and mockedInternalCommand
        verify(mockedExternalCommand).execute(any(), any());
        // and also assert that we are actually back in s1
        assertThat(manager.getCurrentState(), is(s1));       
    }
    
    @Test
    public void testInternalSledWithExternalAllowed() {
        // This method will test that internals are accepted as long as they
        // are possible
        // State s2 allows an external command as well, but our implementation should greedily slide through it.
        State<GameCommandResult, Class<? extends Command>>  s1 = new State<>("Accept External", mockedExternalCommand.getClass());
        State<GameCommandResult, Class<? extends Command>>  s2 = new State<>("Accept Internal but also external", mockedExternalCommand.getClass(), TestInternalCommand.class);
        State<GameCommandResult, Class<? extends Command>>  s3 = new State<>("Accept Internal Again", TestInternalCommand.class);

        
        // The external command results in a "ExternalProgress"
        s1.addTransition(TestCommandResult.ExternalProgress, s2);        
        // The internal command results in "InternalProgress"
        s2.addTransition(TestCommandResult.InternalProgress, s3);
        s2.addTransition(TestCommandResult.InternalProgress, s1);
        
        // now we mock the getStateManager call from the game to result in our
        // own statemanager
        StateManager<GameCommandResult, Class<? extends Command>> manager = new StateManager<>(s1);
        
        // now we create an anonymous instance of Game
        game = createGame(p, manager);
        game.executeCommand(p, mockedExternalCommand);
        
        // After calling an external command, we should see calls to
        // the external command, we cannot check it for the internal one 
        // because it is (due to technical problems) not mocked.
        verify(mockedExternalCommand).execute(any(), any());
        // However, after this we should be back in s1
        assertThat(manager.getCurrentState(), is(s1));       
    }
    
    
    private Game createGame(Player p, StateManager sm) {
        // we cannot mock here
        // because we will be using a field which broke when we mocked it.
        // we only override methods that are of interest of us
        // all other are marked by "not interested"
        return new Game(){
            @Override public void initialize() {}                                               // not interested, not used anyway
            @Override public void addPlayers(Player... players) {}                              // not interested, not used anyway
            @Override public Optional<Pile> getPileByName(String name) { return null; }         // not interested, not used anyway
            @Override public void setWinners(List<Player> ps) {}                                // not interested, not used anyway

            @Override
            public Player getCurrentPlayer() {                                                  // used by executeCommand, so interested in this
                return p;
            }

            @Override
            public StateManager<GameCommandResult, Class<? extends Command>> getStateManager() {// used by executeCommand, so interested in this
                return sm;
            }
        };
    }
}
