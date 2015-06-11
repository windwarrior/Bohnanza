package nl.utwente.bpsd.impl.standard;

import nl.utwente.bpsd.impl.standard.command.*;

import java.util.*;

import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.model.pile.DiscardPile;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.state.State;
import nl.utwente.bpsd.model.state.StateManager;

public class StandardGame extends Game {

    public static final int NUMBER_START_CARDS = 5;

    private List<Player> players;
    private Pile discardPile;
    private Pile gamePile;
    private Player currentPlayer;
    private int reshuffleCounter;
    private StateManager stateManager;
    private List<Exchange> exchanges;

    public StandardGame() {

        players = new ArrayList<>();
        exchanges = new ArrayList<>();
    }

    public void initialize() {
        // TODO: Generate all variables in the game
        generateGameDeck();
        discardPile = new DiscardPile();
        reshuffleCounter = 0;
        for(Player p:players){
            for (int i = 0; i < NUMBER_START_CARDS; i++) {
                gamePile.pop().ifPresent((Card c) -> ((StandardPlayer)p).getHand().append(c));
            }
        }
        currentPlayer = players.get(0);

        // This stateManager is only aware of the states that a certain player has
        // It should be parallely composed with a statemanager that holds track 
        // of the players, but that is represented in the currentPlayer
        // Below are all states (with a name)
        State<StandardGameCommandResult, Command> startState = new State("Turn", StandardPlantCommand.class, StandardHarvestCommand.class, StandardBuyFieldCommand.class);

        State<StandardGameCommandResult, Command> onePlantedState = new State("One bean planted", StandardPlantCommand.class, StandardSkipCommand.class, StandardHarvestCommand.class, StandardBuyFieldCommand.class);

        State<StandardGameCommandResult, Command> drawCardToTradingState =  new State("Draw cards to trading area", StandardDrawTradeCommand.class, StandardHarvestCommand.class, StandardBuyFieldCommand.class);

        State<StandardGameCommandResult, Command> tradingState = new State("Start trading", StandardTradeCommand.class, StandardSkipCommand.class);

        State<StandardGameCommandResult, Command> plantTradedCardsState = new State("Plant traded cards", StandardPlantCommand.class, StandardSkipCommand.class, StandardHarvestCommand.class, StandardBuyFieldCommand.class);

        State<StandardGameCommandResult, Command> drawCardState = new State("Draw cards to your hand", StandardDrawHandCommand.class, StandardHarvestCommand.class, StandardBuyFieldCommand.class);

        // TODO buy field command
        // These are per state all transitions that can be taken
        startState.addTransition(StandardGameCommandResult.HARVEST, startState);
        startState.addTransition(StandardGameCommandResult.PLANT, onePlantedState);
        startState.addTransition(StandardGameCommandResult.BOUGHT_FIELD, startState);

        onePlantedState.addTransition(StandardGameCommandResult.PLANT, drawCardToTradingState);
        onePlantedState.addTransition(StandardGameCommandResult.SKIP, drawCardToTradingState);
        onePlantedState.addTransition(StandardGameCommandResult.HARVEST, onePlantedState);
        onePlantedState.addTransition(StandardGameCommandResult.BOUGHT_FIELD, onePlantedState);

        drawCardToTradingState.addTransition(StandardGameCommandResult.DRAWN_TO_TRADING, tradingState);
        drawCardToTradingState.addTransition(StandardGameCommandResult.HARVEST, drawCardToTradingState);
        drawCardToTradingState.addTransition(StandardGameCommandResult.BOUGHT_FIELD, drawCardToTradingState);

        // Trading is implemented in the statemachine, but not yet with the players
        tradingState.addTransition(StandardGameCommandResult.TRADE, tradingState);
        tradingState.addTransition(StandardGameCommandResult.SKIP, plantTradedCardsState);

        plantTradedCardsState.addTransition(StandardGameCommandResult.PLANT_TRADED, plantTradedCardsState);
        plantTradedCardsState.addTransition(StandardGameCommandResult.SKIP, drawCardState);
        plantTradedCardsState.addTransition(StandardGameCommandResult.HARVEST, plantTradedCardsState);
        plantTradedCardsState.addTransition(StandardGameCommandResult.BOUGHT_FIELD, plantTradedCardsState);

        drawCardState.addTransition(StandardGameCommandResult.DRAWN_TO_HAND, startState);
        drawCardState.addTransition(StandardGameCommandResult.HARVEST, drawCardState);
        drawCardState.addTransition(StandardGameCommandResult.BOUGHT_FIELD, drawCardState);

        stateManager = new StateManager(startState);
    }

