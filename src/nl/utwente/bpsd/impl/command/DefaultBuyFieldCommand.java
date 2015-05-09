/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameStatus;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bsd.model.command.Command;

/**
 *
 * @author lennart
 */
public class DefaultBuyFieldCommand implements Command {

    Player player;

    @Override
    public GameStatus execute(Game g) {
        return GameStatus.GAME_FINISHED;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
