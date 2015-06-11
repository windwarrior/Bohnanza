package nl.utwente.bpsd.model.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

public class State<K, C> {
    private final String name;
    private final List<Class<? extends C>> allowedClasses;
    private boolean isAcceptingState;
    
    private Map<K, State> transitions;
    
    public State(String name, Class<? extends C>... allowedClasses) {
        this(name, false, allowedClasses);
    }
    
    public State(String name, boolean isAcceptingState, Class<? extends C>... allowedClasses) {
        this.name = name;
        this.isAcceptingState = isAcceptingState;
        this.allowedClasses = Arrays.asList(allowedClasses);
        transitions = new HashMap<>();
    }
    
    public List<Class<? extends C>> getAllowedClasses() {
        return this.allowedClasses;
    }
    
    /** 
     * Adds a transition to this state going to another state.
     * 
     * If a transition with the same label exists, it is overwritten.
     * @param label The label of this transition
     * @param newState The state this transition transfers to
     */
    public void addTransition(K label, State newState) {
        transitions.put(label, newState);
    }
    
    public void removeTransition(K label) {
        // The contract of `remove` states that it will remove something if it
        // is present in the map, so no check is needed.
        this.transitions.remove(label);
    }
    
    public Optional<State> getTransition(K label) {
        return Optional.ofNullable(transitions.get(label));
    }
    
    public boolean isAllowedClass(Class<? extends C> command) {
        return allowedClasses.contains(command);
    }
    
    public boolean isAcceptingState() {
        return this.isAcceptingState;
    }

    public Set<K> alphabet(List<Entry<K, State>> visitedTransitions) {
        Set<K> alphabet = new HashSet<>();
        
        for(Entry<K, State> transition: transitions.entrySet()) {
            if(!visitedTransitions.contains(transition)) {
                // add letter to the alphabet
                alphabet.add(transition.getKey());
                // add this transition to the visted map
                visitedTransitions.add(transition);
                
                // call recursively on the end of this transition
                Set<K> otherAlphabet = transition.getValue().alphabet(visitedTransitions);
                
                // add all discovered letters to the alphabet
                alphabet.addAll(otherAlphabet);             
            }
        }
        
        return alphabet;
    }
    
    public Set<State> reachable(Set<State> visited) {
        Set<State> reachable = new HashSet<>(Arrays.asList(this));
        
        visited.add(this);
        
        for(Entry<K, State> transition: transitions.entrySet()) {
            if(!visited.contains(transition.getValue())) {
                Set<State> otherReachable = transition.getValue().reachable(visited);
                
                reachable.addAll(otherReachable);
            }
        }
        
        return reachable;
    }

    @Override
    public String toString() {
        return "State{" +
                "name='" + name +
                "'}";
    }
}
