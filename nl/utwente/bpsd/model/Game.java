package nl.utwente.bpsd.model;

import java.util.Observable;

public interface Game extends Observable{

    public void draw(Player p);

    public void plant(Player p, int field, Card card);

    public void trade(Player current, Player p);

    public void harvest(Player p, int field);

    public void buyField(Player p);

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
