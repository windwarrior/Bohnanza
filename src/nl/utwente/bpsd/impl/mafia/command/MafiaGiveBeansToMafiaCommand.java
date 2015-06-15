package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaBoss;
import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaGameCommandResult;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.List;

/**
 * A bean card is taken from player's field and put into Mafia's field
 * (if bean types are matching).
 * Checks all of the players fields if there is a card that should be given to one of the Mafia bosses.
 * Phase 2 in 2-player MafiaGame/ Phase 1 in single player MafiaGame
 */
public class MafiaGiveBeansToMafiaCommand extends MafiaGameCommand{

    public GameCommandResult execute(Player p, Game g) {
        super.execute(p, g);
        MafiaGame game = (MafiaGame) g;
        MafiaPlayer player = (MafiaPlayer) p;

        List<MafiaBoss> mafiaBosses = game.getMafia();
        List<Pile> fields = player.getAllFields();

        //Discard all leftover reveal pile to discard pile
        for(Pile pile : fields){
            while(pile.pileSize() != 0){
                pile.pop().ifPresent((Card c) -> game.getDiscardPile().append(c));
            }
        }

        CardType bossCard;
        CardType playersCard;
        for(MafiaBoss boss: mafiaBosses){
            //boss should have at least one card in field
            if(boss.getPile().pileSize() == 0 || !boss.getPile().peek().isPresent())
                continue;
            bossCard = boss.getPile().peek().get();
            for(Pile field: fields){
                //player's field can not be empty
                if(field.pileSize() == 0 || !field.peek().isPresent())
                    continue;
                playersCard = field.peek().get();
                if(playersCard.equals(bossCard))
                    boss.getPile().append(field.pop().get());
            }
        }
        return MafiaGameCommandResult.GIVE_BEANS;
    }
}
