package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.List;
import nl.utwente.bpsd.model.GameCommandResult;

//TODO: Add this to the state machine.
public class DefaultBuyFieldCommand extends DefaultGameCommand {

    public static final int FIELDCOST = 3;
    public static final int NUMMAXFIELDS = 3;

    /**
     * If the player has enough coins in the treasury and a free spot of a new field the player buys a new field.
     */
    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        DefaultPlayer player = (DefaultPlayer) p;

        GameCommandResult result = DefaultGameCommandResult.INVALID;

        Pile treasury = player.getTreasury();
        List<Pile> fields = player.getAllFields();
        if(treasury.pileSize() >= FIELDCOST && fields.size() < NUMMAXFIELDS){
            for (int i = 0; i < FIELDCOST; i++) {
                treasury.pop();
            }
            Pile newField = new Pile();
            fields.add(newField);
            result = DefaultGameCommandResult.BOUGHT_FIELD;
        }
        return result;
    }
}