    @Override
    public boolean executeCommand(Player p, Command klass) {
        boolean result = false;

        // Yes comparison by reference, should be the same instance as well!
        if (this.currentPlayer == p && this.stateManager.isAllowedClass(klass.getClass())) {
            GameCommandResult commandOutput = klass.execute(p,this);

            result = this.stateManager.isTransition(commandOutput);

            if (result) {
                this.stateManager.doTransition(commandOutput);

/*                if(commandOutput == StandardGameCommandResult.RESHUFFLE) {
                    //TODO: Reshuffle
                    //end game or:
                    ++reshuffleCounter;
                    commandOutput = klass.execute(this);
                }*/

                if(commandOutput == StandardGameCommandResult.DRAWN_TO_HAND) {
                    int currentPlayerIndex = this.players.indexOf(this.currentPlayer);

                    this.currentPlayer = this.players.get((currentPlayerIndex + 1) % this.players.size());
                    // Notify observers
                    this.setChanged();
                    this.notifyObservers();
                }

                if (this.stateManager.isInAcceptingState()) {
                    this.gameEnd();
                }
            }
        }

        return result;

    }

    @Override
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Pile getGamePile() {
        return gamePile;
    }

    public Pile getDiscardPile() {
        return discardPile;
    }

    public int getReshuffleCounter() {
        return reshuffleCounter;
    }

    public List<Exchange> getExchanges() { return exchanges; }

    /**
     * @return  Optional<Exchange> exchange with indicated in parameters player sides,
     * in case of no such exchange returns Optional.empty().
     */
    public Optional<Exchange> getExchange(Player player, Player opponent) {
        for (Exchange exchange : exchanges) {
            if (exchange.isPlayerInExchange(player) && exchange.isPlayerInExchange(opponent))
                return Optional.of(exchange);
        }
        return Optional.empty();
    }

