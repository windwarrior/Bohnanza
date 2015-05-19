package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.exceptions.ImproperlyConfiguredException;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;

/**
 * Created by Jochem Elsinga on 5/19/2015.
 */
public class DefaultReshuffleCommand extends DefaultGameCommand{

    //TODO: Implement this class
    //TODO: Write a test for this class
    @Override
    public GameCommandResult execute(Player p, Game g){
        //NOTE: remember the functionality of discard pile (i.e. shuffleRemove())
        throw new UnsupportedOperationException("Not yet supported");
    }
}
