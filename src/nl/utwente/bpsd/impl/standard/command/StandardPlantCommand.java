package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.List;
import java.util.Optional;

import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.model.pile.HarvestablePile;

public class StandardPlantCommand extends StandardGameCommand {
    int fieldIndex;
    int tradingIndex = -1;
    Card card;

    /**
     * Planting bean card in selected player's field
     * @requires this.player != null this.fieldIndex != null && g != null && this.card != null
     */
    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultGame
        StandardPlayer player = (StandardPlayer) p;

        if(player.getAllFields().size() <= fieldIndex || fieldIndex < 0)
            return StandardGameCommandResult.INVALID;
        List<HarvestablePile> fields = player.getAllFields();
        Pile field = fields.get(fieldIndex);

        /*
         * Check if planting from player's hand or from trading pile +
         * Get CardType of planted card
         */
        // TODO: correct use of optionals
        Optional<CardType> ct = tradingIndex == -1 ? player.getHand().peek() : ((HandPile) player.getTrading()).getCardType(tradingIndex);

        /*
         * Player can only plant card on empty field or on
         * field with matching card types
         */
        if(!ct.isPresent() || (ct.isPresent() && !(field.pileSize() == 0) && field.peek().isPresent() && !field.peek().get().equals(ct.get()))) {
            return StandardGameCommandResult.INVALID;
        }
        else {
            GameCommandResult result;
            if(tradingIndex == -1){
                 result = player.getHand().pop().map((Card c) -> {
                    field.append(c);
                    return StandardGameCommandResult.PLANT;
                }).orElse(StandardGameCommandResult.INVALID);
            } else {
                result = ((HandPile)player.getTrading()).getCard(tradingIndex).map((Card c) -> {
                    field.append(c);
                    return StandardGameCommandResult.PLANT_TRADED;
                }).orElse(StandardGameCommandResult.INVALID);
            }
            return result;
        }
    }

    public void setFieldIndex(int index){
        this.fieldIndex = index;
    }

    public void setCardIndex(int index){
        this.tradingIndex = index;
    }
}
