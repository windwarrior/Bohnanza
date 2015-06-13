package nl.utwente.bpsd.ui;

import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * Created by Jochem Elsinga on 6/13/2015.
 */
public class MafiaTUI implements Observer{

    MafiaGame game;
    Scanner in;
    Boolean running = true;
    MafiaPlayer currentPlayer;

    public static final String[] MAFIA_NAMES = {"Al Cabohne","Don Corlebohne","Joe Bohnano"};

    public MafiaTUI() {
        System.out.println("Al Cabohne Bohnanza!");
        System.out.println("Currently only two player game is available...");
        in = new Scanner(System.in);
        String[] names;
        do {
            System.out.println("Please give player names (2): ");
            System.out.print("> ");
            names = in.nextLine().split(" ");
        }while(names.length != 2);
        game = new MafiaGame();
        for(String name : names) {
            game.addPlayers(new MafiaPlayer(name));
        }
        game.initialize();
        currentPlayer = (MafiaPlayer)game.getCurrentPlayer();
        game.addObserver(this);
    }

    public void run(){
        System.out.println("2P Al Cabohne Game!");
        System.out.println("Type Help for help");
        while(running) {
            System.out.print("> ");
            String line = in.nextLine();
            if (!line.equals("")) execute(line);
        }
    }

    private void execute(String command) {
        ArrayList<String> commandParts = new ArrayList<>();
        Scanner inCommand = new Scanner(command);
        while (inCommand.hasNext()) commandParts.add(inCommand.next());
        boolean result;
        switch (commandParts.get(0).toLowerCase()) {
            case "skip":
                result = currentPlayer.skip();
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
                result = currentPlayer.drawIntoHand();
                if(!result) System.out.println("Action was not completed, is the move valid?");
                break;
            case "drawr":
                //TODO: find out how action is implemented in the state machine
                System.out.println("Should this move be implicit or explicit?");
                break;
            case "planthf":
                try {
                    int fieldIndex = Integer.parseInt(commandParts.get(1));
                    System.out.println("Planting from hand to field " + fieldIndex);
                    result = currentPlayer.plantFromHand(fieldIndex-1);
                    if(!result) System.out.println("Action was not completed, is the move valid?");
                } catch (NumberFormatException e) {
                    System.err.println("Please include a valid field index");
                } catch (IndexOutOfBoundsException e){
                    System.err.println("Please include a field index");
                }
                break;
            case "planthm":
                try {
                    int handIndex = Integer.parseInt(commandParts.get(1));
                    int mafiaIndex = Integer.parseInt(commandParts.get(2));
                    System.out.println("Planting from hand " + handIndex + " to mafia " + MAFIA_NAMES[mafiaIndex-1]);
                    result = currentPlayer.plantFromHandToMafia(mafiaIndex,handIndex);
                    if(!result) System.out.println("Action was not completed, is the move valid?");
                } catch (NumberFormatException e) {
                    System.err.println("Please include a valid field and trading index");
                } catch (IndexOutOfBoundsException e){
                    System.err.println("Please include a field and trading index");
                }
                break;
            case "plantrf":
                try {
                    int fieldIndex = Integer.parseInt(commandParts.get(1));
                    int revealIndex = Integer.parseInt(commandParts.get(2));
                    System.out.println("Planting from reveal " + revealIndex + " to field " + fieldIndex);
                    result = currentPlayer.plantFromReveal(fieldIndex,revealIndex,false);
                    if(!result) System.out.println("Action was not completed, is the move valid?");
                } catch (NumberFormatException e) {
                    System.err.println("Please include a valid field and trading index");
                } catch (IndexOutOfBoundsException e){
                    System.err.println("Please include a field and trading index");
                }
                break;
            case "plantrm":
                try {
                    int mafiaIndex = Integer.parseInt(commandParts.get(1));
                    int revealIndex = Integer.parseInt(commandParts.get(2));
                    System.out.println("Planting from reveal " + revealIndex + " to mafia " + MAFIA_NAMES[mafiaIndex-1]);
                    result = currentPlayer.plantFromReveal(mafiaIndex,revealIndex,true);
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
                    result = currentPlayer.harvest(fieldIndex-1);
                    if(!result) System.out.println("Action was not completed, is the move valid?");
                } catch (NumberFormatException e) {
                    System.err.println("Please include a valid field index");
                } catch (IndexOutOfBoundsException e){
                    System.err.println("Please include a field index");
                }
                break;
            case "buy":
                result = currentPlayer.buyField();
                if(!result) System.out.println("Action was not completed, is the move valid?");
                break;
            case "current":
                System.out.println("Current player is: "+currentPlayer.getName());
                break;
            default:
                System.out.println("Please use a valid input");
        }
    }

