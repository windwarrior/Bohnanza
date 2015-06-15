package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaGameCommandResult;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.List;
import java.util.Optional;

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

        List<Pile> fields = player.getAllFields();
        Pile field = fields.get(fieldIndex);
        GameCommandResult result;

        Optional<CardType> cardType = player.getHand().peek();

        /*
         * Cards of the same type must be planted on the same field.
         * Player can only plant card on empty field or on
         * field with matching card types.
         */
        if(cardType.isPresent() && isOtherFieldWithCardType(cardType.get(), fields))
            return MafiaGameCommandResult.INVALID;
        if(!(field.pileSize() == 0) && field.peek().isPresent() && !field.peek().equals(cardType))
            return MafiaGameCommandResult.INVALID;


        result = player.getHand().pop().map((Card c) -> {
            field.append(c);
            return MafiaGameCommandResult.PLANT_HAND_FIELD;
        }).orElse(MafiaGameCommandResult.INVALID);
//
//        //check if player has any card in hand
//        result = cardType.map((CardType ct) -> {
//
//            if(isOtherFieldWithCardType(ct, fields) || (!(field.pileSize() == 0) && field.peek().isPresent() && !field.peek().get().equals(ct)))
//                return MafiaGameCommandResult.INVALID;
//            field.append(player.getHand().pop().get());
//            return MafiaGameCommandResult.PLANT_HAND_FIELD;
//        }).orElse(MafiaGameCommandResult.INVALID);

        return result;
    }

    public void setFieldIndex(int index){
        this.fieldIndex = index;
    }

    /**
     *@return true if player has other field with the same card types
     * otherwise returns false
     */
    private boolean isOtherFieldWithCardType(CardType ct, List<Pile> fields){
        for (int i=0; i<fields.size(); ++i) {
            if(i!=fieldIndex && fields.get(i).peek().isPresent() && fields.get(i).peek().get().equals(ct))
                return true;
        }
        return false;
    }
}
