package nl.utwente.bpsd.model.state;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    
    public boolean isInAcceptingState() {
        return this.currentState.isAcceptingState();
    }
    
    public void reset() {
        this.currentState = this.initialState;
    }
    
    public Set<K> alphabet() {
        return this.initialState.alphabet(new ArrayList<>());
    }

    public State getCurrentState(){
        return currentState;
    }
    /*
    Maybe in the future: 
    
    public StateManager compose(StateManager other) {
        // compute the crossproduct of both statemachines
        List<State> crossProd = new ArrayList<>();
        
        // reachable of an initial state should be all states
        // Sets because state instances should be unique
        Set<State> statesThis = this.initialState.reachable(new HashSet<>());
        Set<State> statesOther = other.initialState.reachable(new HashSet<>());
        
        Set<K> alphabetThis = this.alphabet();
        Set<K> alphabetOther = other.alphabet();
        
        State combinedInitial = null;
        
        for(State s1 : statesThis) {
            for(State s2: statesOther) {
                State combinedState = new State(s1.toString() + " " + s1.toString());
                if(s1.equals(this.initialState) && s2.equals(other.initialState)) {
                    combinedInitial = combinedState;
                }
                crossProd.add(combinedState);
            }
        }
    
    }
    */
}
