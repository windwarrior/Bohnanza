package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.pile.Pile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Jochem Elsinga on 6/14/2015.
 */
public class MafiaDrawCardsToRevealCommandTest {


    MafiaGame game;
    MafiaDrawCardsToRevealCommand dc;
    MafiaPlayer player;

    @Before
    public void setUp() throws Exception {
        game = new MafiaGame();
        dc = new MafiaDrawCardsToRevealCommand();
        player = new MafiaPlayer("Test");
        game.addPlayers(player);
        game.initialize();
    }

    @Test
    public void testExecute() throws Exception {
        dc.execute(player,game);
        //Shared:
        String reveal = "Reveal: \n";
        String mafia = "";
        for(int i = 0; i < game.getRevealArray().size(); i++){
            Pile pile = game.getRevealArray().get(i);
            reveal += "\tField " + (i+1) + ": " + pile.pileSize() + " beans of type: " +
                    pile.peek().map((CardType ct) -> ct.toString()).orElse("no beans") + "\n";
        }
        for(int i = 0; i < game.getMafia().size(); i++){
            Pile pile = game.getMafia().get(i);
            reveal +=  "Mafia: " + pile.pileSize() + " beans of type: " +
                    pile.peek().map((CardType ct) -> ct.toString()).orElse("no beans") + "\n";
        }
        String gameString = reveal+mafia;

        System.out.println(gameString);
    }

}