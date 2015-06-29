package nl.utwente.bpsd.model;

import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.Stack;
import nl.utwente.bpsd.exceptions.ImproperlyConfiguredException;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.state.StateManager;

public abstract class Game extends Observable {
    private Stack<Command> suspended = new Stack<>();

    /**
     * Give players this game
     * Set up card pile
     */
    public abstract void initialize();
    
    public abstract void addPlayers(Player... players);

    // Only allowed from within trusted code
    public boolean executeCommand(Player p, Command klass) {
        boolean result = false;

        // Yes comparison by reference, should be the same instance as well!
        if (this.getCurrentPlayer() == p && this.getStateManager().isAllowed(klass.getClass())) {
            GameCommandResult commandOutput = klass.execute(p,this);

            result = this.getStateManager().isTransition(commandOutput);

            if (result) {
                this.getStateManager().doTransition(commandOutput);
                
                // we are now in the next state, we assume internal actions to
                // have priority so will try something internal.
                // this will recurse as long as there is something internal to
                // do, so make sure that internal actions dont loop.
                this.tryInternal(this.getStateManager().getCurrentState().getAllowed());                
            }
        }

        return result;
    }
    
    private boolean tryInternal(List<Class<? extends Command>> allowedClasses) {
        for (Class<? extends Command> thing : allowedClasses) {
            if (InternalCommand.class.isAssignableFrom(thing)) {
                try {
                    // we can do an internal command
                    this.executeCommand(this.getCurrentPlayer(), thing.newInstance());
                } catch (InstantiationException | IllegalAccessException ex) {
                            // This exception should never happen, only goes wrong if thing requires parameters or is private or something
                    // This exception shall be treated as a runtime exception from now on
                    ex.printStackTrace();
                    throw new ImproperlyConfiguredException("Something was not configured right! Could not create instance of " + thing + " because it threw " + ex);
                }
                return true; // no need to search any further, we found our internal command
            }
        }
        return false;
    }

    public void suspend(Command c) {
        this.suspended.push(c);
    }

    public abstract Player getCurrentPlayer();
    
    public abstract StateManager<GameCommandResult, Class<? extends Command>> getStateManager();
    
    public abstract Optional<Pile> getPileByName(String name);
    
    public abstract void setWinners(List<Player> ps);

    public Pile getDiscardPile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
