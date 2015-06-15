package nl.utwente.bpsd.impl.standard.command.internal;

import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.command.StandardGameCommand;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.InternalCommand;
import nl.utwente.bpsd.model.Player;

/**
 *
 * @author lennart
 */
public class StandardNextPlayerCommand extends StandardGameCommand implements InternalCommand {
    
    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // Forces a check whether p and g are of type StandardPlayer/StandardGame
        StandardGame sg = (StandardGame) g;
        
        
        int currentPlayerIndex = sg.getPlayers().indexOf(sg.getCurrentPlayer());
        
        currentPlayerIndex = (currentPlayerIndex + 1) % sg.getPlayers().size();

        sg.setCurrentPlayer(sg.getPlayers().get(currentPlayerIndex));
        
        return StandardGameCommandResult.PROGRESS;
    }
}
