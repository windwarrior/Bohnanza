package nl.utwente.bpsd.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.utwente.bpsd.impl.command.*;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Player;

import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.Pile;

public class DefaultPlayer extends Player {

    private String name;
    private List<Pile> fields;
    private Pile hand;
    private Pile treasury;
    private Pile trading;

    public DefaultPlayer(String name) {
        this.name = name;
        fields = new ArrayList<>();
        fields.add(new Pile());
        fields.add(new Pile());
        hand = new HandPile();
        treasury = new Pile();
        trading = new HandPile();
    }

    public boolean drawIntoHand() {
        DefaultDrawHandCommand dc = new DefaultDrawHandCommand();

        return super.executeCommand(dc);
    }

    public boolean drawIntoTrading() {
        DefaultDrawTradeCommand dc = new DefaultDrawTradeCommand();

        return super.executeCommand(dc);
    }

    public boolean plantFromTrading(int tradingIndex, int fieldIndex) {
        DefaultPlantCommand dc = new DefaultPlantCommand();
        dc.setFieldIndex(fieldIndex);
        Optional<Card> card = ((HandPile) (this.getTrading())).getCard(tradingIndex);
        // TODO: check if optional card contains a card otherwise error
        dc.setCard(card.get());
        return super.executeCommand(dc);
    }

    public boolean plantFromHand(int fieldIndex) {
        // TODO: handle states
        DefaultPlantCommand dc = new DefaultPlantCommand();
        dc.setFieldIndex(fieldIndex);
        // TODO: This should have some check to see if a player still has cards in his hand
        dc.setCard(this.getHand().pop().get());
        return super.executeCommand(dc);
    }

    //Still needs consideration
    public boolean trade() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean harvest(int fieldIndex) {
        DefaultHarvestCommand dc = new DefaultHarvestCommand();
        dc.setFieldIndex(fieldIndex);

        return super.executeCommand(dc);
    }

    public boolean buyField() {
        DefaultBuyFieldCommand dc = new DefaultBuyFieldCommand();

        return super.executeCommand(dc);
    }

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

    //For possible extension with new Piles
    public Pile getAdditionalPileByName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return String.format("This is player with name: %s", this.name);
    }

    @Override
    public Optional<Pile> getPileByName(String name) {
        // early returns because that makes this function way shorter
        switch (name.toLowerCase()) {
            case "hand":
                return Optional.of(this.getHand());
            case "treasury":
                return Optional.of(this.getTreasury());
            case "trading":
                return Optional.of(this.getTrading());
            default:
                return Optional.empty();
        }
    }
}
