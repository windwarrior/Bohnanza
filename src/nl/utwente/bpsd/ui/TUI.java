package nl.utwente.bpsd.ui;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Jochem Elsinga on 6/13/2015.
 */
public class TUI {

    Boolean running = true;
    Scanner in;

    public TUI(){
        in = new Scanner(System.in);
    }

    private void run(){
        System.out.println("Welcome to Bohnanza main TUI!");
        System.out.println("Type Help for help");
        while(running){
            System.out.print("> ");
            String line = in.nextLine();
            if (!line.equals("")) execute(line);
        }
        System.exit(0);
    }

    private void execute(String command){
        ArrayList<String> commandParts = new ArrayList<>();
        Scanner inCommand = new Scanner(command);
        while (inCommand.hasNext()) commandParts.add(inCommand.next());
        switch (commandParts.get(0).toLowerCase()) {
            case "help" :
                printHelp();
                break;
            case "end":
                System.out.println("Ending Bohnanza...");
                running = false;
                break;
            case "mafia":
                MafiaTUI mafiaTUI = new MafiaTUI();
                mafiaTUI.run();
                System.out.println("Weclome back!");
                break;
            case "standard":
                StandardTUI standardTUI = new StandardTUI();
                standardTUI.run();
                System.out.println("Weclome back!");
                break;
            default:
                System.out.println("Please use a valid input");
        }
    }

    private void printHelp(){
        String result = "The following inputs are possible:\n" +
                " 1. Standard ..........................starts a standard 1 player Bohnanza game \n"+
                " 2. Mafia .............................starts an Al Cabohne Bohnanza game \n"+
                " 3. Help ..............................this help menu \n"+
                " 4. End ...............................ends this program";
        System.out.println(result);
    }

    public static void main(String[] args){
        TUI tui = new TUI();
        tui.run();
    }
}
