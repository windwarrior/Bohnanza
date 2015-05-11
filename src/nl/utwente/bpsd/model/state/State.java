package nl.utwente.bpsd.model.state;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class State<K, C> {
    private final String name;
    private final List<Class<? extends C>> allowedClasses;
    
    private Map<K, State> transitions;
    
    public State(String name, Class<? extends C>... allowedClasses) {
        this.name = name;
        this.allowedClasses = Arrays.asList(allowedClasses);
        transitions = new HashMap<>();
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
        // The contract of remove states that it will remove something if it
        // is present in the map, so no check is needed.
        this.transitions.remove(label);
    }
    
    public Optional<State> getTransition(K label) {
        return Optional.ofNullable(transitions.get(label));
    }
    
    public boolean isAllowedClass(Class<? extends C> command) {
        return allowedClasses.contains(command);
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}
