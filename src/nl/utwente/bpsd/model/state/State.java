package nl.utwente.bpsd.model.state;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import nl.utwente.bpsd.model.command.Command;

public class State {
    private final String name;
    private final List<Class<? extends Command>> allowedCommands;
    
    private Map<String, State> transitions;
    
    public State(String name, Class<? extends Command>... allowedCommands) {
        this.name = name;
        this.allowedCommands = Arrays.asList(allowedCommands);
        transitions = new HashMap<>();
    }
    
    /** 
     * Adds a transition to this state going to another state.
     * 
     * If a transition with the same label exists, it is overwritten.
     * @param label The label of this transition
     * @param newState The state this transition transfers to
     */
    public void addTransition(String label, State newState) {
        transitions.put(label, newState);
    }
    
    public void removeTransition(String label) {
        // The contract of remove states that it will remove something if it
        // is present in the map, so no check is needed.
        this.transitions.remove(label);
    }
    
    public Optional<State> getTransition(String label) {
        return Optional.ofNullable(transitions.get(label));
    }
    
    public boolean isAllowedCommand(Class<? extends Command> command) {
        return allowedCommands.contains(command);
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}
