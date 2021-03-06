package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaGameCommandResult;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.List;
import java.util.Optional;
import nl.utwente.bpsd.model.pile.HarvestablePile;

/** Current player plants first card from hand to one of his/her fields
 * in Phase 3) from Al Cabohne game rules
 */
public class MafiaPlantFromHandToFieldCommand extends MafiaGameCommand{
    int fieldIndex;
    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultGame
        MafiaPlayer player = (MafiaPlayer) p;

        if(player.getAllFields().size() <= fieldIndex || fieldIndex < 0)
            return MafiaGameCommandResult.INVALID;

        List<HarvestablePile> fields = player.getAllFields();
        Pile field = fields.get(fieldIndex);
        GameCommandResult result;

        Optional<CardType> cardType = player.getHand().peek();

        /*
         * Cards of the same type must be planted on the same field.
         * Player can only plant card on empty field or on
         * field with matching card types.
         */
        if((cardType.isPresent() && isOtherFieldWithCardType(cardType.get(), fields)) || (!(field.pileSize() == 0) && field.peek().isPresent() && !field.peek().equals(cardType)))
            return MafiaGameCommandResult.INVALID;


        result = player.getHand().pop().map((Card c) -> {
            field.append(c);
            return MafiaGameCommandResult.PLANT_HAND_FIELD;
        }).orElse(MafiaGameCommandResult.INVALID);

        return result;
    }

    public void setFieldIndex(int index){
        this.fieldIndex = index;
    }

    /**
     *@return true if player has other field with the same card types
     * otherwise returns false
     */
    private boolean isOtherFieldWithCardType(CardType ct, List<HarvestablePile> fields){
        for (int i=0; i<fields.size(); ++i) {
            if(i!=fieldIndex && fields.get(i).peek().isPresent() && fields.get(i).peek().get().equals(ct))
                return true;
        }
        return false;
    }
}
