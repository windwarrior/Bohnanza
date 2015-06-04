package nl.utwente.bpsd.impl.standard;

import nl.utwente.bpsd.impl.standard.command.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Player;

import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.Pile;

public class StandardPlayer extends Player {

    private String name;
    private List<Pile> fields;
    private Pile hand;
    private Pile treasury;
    private Pile trading;

    public StandardPlayer(String name) {
        this.name = name;
        fields = new ArrayList<>();
        fields.add(new Pile());
        fields.add(new Pile());
        hand = new HandPile();
        treasury = new Pile();
        trading = new HandPile();
    }

    public String getName(){
        return this.name;
    }

    public boolean skip() {
        StandardSkipCommand dc = new StandardSkipCommand();
        return super.executeCommand(dc);
    }

    public boolean drawIntoHand() {
        StandardDrawHandCommand dc = new StandardDrawHandCommand();

        return super.executeCommand(dc);
    }

    public boolean drawIntoTrading() {
        StandardDrawTradeCommand dc = new StandardDrawTradeCommand();

        return super.executeCommand(dc);
    }

    public boolean plantFromTrading(int tradingIndex, int fieldIndex) {
        StandardPlantCommand dc = new StandardPlantCommand();
        dc.setFieldIndex(fieldIndex);
        dc.setCardIndex(tradingIndex);

        return super.executeCommand(dc);
    }

    public boolean plantFromHand(int fieldIndex) {
        StandardPlantCommand dc = new StandardPlantCommand();
        dc.setFieldIndex(fieldIndex);

        return super.executeCommand(dc);
    }

    //Still needs consideration
    public boolean trade() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean harvest(int fieldIndex) {
        StandardHarvestCommand dc = new StandardHarvestCommand();
        dc.setFieldIndex(fieldIndex);

        return super.executeCommand(dc);
    }

    public boolean buyField() {
        StandardBuyFieldCommand dc = new StandardBuyFieldCommand();

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
