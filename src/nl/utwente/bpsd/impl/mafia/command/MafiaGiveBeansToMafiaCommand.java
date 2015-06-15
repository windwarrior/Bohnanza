package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaBoss;
import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaGameCommandResult;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.List;
import java.util.Optional;
import nl.utwente.bpsd.model.pile.HarvestablePile;

/**
 * A bean card is taken from player's field and put into Mafia's field
 * (if bean types are matching).
 * Checks all of the players fields if there is a card that should be given to one of the Mafia bosses.
 * Phase 2 in 2-player MafiaGame/ Phase 1 in single player MafiaGame
 *
 */
public class MafiaGiveBeansToMafiaCommand extends MafiaGameCommand{

    public GameCommandResult execute(Player p, Game g) {
        super.execute(p, g);
        MafiaGame game = (MafiaGame) g;
        MafiaPlayer player = (MafiaPlayer) p;

        List<MafiaBoss> mafiaBosses = game.getMafia();
        List<HarvestablePile> fields = player.getAllFields();

        //Discard all leftover reveal pile to discard pile
        for(Pile pile : game.getRevealArray()){
            while(pile.pileSize() != 0){
                pile.pop().ifPresent((Card c) -> game.getDiscardPile().append(c));
            }
        }

        for(MafiaBoss mafia : mafiaBosses){
            Optional<CardType> mafiaCT = mafia.getPile().peek();
            for(Pile field : fields){
                if(field.pileSize() != 0 && mafiaCT.equals(field.peek()))
                    field.pop().ifPresent((Card c) -> mafia.getPile().append(c));
            }
        }

        return MafiaGameCommandResult.GIVE_BEANS;
    }
}
