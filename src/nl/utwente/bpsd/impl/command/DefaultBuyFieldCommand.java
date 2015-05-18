package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.Command;

import java.util.List;
import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.GameCommandResult;

//TODO: Add this to the state machine.
public class DefaultBuyFieldCommand extends DefaultGameCommand {

    public static final int FIELDCOST = 3;
    public static final int NUMMAXFIELDS = 3;

    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        DefaultGame game = (DefaultGame) g; // Cast it because it is now indeed a DefaultGame
        DefaultPlayer player = (DefaultPlayer) p;
        Pile treasury = player.getTreasury();
        List<Pile> fields = player.getAllFields();
        if(treasury.pileSize() >= FIELDCOST && fields.size() < NUMMAXFIELDS){
            for (int i = 0; i < FIELDCOST; i++) {
                treasury.pop();
            }
            Pile newField = new Pile();
            fields.add(newField);
        }
        return DefaultGameCommandResult.BOUGHT_FIELD;
    }
}
