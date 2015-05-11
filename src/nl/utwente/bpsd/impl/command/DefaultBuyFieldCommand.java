/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.DefaultGameCommandResult;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.command.Command;

import java.util.List;

//TODO: Add this to the state machine.
public class DefaultBuyFieldCommand implements Command {

    Player player;
    public static final int FIELDCOST = 3;
    public static final int NUMMAXFIELDS = 3;

    @Override
    public DefaultGameCommandResult execute(Game g) {
        Pile treasury = player.getTreasury();
        List<Pile> fields = player.getAllFields();
        if(treasury.pileSize() >= FIELDCOST && fields.size() < NUMMAXFIELDS){
            for (int i = 0; i < FIELDCOST; i++) {
                treasury.pop();
            }
            Pile newField = new Pile();
            fields.add(newField);
        }
        return DefaultGameCommandResult.GAME_PROGRESS;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
