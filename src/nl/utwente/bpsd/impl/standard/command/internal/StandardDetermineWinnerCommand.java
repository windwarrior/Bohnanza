package nl.utwente.bpsd.impl.standard.command.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.impl.standard.command.StandardGameCommand;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.InternalCommand;
import nl.utwente.bpsd.model.Player;

/**
 *
 * @author lennart
 */
public class StandardDetermineWinnerCommand extends StandardGameCommand implements InternalCommand {

    @Override
    public GameCommandResult execute(Player currentPlayer, Game g) {
        super.execute(currentPlayer,g); // Forces a check whether p and g are of type StandardPlayer/StandardGame
        StandardGame sg = (StandardGame) g;
        /*
            If the game ends some things happen
        
            1. Harvest and sell all fields
            2. Count the gold coins
                
            In case of a tie, the player with the most cards wins
                
        */
        
        for (Player p : sg.getPlayers()) {
            StandardPlayer sp = (StandardPlayer) p;
            
            int amountOfFields = sp.getAllFields().size();
            
            for (int i = 0; i < amountOfFields; i++) {
                // This action may fail
                // But because actions happen atomically, fails should not be
                // devastating for the resulting state.
                
                // Furthermore, sp.harverst will try to move the game into a 
                // state that is probably not allowed, the statemachine will
                // try to advance, but fails to do so and will return false.
                // As we are not interested in whether this works we just ignore
                // the result of this call altogheter, we just want to harvest 
                // all fields that can be harvested.
                sp.harvest(i);
            }
        }
        
        // Make a shallow copy of the players of the game so that we can order 
        // them on result.
        List<Player> finalOutcome = new ArrayList<>(sg.getPlayers());
        
        Collections.sort(finalOutcome, (Player p1, Player p2) -> {
            int result = Integer.compare(p1.getTreasury().pileSize(), p2.getTreasury().pileSize());
            
            // It is possible that players have the same score, the rules dicate
            // that the final result is then determined by the hand size
            return result == 0 ? Integer.compare(p1.getHand().pileSize(), p2.getHand().pileSize()) : result;
        });
        
        // Now we set the winner        
        sg.setWinners(finalOutcome);
        
        return StandardGameCommandResult.PROGRESS;        
    }
    
}
