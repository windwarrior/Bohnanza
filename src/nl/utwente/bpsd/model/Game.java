package nl.utwente.bpsd.model;

import java.util.List;
import java.util.Observable;
import java.util.Optional;
import nl.utwente.bpsd.model.pile.Pile;

public abstract class Game extends Observable {

    /**
     * Give players this game
     * Set up card pile
     */
    public abstract void initialize();
    
    public abstract void addPlayers(Player... players);

    // Only allowed from within trused code
    public abstract boolean executeCommand(Player p, Command c);

    public abstract Player getCurrentPlayer();
    
    public abstract Optional<Pile> getPileByName(String name);
    
    public abstract void gameEnd();
}
