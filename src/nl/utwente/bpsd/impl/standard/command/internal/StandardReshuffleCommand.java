package nl.utwente.bpsd.impl.standard.command.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.command.StandardGameCommand;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.InternalCommand;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.Pile;

/**
 *
 * @author lennart
 */
public class StandardReshuffleCommand extends StandardGameCommand implements InternalCommand {

    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // Forces a check whether p and g are of type StandardPlayer/StandardGame
        StandardGame sg = (StandardGame) g;
        
        int counter = sg.getReshuffleCounter(); // The amount of times the deck was reshuffled
        
        GameCommandResult result = StandardGameCommandResult.FINISHED;
        if (!(counter >= sg.getMaxReshuffleCount())) {
            List<Card> toShuffle = new ArrayList<>();
            
            // TODO: Also empty the GamePile (not sure if this has to happen?)
            Pile[] pileList = {sg.getDiscardPile(), sg.getGamePile()};
            
            for (Pile pile : pileList) {
                boolean iterate = true;
                
                while(iterate) {
                    pile.pop().map(x -> toShuffle.add(x)).orElse(iterate = false);                
                }
            }
                       
            Collections.shuffle(toShuffle);
            
            toShuffle.stream().forEach(x -> sg.getGamePile().append(x));
                        
            result = StandardGameCommandResult.RESHUFFLED;
        }
        
        return result;       
    }
}
