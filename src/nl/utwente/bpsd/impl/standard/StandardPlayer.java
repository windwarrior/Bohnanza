package nl.utwente.bpsd.impl.standard;

import nl.utwente.bpsd.impl.standard.command.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.Player;

import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.HarvestablePile;
import nl.utwente.bpsd.model.pile.Pile;

public class StandardPlayer extends Player {

    private String name;
    //TODO: should these now also be harvest piles?
    private List<HarvestablePile> fields;
    private Pile hand;
    private Pile treasury;
    private Pile trading;

    public StandardPlayer(String name) {
        this.name = name;
        hand = new HandPile();
        treasury = new Pile();
        trading = new HandPile();
        
        
        fields = new ArrayList<>();
    }
    
    public void addField(HarvestablePile p) {
        fields.add(p);
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

    //TODO: remove this command
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

    public boolean startExchange(Player opponent){
        StandardStartExchangeCommand ec = new StandardStartExchangeCommand();
        ec.setOpponent(opponent);
        return super.executeCommand(ec);
    }

    public boolean acceptExchange(Player opponent){
        StandardAcceptExchangeCommand ec = new StandardAcceptExchangeCommand();
        ec.setOpponent(opponent);
        return super.executeCommand(ec);
    }

    public boolean declineExchange(Player opponent){
        StandardDeclineExchangeCommand ec = new StandardDeclineExchangeCommand();
        ec.setOpponent(opponent);
        return super.executeCommand(ec);
    }

    public boolean addHandCardToExchange(Player opponent, int cardIndex){
        StandardAddHandCardToExchangeCommand ec = new StandardAddHandCardToExchangeCommand();
        ec.setOpponent(opponent);
        ec.setCardIndex(cardIndex);
        return super.executeCommand(ec);
    }

    public boolean addTradingAreaCardToExchange(Player opponent, int cardIndex){
        StandardAddTradingAreaCardToExchangeCommand ec = new StandardAddTradingAreaCardToExchangeCommand();
        ec.setOpponent(opponent);
        ec.setCardIndex(cardIndex);
        return super.executeCommand(ec);
    }

    public boolean removeCardFromExchange(Player opponent, int cardIndex){
        StandardRemoveCardFromExchangeCommand ec = new StandardRemoveCardFromExchangeCommand();
        ec.setOpponent(opponent);
        ec.setCardIndex(cardIndex);
        return super.executeCommand(ec);
    }

    public Pile getHand() {
        return hand;
    }


    @Override
    public Pile getTreasury() {
        return treasury;
    }
    
    public Pile getTrading() {
        return trading;
    }

    public List<HarvestablePile> getAllFields() {
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
