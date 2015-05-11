package nl.utwente.bpsd.model.state;

import java.util.Optional;
import nl.utwente.bpsd.model.command.Command;

public class StateManager {
    private State currentState;
    
    public StateManager() {
        this.currentState = null;
    }
    
    public void addInitialState(State s) {
        this.currentState = s;
    }
    
    public Optional<State> doTransition(String label) {
        Optional<State> result = currentState.getTransition(label);
        
        result.ifPresent((State x) -> this.currentState = x);
        
        return result;
    }
    
    public boolean isTransition(String label) {
        return currentState.getTransition(label).isPresent();
    }
    
    public boolean isAllowedCommand(Class<? extends Command> command) {
        return currentState.isAllowedCommand(command);
    }
}
