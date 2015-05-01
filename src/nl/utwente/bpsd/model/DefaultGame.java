package nl.utwente.bpsd.model;

import java.util.List;
import nl.utwente.bpsd.model.piles.Pile;

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

    public Card draw(Player p) {
        throw new UnsupportedOperationException("Not supported yet.");
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