package nl.utwente.bpsd.ui;

import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.*;

/**
 * 1 player textual UI for default game without trading
 */
public class TUI implements Observer {

    StandardPlayer player;
    StandardGame game;
    Scanner in;
    Boolean running = true;

    public TUI() {
        player = new StandardPlayer("New Player");
        game = new StandardGame();
        in = new Scanner(System.in);
        game.addPlayers(player);
        game.initialize();
        game.addObserver(this);
    }

    public void run() {
        System.out.println("1P Bohnanza Game!");
        System.out.println("Type Help for help");
        while (running) {
            System.out.print("> ");
            String line = in.nextLine();
            if (!line.equals("")) execute(line);
        }
        System.exit(0);
    }

    public void execute(String command) {
        ArrayList<String> commandParts = new ArrayList<>();
        Scanner inCommand = new Scanner(command);
        while (inCommand.hasNext()) commandParts.add(inCommand.next());
        boolean result;
        switch (commandParts.get(0).toLowerCase()) {
            case "skip":
                result = player.skip();
                if(!result) System.out.println("Action was not completed, is the move valid?");
                break;
            case "state":
                System.out.println("Current state is: "+game.getCurrentState());
                break;
            case "help":
                printHelp();
                break;
            case "end":
                System.out.println("Ending game...");
                running = false;
                break;
            case "overview":
                printOveriew();
                break;
            case "drawh":
                result = player.drawIntoHand();
                if(!result) System.out.println("Action was not completed, is the move valid?");
                break;
            case "drawt":
                result = player.drawIntoTrading();
                if(!result) System.out.println("Action was not completed, is the move valid?");
                break;
            case "planth":
                try {
                    int fieldIndex = Integer.parseInt(commandParts.get(1));
                    System.out.println("Planting from hand to field " + fieldIndex);
                    result = player.plantFromHand(fieldIndex-1);
                    if(!result) System.out.println("Action was not completed, is the move valid?");
                } catch (NumberFormatException e) {
                    System.err.println("Please include a valid field index");
                } catch (IndexOutOfBoundsException e){
                    System.err.println("Please include a field index");
                }
               break;
            case "plantt":
                try {
                    int fieldIndex = Integer.parseInt(commandParts.get(1));
                    int tradingIndex = Integer.parseInt(commandParts.get(2));
                    System.out.println("Planting from trading " + tradingIndex + " to field " + fieldIndex);
                    result = player.plantFromTrading(tradingIndex-1, fieldIndex-1);
                    if(!result) System.out.println("Action was not completed, is the move valid?");
                } catch (NumberFormatException e) {
                    System.err.println("Please include a valid field and trading index");
                } catch (IndexOutOfBoundsException e){
                    System.err.println("Please include a field and trading index");
                }
                break;
            case "harvest":
                try {
                    int fieldIndex = Integer.parseInt(commandParts.get(1));
                    System.out.println("Harvesting field " + fieldIndex);
                    result = player.harvest(fieldIndex-1);
                    if(!result) System.out.println("Action was not completed, is the move valid?");
                } catch (NumberFormatException e) {
                    System.err.println("Please include a valid field index");
                } catch (IndexOutOfBoundsException e){
                    System.err.println("Please include a field index");
                }
                break;
            case "buy":
                result = player.buyField();
                if(!result) System.out.println("Action was not completed, is the move valid?");
                break;
            default:
                System.out.println("Please use a valid input");
        }

    }

    private void printHelp(){
        String result = "The following inputs are possible:\n" +
                " 1. Overview...........................shows player info \n"+
                " 2. DrawH .............................daws cards into players hand \n"+
                " 3. DrawT..............................draws cards into trading area \n"+
                " 4. PlantH fieldIndex..................plant bean from hand into field \n"+
                " 5. PlantT fieldIndex tradingIndex.....plant bean from trading into field \n"+
                " 6. Harvest fieldIndex.................harvest beans from field \n"+
                " 7. Buy................................buys a new field \n"+
                " 8. Skip...............................skips next move if possible \n"+
                " 9. State..............................returns current game state \n"+
                "10. Help...............................this help menu \n"+
                "11. End................................ends the game";
        System.out.println(result);
    }


    private void printOveriew(){
        String hand = "Hand: \n";
        String trading = "Trading: \n";
        String fields = "Fields: \n";
        String treasury = "Treasury size: "+player.getTreasury().pileSize();
        for(int i = 0; i < player.getHand().pileSize(); i++){
            hand += "\t" + (i+1) + ": " +((HandPile)player.getHand()).getCardType(i).map(CardType::toString).orElse("no beans") + "\n";
        }
        for(int i = 0; i < player.getTrading().pileSize(); i++){
            trading += "\t" + (i+1) + ": " + ((HandPile)player.getTrading()).getCardType(i).map(CardType::toString).orElse("no beans") + "\n";
        }
        for(int i = 0; i < player.getAllFields().size(); i++){
            Pile pile = player.getAllFields().get(i);
            fields += "\tField " + (i+1) + ": " + pile.pileSize() + " beans of type: " +
                    pile.peek().map((CardType ct) -> ct.toString()).orElse("no beans") + "\n";
        }
        System.out.println(hand+fields+trading+treasury);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Received an update:" + arg);

    }

    public static void main(String args[]) {
        TUI tui = new TUI();
        tui.run();
    }
}