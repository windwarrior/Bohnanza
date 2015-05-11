package nl.utwente.bpsd.model.state;

import java.util.Optional;
import nl.utwente.bpsd.model.command.Command;

public class StateManager<K, C> {
    private State currentState;
    private final State initialState;
    
    public StateManager(State initial) {
        this.initialState = initial;
        this.currentState = initial;
    }
    
    public void addInitialState(State s) {
        this.currentState = s;
    }
    
    public Optional<State> doTransition(K label) {
        Optional<State> result = currentState.getTransition(label);
        
        result.ifPresent((State x) -> this.currentState = x);
        
        return result;
    }
    
    public boolean isTransition(K label) {
        return currentState.getTransition(label).isPresent();
    }
    
    public boolean isAllowedClass(Class<? extends C> klass) {
        return currentState.isAllowedClass(klass);
    }
    
    public void reset() {
        this.currentState = this.initialState;
    }
}
