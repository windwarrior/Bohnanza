/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameStatus;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bsd.model.command.Command;

import java.util.List;

/**
 *
 * @author lennart
 * @author Kasia
 */
public class DefaultPlantCommand implements Command {
    Player player;
    int fieldIndex;
    Card card;

    /**
     * Planting bean card in selected player's field
     * @requires this.player != null this.fieldIndex != null && g != null && this.card != null
     */
    @Override
    public GameStatus execute(Game g) {
        // TODO: checking if this.fieldIndex is in range and this.player != null this.fieldIndex != null && g != null && this.card!= null
        List<Pile> fields = player.getAllFields();
        Pile field = fields.get(fieldIndex);

        /*
         * Player can only plant card on empty field or on
         * field with matching card types
         */
        if(!(field.pileSize() == 0) && !field.peek().equals(card)) {
            //maybe other error handling?
            return GameStatus.GAME_PLANT_ERROR;
        }
        else {
            /* TODO: What about removing card from player's hand? Where it should be done?
             * Is it already removed here?
             * In case of error should it be put back in player's hand?
            */
            field.append(card);
            return GameStatus.GAME_PROGRESS;
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setFieldIndex(int index){
        this.fieldIndex = index;
    }

    public void setCard(Card card){
        this.card = card;
    }
    
}
