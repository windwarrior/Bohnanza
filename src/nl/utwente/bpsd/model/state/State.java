package nl.utwente.bpsd.model.state;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import nl.utwente.bsd.model.command.Command;

public class State {
    private final String name;
    private final List<Class<? extends Command>> allowedCommands;
    
    private Map<String, State> transitions;
    
    public State(String name, Class<? extends Command>... allowedCommands) {
        this.name = name;
        this.allowedCommands = Arrays.asList(allowedCommands);
    }
    
    public void addTransition(String label, State newState) {
        transitions.put(label, newState);
    }
    
    public Optional<State> getTransition(String label) {
        return Optional.ofNullable(transitions.get(label));
    }
    
    public boolean isAllowedCommand(Class<? extends Command> command) {
        return allowedCommands.contains(command);
    }
    
}
