package nl.utwente.bpsd.impl;

import java.util.List;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.impl.command.DefaultDrawHandCommand;

public class DefaultGame extends Game{

    private List<Player> players;
    private Pile discardPile;
    private Pile gamePile;
    private GameState currentTurnState;
    private Player currentPlayer;
    private int reshuffleCounter;

    public DefaultGame(){

    }

    public void initialize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void draw(Player p) {
        // TODO: check if the current state allows this action
        
        DefaultDrawHandCommand dc = new DefaultDrawHandCommand();
        
        dc.setPlayer(p);
        dc.execute(this);
        
        // TODO: after this something has to happen with the current state
        // TODO: notify observer of the draw command
        // TODO: notify observer of the new state
    }

    public boolean validPlant(Player p, Pile field, Card card) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void trade(Player current, Player p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Card> validHarvest(Player p, Pile field) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean validBuyField(Player p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addPlayers(Player... p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void endGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Player determineWinner() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Player getCurrentPlayer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void nextState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Pile getGamePile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    enum GameState {
        PREPERATION,
        PLAY,
        END,
        MUST_PLANT_FROM_HAND,
        MAY_PLANT_FROM_HAND,
        DRAW_FOR_TRADING,
        TRADE,
        PLANT_FROM_TRADING,
        DRAW_FOR_HAND;
    }
}