    private void printHelp(){
        String result = "The following inputs are possible:\n" +
                " 1. Overview ..........................shows player info \n"+
                " 2. DrawH .............................draws cards into players hard \n"+
                " 3. DrawR .............................draws cards into reveal area \n"+
                " 4. PlantHF fieldIndex ................plant first bean from hand into player field \n"+
                " 5. PlantHM handIndex mafiaIndex ......plant a bean from hand into mafia field \n"+
                " 6. PlantRF fieldIndex revealIndex ....plant bean from reveal to player field \n"+
                " 7. PlantRM mafiaIndex revealIndex ....plant bean from reveal to mafia field \n"+
                " 8. Harvest fieldIndex ................harvest beans from field \n"+
                " 9. Buy ...............................buys a new field \n"+
                "10. Skip ..............................skips next move if possible \n"+
                "11. State .............................returns current game state \n"+
                "12. Help ..............................this help menu \n"+
                "13. End ...............................ends the game";
        System.out.println(result);
    }

    private void printOveriew(){
        //Player One
        MafiaPlayer playerOne = (MafiaPlayer) game.getPlayers().get(0);
        String p1Name = "Player One: "+playerOne.getName()+"\n";
        String p1Hand = "\tHand: \n";
        String p1Fields = "\tFields: \n";
        String p1Treasury = "\tTreasury size: "+playerOne.getTreasury().pileSize();
        for(int i = 0; i < playerOne.getHand().pileSize(); i++){
            p1Hand += "\t\t" + (i+1) + ": " +((HandPile)playerOne.getHand()).getCardType(i).map(CardType::toString).orElse("no beans") + "\n";
        }
        for(int i = 0; i < playerOne.getAllFields().size(); i++){
            Pile pile = playerOne.getAllFields().get(i);
            p1Fields += "\t\tField " + (i+1) + ": " + pile.pileSize() + " beans of type: " +
                    pile.peek().map((CardType ct) -> ct.toString()).orElse("no beans") + "\n";
        }
        String playerOneString = p1Name+p1Hand+p1Fields+p1Treasury+"\n";

        //Player Two
        MafiaPlayer playerTwo = (MafiaPlayer) game.getPlayers().get(1);
        String p2Name = "Player Two: "+playerTwo.getName()+"\n";
        String p2Hand = "\tHand: \n";
        String p2Fields = "\tFields: \n";
        String p2Treasury = "\tTreasury size: "+playerTwo.getTreasury().pileSize();
        for(int i = 0; i < playerTwo.getHand().pileSize(); i++){
            p2Hand += "\t\t" + (i+1) + ": " +((HandPile)playerTwo.getHand()).getCardType(i).map(CardType::toString).orElse("no beans") + "\n";
        }
        for(int i = 0; i < playerTwo.getAllFields().size(); i++){
            Pile pile = playerTwo.getAllFields().get(i);
            p2Fields += "\t\tField " + (i+1) + ": " + pile.pileSize() + " beans of type: " +
                    pile.peek().map((CardType ct) -> ct.toString()).orElse("no beans") + "\n";
        }
        String playerTwoString = p2Name+p2Hand+p2Fields+p2Treasury+"\n";

        //Shared:
        String reveal = "Reveal: \n";
        String mafia = "";
        String mafiaTreasury = "Mafia treasury size: "+game.getMafiaTreasury().pileSize();
        for(int i = 0; i < game.getRevealArray().size(); i++){
            Pile pile = game.getRevealArray().get(i);
            reveal += "\tField " + (i+1) + ": " + pile.pileSize() + " beans of type: " +
                    pile.peek().map((CardType ct) -> ct.toString()).orElse("no beans") + "\n";
        }
        for(int i = 0; i < game.getMafia().size(); i++){
            Pile pile = game.getMafia().get(i);
            reveal += MAFIA_NAMES[i] + ": " + pile.pileSize() + " beans of type: " +
                    pile.peek().map((CardType ct) -> ct.toString()).orElse("no beans") + "\n";
        }
        String gameString = reveal+mafia+mafiaTreasury;

        System.out.println(playerOneString+playerTwoString+gameString);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Received an update: " + arg);
        currentPlayer = (MafiaPlayer)game.getCurrentPlayer();
        System.out.println("Current player is now: "+currentPlayer.getName());
    }
}
