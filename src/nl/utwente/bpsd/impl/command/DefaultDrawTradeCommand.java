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

/**
 *
 * @author lennart
 */
public class DefaultDrawTradeCommand implements Command {

    Player player;

    @Override
    public GameStatus execute(Game g) {
        Pile gamePile = g.getGamePile();
        if(gamePile.pileSize() < 2) {
            // TODO: Implement reshuffle functionality
            return GameStatus.GAME_FINISHED;
        }
        Card firstDraw = gamePile.pop();
        Card secondDraw = gamePile.pop();
        player.getTrading().append(firstDraw);
        player.getTrading().append(secondDraw);
        return GameStatus.GAME_PROGRESS;
    }

    public void setPlayer(Player player){
        this.player = player;
    }
}
