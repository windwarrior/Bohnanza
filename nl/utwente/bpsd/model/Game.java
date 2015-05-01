package nl.utwente.bpsd.model;

import java.util.Observable;

public interface Game extends Observable{

    /**
     * Give players this game
     * Set up card piles
     */
    public void initialize();

    /**
     * Draws (returns) a card for the player p
     */
    public Card draw(Player p);

    /**
     * returns if a Player p may plant Card card in Pile field
     */
    public boolean validPlant(Player p, Pile field, Card card);

    //Still difficult
    public void trade(Player current, Player p);

    /**
     * returns the profits in a terms of cards if a Player p harvests Pile field
     */
    public List<Card> validHarvest(Player p, Pile field);

    /**
     * returns if a Player p may buy an extra field
     */
    public boolean validBuyField(Player p);

    /**
     * Adds players to the game
     */
    public void addPlayers(Player... p);

    public void endGame();

    public Player determineWinner();

    public Player getCurrentPlayer();

    /**
     * Update game state and currentPlayer or end game
     * Done afer every move made?
     */
    private void nextState();
}
