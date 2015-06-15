package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.List;
import java.util.TreeMap;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.model.pile.HarvestablePile;

public class StandardHarvestCommand extends StandardGameCommand {

    int fieldIndex;

    /**
     * Sells beans from the player's selected bean field indicated by fieldIndex
     *
     * @requires this.player != null this.fieldIndex != null && g != null;
     */
    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        StandardGame game = (StandardGame) g; // Cast it because it is now indeed a StandardGame
        StandardPlayer player = (StandardPlayer) p;

        if(player.getAllFields().size() <= fieldIndex || fieldIndex < 0)
            return StandardGameCommandResult.INVALID;
        List<HarvestablePile> fields = player.getAllFields();
        HarvestablePile field = fields.get(fieldIndex);

        /*
         * Player can sell from any bean field where are at least 2 cards
         * Field with single card can be sell only when all his bean fields have just one card
         */
        boolean singleCard = true;
        if (field.pileSize() == 1) {
            for (int i = 0; i < fields.size() && singleCard; ++i) {
                singleCard = false;
                if (fields.get(i).pileSize() == 1) {
                    singleCard = true;
                }
            }
        }
        
        return (singleCard && field.harvest()) ? StandardGameCommandResult.HARVEST : StandardGameCommandResult.INVALID;

    }

    public void setFieldIndex(int index) {
        this.fieldIndex = index;
    }

}
