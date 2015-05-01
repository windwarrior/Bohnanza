package nl.utwente.bpsd.model;

import java.util.List;
import java.util.Observable;
import nl.utwente.bpsd.model.piles.Pile;

public abstract class Game extends Observable {

    /**
     * Give players this game
     * Set up card piles
     */
    public abstract void initialize();

    /**
     * Draws (returns) a card for the player p
     */
    public abstract Card draw(Player p);

    /**
     * returns if a Player p may plant Card card in Pile field
     */
    public abstract boolean validPlant(Player p, Pile field, Card card);

    //Still difficult
    public abstract void trade(Player current, Player p);

    /**
     * returns the profits in a terms of cards if a Player p harvests Pile field
     */
    public abstract List<Card> validHarvest(Player p, Pile field);

    /**
     * returns if a Player p may buy an extra field
     */
    public abstract boolean validBuyField(Player p);

    /**
     * Adds players to the game
     */
    public abstract void addPlayers(Player... p);

    public abstract void endGame();

    public abstract Player determineWinner();

    public abstract Player getCurrentPlayer();

    /**
     * Update game state and currentPlayer or end game
     * Done afer every move made?
     */
    public abstract void nextState();
}