    /**
     * Creates all cards and puts these in the gamePile Note: Not sure if this
     * should be done here, in any case it needs to happen somewhere
     */
    private void generateGameDeck() {
        List<CardType> allCardType = new ArrayList<>();

        Map<Integer, Integer> coffeeBeanOMeter = new HashMap<>();
        coffeeBeanOMeter.put(4, 1);
        coffeeBeanOMeter.put(7, 2);
        coffeeBeanOMeter.put(10, 3);
        coffeeBeanOMeter.put(12, 4);
        CardType coffeeBean = new CardType("Coffee Bean", coffeeBeanOMeter, 24);
        allCardType.add(coffeeBean);

        Map<Integer, Integer> waxBeanOMeter = new HashMap<>();
        waxBeanOMeter.put(4, 1);
        waxBeanOMeter.put(7, 2);
        waxBeanOMeter.put(9, 3);
        waxBeanOMeter.put(11, 4);
        CardType waxBean = new CardType("Wax Bean", waxBeanOMeter, 22);
        allCardType.add(waxBean);

        Map<Integer, Integer> blueBeanOMeter = new HashMap<>();
        blueBeanOMeter.put(4, 1);
        blueBeanOMeter.put(6, 2);
        blueBeanOMeter.put(8, 3);
        blueBeanOMeter.put(10, 4);
        CardType blueBean = new CardType("Blue Bean", blueBeanOMeter, 20);
        allCardType.add(blueBean);

        Map<Integer, Integer> chiliBeanOMeter = new HashMap<>();
        chiliBeanOMeter.put(3, 1);
        chiliBeanOMeter.put(6, 2);
        chiliBeanOMeter.put(8, 3);
        chiliBeanOMeter.put(9, 4);
        CardType chiliBean = new CardType("Chili Bean", chiliBeanOMeter, 18);
        allCardType.add(chiliBean);

        Map<Integer, Integer> stinkBeanOMeter = new HashMap<>();
        stinkBeanOMeter.put(3, 1);
        stinkBeanOMeter.put(5, 2);
        stinkBeanOMeter.put(7, 3);
        stinkBeanOMeter.put(8, 4);
        CardType stinkBean = new CardType("Stink Bean", stinkBeanOMeter, 16);
        allCardType.add(stinkBean);

        Map<Integer, Integer> greenBeanOMeter = new HashMap<>();
        greenBeanOMeter.put(3, 1);
        greenBeanOMeter.put(5, 2);
        greenBeanOMeter.put(6, 3);
        greenBeanOMeter.put(7, 4);
        CardType greenBean = new CardType("Green Bean", greenBeanOMeter, 14);
        allCardType.add(greenBean);

        Map<Integer, Integer> soyBeanOMeter = new HashMap<>();
        soyBeanOMeter.put(2, 1);
        soyBeanOMeter.put(4, 2);
        soyBeanOMeter.put(6, 3);
        soyBeanOMeter.put(7, 4);
        CardType soyBean = new CardType("Soy Bean", soyBeanOMeter, 12);
        allCardType.add(soyBean);

        Map<Integer, Integer> blackeyedBeanOMeter = new HashMap<>();
        blackeyedBeanOMeter.put(2, 1);
        blackeyedBeanOMeter.put(4, 2);
        blackeyedBeanOMeter.put(5, 3);
        blackeyedBeanOMeter.put(6, 4);
        CardType blackeyedBean = new CardType("Black-eyed Bean", blackeyedBeanOMeter, 10);
        allCardType.add(blackeyedBean);

        Map<Integer, Integer> redBeanOMeter = new HashMap<>();
        redBeanOMeter.put(2, 1);
        redBeanOMeter.put(3, 2);
        redBeanOMeter.put(4, 3);
        redBeanOMeter.put(5, 4);
        CardType redBean = new CardType("Red Bean", redBeanOMeter, 8);
        allCardType.add(redBean);

        Map<Integer, Integer> gardenBeanOMeter = new HashMap<>();
        gardenBeanOMeter.put(2, 2);
        gardenBeanOMeter.put(3, 3);
        CardType gardenBean = new CardType("Garden Bean", gardenBeanOMeter, 6);
        allCardType.add(gardenBean);

        Map<Integer, Integer> cocoaBeanOMeter = new HashMap<>();
        cocoaBeanOMeter.put(2, 2);
        cocoaBeanOMeter.put(3, 3);
        cocoaBeanOMeter.put(4, 4);
        CardType cocoaBean = new CardType("Cocoa Bean", cocoaBeanOMeter, 4);
        allCardType.add(cocoaBean);

        List<Card> allCards = new ArrayList<>();
        for (CardType ct : allCardType) {
            for (int i = 0; i < ct.getNumberOfType(); i++) {
                allCards.add(new Card(ct, i));
            }
        }

        /*List<Card> testCards = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Card c = new Card(chiliBean, i);
            testCards.add(c);
        }*/

        Collections.shuffle(allCards);
            gamePile = new Pile();
            for (Card c : allCards) {
                gamePile.append(c);
        }
    }

    @Override
    public void addPlayers(Player... players) {
        //TODO: Check if player has unique name
        this.players = Arrays.asList(players);
        for(Player p:players) p.setGame(this);
    }

    @Override
    public void gameEnd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Pile> getPileByName(String name) {
        switch (name.toLowerCase()) {
            case "discard":
                return Optional.of(this.getDiscardPile());
            case "game":
                return Optional.of(this.getGamePile());
            default:
                return Optional.empty();
        }
    }

    /**
     * Help function to make TUI more readable
     */
    public State getCurrentState(){
        return stateManager.getCurrentState();
    }
}
