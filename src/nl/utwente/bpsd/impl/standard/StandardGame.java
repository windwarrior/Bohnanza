package nl.utwente.bpsd.impl.standard;

import nl.utwente.bpsd.impl.standard.command.StandardPlantTradedCommand;
import nl.utwente.bpsd.impl.standard.command.StandardSkipCommand;
import nl.utwente.bpsd.impl.standard.command.StandardTradeCommand;
import nl.utwente.bpsd.impl.standard.command.StandardDrawHandCommand;
import java.util.*;

import nl.utwente.bpsd.impl.standard.command.StandardDrawTradeCommand;
import nl.utwente.bpsd.impl.standard.command.StandardHarvestCommand;
import nl.utwente.bpsd.impl.standard.command.StandardPlantCommand;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.pile.DiscardPile;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.Command;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.state.State;
import nl.utwente.bpsd.model.state.StateManager;

public class StandardGame extends Game {

    private List<Player> players;
    private Pile discardPile;
    private Pile gamePile;
    private Player currentPlayer;
    private int reshuffleCounter;
    private StateManager stateManager;

    public StandardGame() {
        players = new ArrayList<>();
    }

    public void initialize() {
        // TODO: Generate all variables in the game
        generateGameDeck();
        discardPile = new DiscardPile();
        reshuffleCounter = 0;
        // TODO: Deal initial hand to players

        // This stateManager is only aware of the states that a certain player has
        // It should be parallely composed with a statemanager that holds track 
        // of the players, but that is represented in the currentPlayer
        // Below are all states (with a name)
        State<StandardGameCommandResult, Command> startState = new State("Turn", StandardPlantCommand.class, StandardHarvestCommand.class);

        State<StandardGameCommandResult, Command> onePlantedState = new State("One bean planted", StandardPlantCommand.class, StandardSkipCommand.class);

        State<StandardGameCommandResult, Command> drawCardToTradingState =  new State("Draw cards to trading area", StandardDrawTradeCommand.class);

        State<StandardGameCommandResult, Command> tradingState = new State("Start trading", StandardTradeCommand.class, StandardSkipCommand.class);

        State<StandardGameCommandResult, Command> plantTradedCardsState = new State("Plant traded cards", StandardPlantTradedCommand.class, StandardSkipCommand.class);

        State<StandardGameCommandResult, Command> drawCardState = new State("Draw cards to your hand", StandardDrawHandCommand.class);

        // TODO buy field command
        // These are per state all transitions that can be taken
        startState.addTransition(StandardGameCommandResult.HARVEST, startState);
        startState.addTransition(StandardGameCommandResult.PLANT, onePlantedState);

        onePlantedState.addTransition(StandardGameCommandResult.PLANT, drawCardToTradingState);
        onePlantedState.addTransition(StandardGameCommandResult.SKIP, drawCardToTradingState);

        drawCardToTradingState.addTransition(StandardGameCommandResult.DRAWN_TO_TRADING, tradingState);

        // Trading is implemented in the statemachine, but not yet with the players
        tradingState.addTransition(StandardGameCommandResult.TRADE, tradingState);
        tradingState.addTransition(StandardGameCommandResult.SKIP, plantTradedCardsState);

        plantTradedCardsState.addTransition(StandardGameCommandResult.PLANT_TRADED, plantTradedCardsState);
        plantTradedCardsState.addTransition(StandardGameCommandResult.SKIP, drawCardState);

        drawCardState.addTransition(StandardGameCommandResult.DRAWN_TO_HAND, startState);

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
            Card c = new Card(ct);
            for (int i = 0; i < ct.getNumberOfType(); i++) {
                allCards.add(c);
            }
        }

        Collections.shuffle(allCards);
            gamePile = new Pile();
            for (Card c : allCards) {
                gamePile.append(c);
        }
    }

    @Override
    public void addPlayers(Player... players) {
        this.players = Arrays.asList(players);
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

}