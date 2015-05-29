package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.List;
import nl.utwente.bpsd.impl.standard.StandardGame;

public class StandardPlantCommand extends StandardGameCommand {
    int fieldIndex;
    Card card;

    /**
     * Planting bean card in selected player's field
     * @requires this.player != null this.fieldIndex != null && g != null && this.card != null
     */
    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultGame
        StandardGame game = (StandardGame) g; // Cast it because it is now indeed a StandardGame
        StandardPlayer player = (StandardPlayer) p;

        // TODO: checking if this.fieldIndex is in range and this.player != null this.fieldIndex != null && g != null && this.card!= null
        List<Pile> fields = player.getAllFields();
        Pile field = fields.get(fieldIndex);

        /*
         * Player can only plant card on empty field or on
         * field with matching card types
         */
        if(!(field.pileSize() == 0) && field.peek().isPresent() && !field.peek().get().equals(card)) {
            return StandardGameCommandResult.INVALID;
        }
        else {
            /* TODO: What about removing card from player's hand? Where it should be done?
             * Is it already removed here?
             * In case of error should it be put back in player's hand?
            */
            field.append(card);
            return StandardGameCommandResult.PLANT;
        }
    }

    public void setFieldIndex(int index){
        this.fieldIndex = index;
    }

    public void setCard(Card card){
        this.card = card;
    }
    
}
