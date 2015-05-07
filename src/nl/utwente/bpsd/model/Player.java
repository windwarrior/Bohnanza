package nl.utwente.bpsd.model;

import java.util.List;
import java.util.Map;
import nl.utwente.bpsd.model.pile.Pile;

public class Player {
    private String name;
    private List<Pile> fields;
    private Pile hand;
    private Pile treasury;
    private Pile trading;
    private Game game;

    // This is for possible expansions.
    private Map<String, Pile> additionalPiles;

    public Player(String name) {}

    public void drawIntoHand() {
        this.game.draw(this);
    }

    public void drawIntoTrading() {}

    public void plant(Card card, int fieldIndex) {}

    //Still needs consideration
    public void trade() {}

    public void harvest(int fieldIndex) {}

    public void buyField() {}

    public Pile getHand() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Pile getTreasury() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Pile getTrading() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Pile> getAllFields() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void extendFields(){}

    //For possible extension with new Piles
    public Pile getAdditionalPileByName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addAllHand(List<Card> toBeInserted) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}