package nl.utwente.bpsd.model;

import java.util.Optional;
import nl.utwente.bpsd.model.pile.Pile;

public abstract class Player {
    // subclasses need to access this
    protected Game game;
    
    public void setGame(Game game) {
        this.game = game;
    }
    
    protected boolean executeCommand(Command c) {
        return game.executeCommand(this, c);
    }
    
    public abstract Optional<Pile> getPileByName(String name);

}
