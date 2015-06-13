package nl.utwente.bpsd.impl.mafia;

import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.DiscardPile;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.*;

public class MafiaGame extends StandardGame{

    //@requires 1 < numPlayers && numPlayers <= 3;
    private int numPlayers = 2;
    private ArrayList<Pile> mafia;
    private Pile mafiaTreasury;

    private ArrayList<Pile> revealArray;

    public MafiaGame(){
        super();
    }

    @Override
    public void initialize(){
        mafia = new ArrayList<>();
        //Order in game: "Al Cabohne", "Don Corlebohne" and optionally "Joe Bohnano"
        for (int i = 0; i < numPlayers; i++) {
            mafia.add(new Pile());
        }
        revealArray = new ArrayList<Pile>();
        //TODO: if 1-player game joeBohnano should be set
        mafiaTreasury = new Pile();
        this.setDiscardPile(new DiscardPile());
        this.generateGameDeck();
        this.setupPhase();
    }

    private void setupPhase(){
        //Players get cards
        for(Player p:getPlayers()){
            for (int i = 0; i < NUMBER_START_CARDS; i++) {
                getGamePile().pop().ifPresent((Card c) -> ((MafiaPlayer)p).getHand().append(c));
            }
        }
        //Al Cabohne gets cards
        do {
            getGamePile().pop().ifPresent((Card c) -> mafia.get(0).append(c));
        }while(getGamePile().peek().equals(mafia.get(0).peek()));
        //Don Corlebohne gets cards
        getGamePile().pop().ifPresent((Card c) -> mafia.get(1).append(c));
    }

    private void generateGameDeck() {
        List<CardType> allCardType = new ArrayList<>();

        Map<Integer, Integer> blueBeanOMeter = new HashMap<>();
        blueBeanOMeter.put(4, 1);
        blueBeanOMeter.put(6, 2);
        blueBeanOMeter.put(8, 3);
        blueBeanOMeter.put(10, 4);
        CardType blueBean = new CardType("Blue Bean", blueBeanOMeter, 20);
        allCardType.add(blueBean);

        Map<Integer, Integer> kidneyBeanOMeter = new HashMap<>();
        kidneyBeanOMeter.put(3, 1);
        kidneyBeanOMeter.put(6, 2);
        kidneyBeanOMeter.put(7, 3);
        kidneyBeanOMeter.put(8, 4);
        CardType kidneyBean = new CardType("Kidney Bean", kidneyBeanOMeter, 19);
        allCardType.add(kidneyBean);

        Map<Integer, Integer> fireBeanOMeter = new HashMap<>();
        fireBeanOMeter.put(3, 1);
        fireBeanOMeter.put(6, 2);
        fireBeanOMeter.put(8, 3);
        fireBeanOMeter.put(9, 4);
        CardType fireBean = new CardType("Fire Bean", fireBeanOMeter, 18);
        allCardType.add(fireBean);

        Map<Integer, Integer> puffBeanOMeter = new HashMap<>();
        puffBeanOMeter.put(4, 1);
        puffBeanOMeter.put(5, 2);
        puffBeanOMeter.put(6, 3);
        puffBeanOMeter.put(7, 4);
        CardType puffBean = new CardType("Puff Bean", puffBeanOMeter, 16);
        allCardType.add(puffBean);

        Map<Integer,Integer> broadBeanOMeter = new HashMap<>();
        broadBeanOMeter.put(3, 1);
        broadBeanOMeter.put(5, 2);
        broadBeanOMeter.put(7, 3);
        broadBeanOMeter.put(8, 4);
        CardType broadBean = new CardType("Broad Bean", broadBeanOMeter, 16);
        allCardType.add(broadBean);

        Map<Integer, Integer> frenchBeanOMeter = new HashMap<>();
        frenchBeanOMeter.put(3, 1);
        frenchBeanOMeter.put(5, 2);
        frenchBeanOMeter.put(6, 3);
        frenchBeanOMeter.put(7, 4);
        CardType frenchBean = new CardType("French Bean", frenchBeanOMeter, 14);
        allCardType.add(frenchBean);

        Map<Integer,Integer> runnerBeanOMeter = new HashMap<>();
        runnerBeanOMeter.put(3, 1);
        runnerBeanOMeter.put(4, 2);
        runnerBeanOMeter.put(5, 3);
        runnerBeanOMeter.put(6, 4);
        CardType runnerBean = new CardType("Runner Bean", runnerBeanOMeter, 13);
        allCardType.add(runnerBean);

        List<Card> allCards = new ArrayList<>();
        for (CardType ct : allCardType) {
            for (int i = 0; i < ct.getNumberOfType(); i++) {
                allCards.add(new Card(ct,i));
            }
        }

        Collections.shuffle(allCards);
        Pile gameCards = new Pile();
        for(Card c : allCards) gameCards.append(c);
        this.setGamePile(gameCards);
    }

    public ArrayList<Pile> getRevealArray() { return revealArray; }

    public ArrayList<Pile> getMafia() { return mafia; }

    public Pile getMafiaTreasury() { return mafiaTreasury; }

}
