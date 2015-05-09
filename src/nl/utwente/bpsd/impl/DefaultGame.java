package nl.utwente.bpsd.impl;

import java.util.*;

import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.DiscardPile;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.impl.command.DefaultDrawHandCommand;

public class DefaultGame extends Game{

    private List<Player> players;
    private Pile discardPile;
    private Pile gamePile;
    private GameState currentTurnState;
    private Player currentPlayer;
    private int reshuffleCounter;

    public DefaultGame(){
        players = new ArrayList<>();
    }

    public void initialize() {
        // TODO: Generate all variables in the game
        generateGameDeck();
        List<Card> empty = new ArrayList<>();
        discardPile = new DiscardPile(empty);
        // TODO: Deal initial hand to players
    }

    @Override
    public void draw(Player p) {
        // TODO: check if the current state allows this action
        
        DefaultDrawHandCommand dc = new DefaultDrawHandCommand();
        
        dc.setPlayer(p);
        dc.execute(this);
        
        // TODO: after this something has to happen with the current state
        // TODO: notify observer of the draw command
        // TODO: notify observer of the new state
    }

    public boolean validPlant(Player p, Pile field, Card card) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void trade(Player current, Player p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Card> validHarvest(Player p, Pile field) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean validBuyField(Player p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addPlayers(Player... p) {
        for(Player player : p){
            players.add(player);
            player.setGame(this);
        }
    }

    public void endGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Player determineWinner() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Player getCurrentPlayer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void nextState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Pile getGamePile() {
        return gamePile;
    }

    /**
     * Creates all cards and puts these in the gamePile
     * Note: Not sure if this should be done here, in any case it needs to happen somewhere
     */
    private void generateGameDeck(){
        Map<Integer, Integer> coffeeBeanOMeter = new HashMap<>();
        coffeeBeanOMeter.put(4,1);
        coffeeBeanOMeter.put(7,2);
        coffeeBeanOMeter.put(10,3);
        coffeeBeanOMeter.put(12,4);
        CardType coffeeBean = new CardType("Coffee Bean",coffeeBeanOMeter);

        Map<Integer, Integer> waxBeanOMeter = new HashMap<>();
        waxBeanOMeter.put(4,1);
        waxBeanOMeter.put(7,2);
        waxBeanOMeter.put(9,3);
        waxBeanOMeter.put(11,4);
        CardType waxBean = new CardType("Wax Bean",waxBeanOMeter);

        Map<Integer, Integer> blueBeanOMeter = new HashMap<>();
        blueBeanOMeter.put(4,1);
        blueBeanOMeter.put(6,2);
        blueBeanOMeter.put(8,3);
        blueBeanOMeter.put(10,4);
        CardType blueBean = new CardType("Blue Bean",blueBeanOMeter);

        Map<Integer, Integer> chiliBeanOMeter = new HashMap<>();
        chiliBeanOMeter.put(3,1);
        chiliBeanOMeter.put(6,2);
        chiliBeanOMeter.put(8,3);
        chiliBeanOMeter.put(9,4);
        CardType chiliBean = new CardType("Chili Bean",chiliBeanOMeter);

        Map<Integer, Integer> stinkBeanOMeter = new HashMap<>();
        stinkBeanOMeter.put(3,1);
        stinkBeanOMeter.put(5,2);
        stinkBeanOMeter.put(7,3);
        stinkBeanOMeter.put(8,4);
        CardType stinkBean = new CardType("Stink Bean",stinkBeanOMeter);

        Map<Integer, Integer> greenBeanOMeter = new HashMap<>();
        greenBeanOMeter.put(3,1);
        greenBeanOMeter.put(5,2);
        greenBeanOMeter.put(6,3);
        greenBeanOMeter.put(7,4);
        CardType greenBean = new CardType("Green Bean",greenBeanOMeter);

        Map<Integer, Integer> soyBeanOMeter = new HashMap<>();
        soyBeanOMeter.put(2,1);
        soyBeanOMeter.put(4,2);
        soyBeanOMeter.put(6,3);
        soyBeanOMeter.put(7,4);
        CardType soyBean = new CardType("Soy Bean",soyBeanOMeter);

        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2,1);
        blackeyedBeanOMeter.put(4,2);
        blackeyedBeanOMeter.put(5,3);
        blackeyedBeanOMeter.put(6,4);
        CardType blackeyedBean = new CardType("Black-eyed Bean",blackeyedBeanOMeter);

        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2,1);
        redBeanOMeter.put(3,2);
        redBeanOMeter.put(4,3);
        redBeanOMeter.put(5,4);
        CardType redBean = new CardType("Red Bean",redBeanOMeter);

        Map<Integer, Integer> gardenBeanOMeter = new HashMap<>();
        gardenBeanOMeter.put(2,2);
        gardenBeanOMeter.put(3,3);
        CardType gardenBean = new CardType("Garden Bean",gardenBeanOMeter);

        Map<Integer, Integer> cocoaBeanOMeter = new HashMap<>();
        cocoaBeanOMeter.put(2,2);
        cocoaBeanOMeter.put(3,3);
        cocoaBeanOMeter.put(4,4);
        CardType cocoaBean = new CardType("Cocoa Bean",cocoaBeanOMeter);

        List<Card> allCards = new ArrayList<>();
        Card coffeeBeanCard = new Card(coffeeBean);
        for(int i = 0; i < 24; i++){ allCards.add(coffeeBeanCard); }

        Card waxBeanCard = new Card(waxBean);
        for(int i = 0; i < 22; i++){ allCards.add(waxBeanCard); }

        Card blueBeanCard = new Card(blueBean);
        for(int i = 0; i < 20; i++){ allCards.add(blueBeanCard); }

        Card chiliBeanCard = new Card(chiliBean);
        for(int i = 0; i < 18; i++){ allCards.add(chiliBeanCard); }

        Card stinkBeanCard = new Card(stinkBean);
        for(int i = 0; i < 16; i++){ allCards.add(stinkBeanCard); }

        Card greenBeanCard = new Card(greenBean);
        for(int i = 0; i < 14; i++){ allCards.add(greenBeanCard); }

        Card soyBeanCard = new Card(soyBean);
        for(int i = 0; i < 12; i++){ allCards.add(soyBeanCard); }

        Card blackeyedBeanCard = new Card(blackeyedBean);
        for(int i = 0; i < 10; i++){ allCards.add(blackeyedBeanCard); }

        Card redBeanCard = new Card(redBean);
        for(int i = 0; i < 8; i++){ allCards.add(redBeanCard); }

        Card gardenBeanCard = new Card(gardenBean);
        for(int i = 0; i < 6; i++){ allCards.add(gardenBeanCard); }

        Card cocoaBeanCard = new Card(cocoaBean);
        for(int i = 0; i < 4; i++){ allCards.add(cocoaBeanCard); }

        // TODO: shuffle allCards before creating game pile
        gamePile = new Pile(allCards);
    }

    enum GameState {
        PREPERATION,
        PLAY,
        END,
        MUST_PLANT_FROM_HAND,
        MAY_PLANT_FROM_HAND,
        DRAW_FOR_TRADING,
        TRADE,
        PLANT_FROM_TRADING,
        DRAW_FOR_HAND
    }
}