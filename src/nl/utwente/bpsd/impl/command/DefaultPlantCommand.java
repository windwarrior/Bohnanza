/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.Command;

import java.util.List;
import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.GameCommandResult;

public class DefaultPlantCommand extends DefaultGameCommand {
    DefaultPlayer player;
    int fieldIndex;
    Card card;

    /**
     * Planting bean card in selected player's field
     * @requires this.player != null this.fieldIndex != null && g != null && this.card != null
     */
    @Override
    public GameCommandResult execute(Game game) {
        super.execute(game); // force a check that this is indeed a defaultgame
        DefaultGame dg = (DefaultGame) game; // Cast it because it is now indeed a DefaultGame
        
        // TODO: checking if this.fieldIndex is in range and this.player != null this.fieldIndex != null && g != null && this.card!= null
        List<Pile> fields = player.getAllFields();
        Pile field = fields.get(fieldIndex);

        /*
         * Player can only plant card on empty field or on
         * field with matching card types
         */
        if(!(field.pileSize() == 0) && !field.peek().equals(card)) {
            //maybe other error handling?
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

    public void setPlayer(DefaultPlayer player) {
        this.player = player;
    }

    public void setFieldIndex(int index){
        this.fieldIndex = index;
    }

    public void setCard(Card card){
        this.card = card;
    }
    
}
