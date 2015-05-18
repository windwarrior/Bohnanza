package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.List;
import nl.utwente.bpsd.impl.DefaultGame;

public class DefaultPlantCommand extends DefaultGameCommand {
    int fieldIndex;
    Card card;

    /**
     * Planting bean card in selected player's field
     * @requires this.player != null this.fieldIndex != null && g != null && this.card != null
     */
    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultGame
        DefaultGame game = (DefaultGame) g; // Cast it because it is now indeed a DefaultGame
        DefaultPlayer player = (DefaultPlayer) p;

        // TODO: checking if this.fieldIndex is in range and this.player != null this.fieldIndex != null && g != null && this.card!= null
        List<Pile> fields = player.getAllFields();
        Pile field = fields.get(fieldIndex);

        /*
         * Player can only plant card on empty field or on
         * field with matching card types
         */
        if(!(field.pileSize() == 0) && field.peek().isPresent() && !field.peek().get().equals(card)) {
            return DefaultGameCommandResult.INVALID;
        }
        else {
            /* TODO: What about removing card from player's hand? Where it should be done?
             * Is it already removed here?
             * In case of error should it be put back in player's hand?
            */
            field.append(card);
            return DefaultGameCommandResult.PLANT;
        }
    }

    public void setFieldIndex(int index){
        this.fieldIndex = index;
    }

    public void setCard(Card card){
        this.card = card;
    }
    
}
