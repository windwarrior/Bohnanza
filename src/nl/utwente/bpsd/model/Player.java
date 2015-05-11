package nl.utwente.bpsd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.utwente.bpsd.model.pile.HandPile;
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

    public Player(String name) {
        this.name = name;
        fields = new ArrayList<>();
        fields.add(new Pile());
        fields.add(new Pile());
        hand = new HandPile();
        treasury = new Pile();
        trading = new HandPile();
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void drawIntoHand() {
        this.game.draw(this);
    }

    public void drawIntoTrading() { this.game.drawTrading(this);}

    public void plantFromTrading(int tradingIndex, int fieldIndex){
        game.plantFromTrading(this,tradingIndex,fieldIndex);
    }

    public void plantFromHand(int fieldIndex) {
        game.plantFromHand(this,fieldIndex);
    }

    //Still needs consideration
    public void trade() {}

    public void harvest(int fieldIndex) {
        game.harvest(this,fieldIndex);
    }

    public void buyField() {}

    public Pile getHand() {
        return hand;
    }

    public Pile getTreasury() {
        return treasury;
    }

    public Pile getTrading() {
        return trading;
    }

    public List<Pile> getAllFields() {
        return fields;
    }

    public void extendFields(){}

    //For possible extension with new Piles
    public Pile getAdditionalPileByName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addAllHand(List<Card> toBeInserted) {
        for (Card c : toBeInserted) {
            hand.append(c);
        }
    }

    @Override
    public String toString(){
        return String.format("This is player with name: %s",this.name);
    }
}