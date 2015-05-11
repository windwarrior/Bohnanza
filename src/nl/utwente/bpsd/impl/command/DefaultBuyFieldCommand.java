/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.Command;

import java.util.List;
import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.GameCommandResult;

//TODO: Add this to the state machine.
public class DefaultBuyFieldCommand extends DefaultGameCommand {

    DefaultPlayer player;
    public static final int FIELDCOST = 3;
    public static final int NUMMAXFIELDS = 3;

    @Override
    public GameCommandResult execute(Game game) {
        super.execute(game); // force a check that this is indeed a defaultgame
        DefaultGame dg = (DefaultGame) game; // Cast it because it is now indeed a DefaultGame
        
        
        Pile treasury = player.getTreasury();
        List<Pile> fields = player.getAllFields();
        if(treasury.pileSize() >= FIELDCOST && fields.size() < NUMMAXFIELDS){
            for (int i = 0; i < FIELDCOST; i++) {
                treasury.pop();
            }
            Pile newField = new Pile();
            fields.add(newField);
        }
        return DefaultGameCommandResult.BOUGHT_FIELD;
    }

    public void setPlayer(DefaultPlayer player) {
        this.player = player;
    }
}
