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
import nl.utwente.bpsd.model.command.Command;

import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author lennart
 * @author Kasia
 */
public class DefaultHarvestCommand implements Command {

    Player player;
    int fieldIndex;

    /**
     * Sells beans from the player's selected bean field
     * @requires this.player != null this.fieldIndex != null && g != null;
     */
    @Override
    public GameStatus execute(Game g) {
        // TODO: checking if this.fieldIndex is in range and this.player != null this.fieldIndex != null && g != null
        List<Pile> fields = player.getAllFields();
        Pile field = fields.get(fieldIndex);

        /*
         * Player can sell from any bean field where are at least 2 cards
         * Field with single card can be sell only when all his bean fields have just one card
         */
        boolean singleCard = true;
        if(field.pileSize() == 1) {
            for (int i = 0; i < fields.size() && singleCard; ++i) {
                singleCard = false;
                if (fields.get(i).pileSize() == 1) {
                    singleCard = true;
                }
            }
        }

        if(field.pileSize()>0 && singleCard) {
            int fieldSize = field.pileSize();
            TreeMap<Integer, Integer> beanOMeter = new TreeMap<Integer, Integer>(field.peek().getCardType().getBeanOMeter());

            int earnedCoins;
            Integer previousKey = beanOMeter.firstKey();
            for(Integer key: beanOMeter.keySet()) {
                if (key <= fieldSize) {
                    previousKey = key;
                }
            }
            earnedCoins = beanOMeter.get(previousKey);

            int numberOfDiscarded = fieldSize - earnedCoins;
            for(int i=numberOfDiscarded; i>0; --i) {
                g.getDiscardPile().append(field.pop());
            }

            for(int i=earnedCoins; i>0; --i) {
                player.getTreasury().append(field.pop());
            }

            return GameStatus.GAME_PROGRESS;
        }
        else {
            //maybe other error handling?
            return GameStatus.GAME_HARVEST_ERROR;
        }

    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setFieldIndex(int index){
        this.fieldIndex = index;
    }
    
}
