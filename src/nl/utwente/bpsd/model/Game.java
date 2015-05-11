package nl.utwente.bpsd.model;

import java.util.List;
import java.util.Observable;
import nl.utwente.bpsd.model.pile.Pile;

public abstract class Game extends Observable {

    /**
     * Give players this game
     * Set up card pile
     */
    public abstract void initialize();

    /**
     * Does the draw into hand action for a player p
     */
    public abstract void draw(Player p);

    /**
     * Does the draw into trading action for a player p
     */
    public abstract void drawTrading(Player player);

    /**
     * returns if a Player p may plant Card card in Pile field
     */
    public abstract void plantFromHand(Player p, int fieldIndex);

    public abstract void plantFromTrading(Player p, int tradingIndex, int fieldIndex);

    //Still difficult
    public abstract void trade(Player current, Player p);

    /**
     * Harvest Pile field for player p, adds profits to player treasury and the rest of the pile into the game discard pile
     */
    public abstract void harvest(Player p, int fieldIndex);

    /**
     * returns if a Player p may buy an extra field
     */
    public abstract void buyField(Player p);

    /**
     * Adds players to the game
     */
    public abstract void addPlayers(Player... p);

    public abstract void endGame();

    public abstract Player determineWinner();

    public abstract Player getCurrentPlayer();

    
    public abstract Pile getGamePile();

    public abstract Pile getDiscardPile();
}
