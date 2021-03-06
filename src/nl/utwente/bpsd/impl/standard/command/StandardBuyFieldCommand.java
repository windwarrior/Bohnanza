package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.List;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.pile.HarvestablePile;

public class StandardBuyFieldCommand extends StandardGameCommand {

    /**
     * If the player has enough coins in the treasury and a free spot of a new field the player buys a new field.
     */
    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        StandardPlayer player = (StandardPlayer) p;

        GameCommandResult result = StandardGameCommandResult.INVALID;

        Pile treasury = player.getTreasury();
        List<HarvestablePile> fields = player.getAllFields();
        if(treasury.pileSize() >= StandardGame.FIELDCOST && fields.size() < StandardGame.NUMMAXFIELDS){
            for (int i = 0; i < StandardGame.FIELDCOST; i++) {
                treasury.pop();
            }
            HarvestablePile newField = new HarvestablePile(player.getTreasury(), g.getDiscardPile());
            fields.add(newField);
            result = StandardGameCommandResult.BOUGHT_FIELD;
        }
        return result;
    }
}
