package nl.utwente.bpsd.impl.mafia;

import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.model.pile.DiscardPile;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.ArrayList;

public class MafiaGame extends StandardGame{

    private Pile alCabohne;
    private Pile donCorlebohne;
    private Pile joeBohnano;
    private Pile mafiaTreasury;

    private ArrayList<Pile> revealArray;

    public MafiaGame(){
        super();
    }

    @Override
    public void initialize(){
        generateGameDeck();
        alCabohne = new Pile();
        donCorlebohne = new Pile();
        revealArray = new ArrayList<Pile>();
        //TODO: if 1-player game joeBohnano should be set
        mafiaTreasury = new Pile();
        this.setDiscardPile(new DiscardPile());

    }

    private void generateGameDeck(){
        this.setGamePile(new Pile());
    }

    public ArrayList<Pile> getRevealArray() { return revealArray; }

}
