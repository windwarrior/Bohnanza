package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaBoss;
import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaGameCommandResult;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MafiaSkipToPhaseSixCommand extends MafiaGameCommand {

    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p, g); // force a check that this is indeed a MafiaGame
        MafiaGame game = (MafiaGame) g;
        MafiaPlayer player = (MafiaPlayer) p;

        List<Pile> empties = new ArrayList<>();
        List<Pile> filled = new ArrayList<>();

        for (MafiaBoss mafia : game.getMafia()) {
            if (mafia.getPile().pileSize() == 0)
                empties.add(mafia.getPile());
            else
                filled.add(mafia.getPile());
        }

        //player do not need to give a card to mafia if there are no empty mafia fields or player's hand is empty
        if(empties.size()!=0 && player.getHand().pileSize()!=0) {
            /* every array field is associated with appropriate player's hand card,
             * set to false means no other mafia boss has this card type
             * otherwise array field should be set to true.
             */
            boolean[] matchArray = new boolean[player.getHand().pileSize()];
            Arrays.fill(matchArray, false);
            for (int i = 0; i < player.getHand().pileSize(); ++i) {
                for(Pile mafiaField : filled) {
                    //card from player's hand match card type of mafia boss's field
                    if(mafiaField.peek().equals(((HandPile)player.getHand()).getCardType(i))) {
                        matchArray[i] = true;
                        break;
                    }
                }
            }

            /* All array fields set to true mean that player do not have cards that can be given to mafia.
             * If there is at least one card that did not match other mafia bosses' fields,
             * player must pass it to boss with an empty field before transition to next game phase.
             */
            for (boolean aMatchArray : matchArray) {
                if (!aMatchArray)
                    return MafiaGameCommandResult.INVALID;
            }
        }

        //Additional 1Player check: reveal piles should be empty
        if(game.getPlayers().size() == 1){
            for(Pile field : game.getRevealArray()) {
                if(field.pileSize() != 0)
                    return MafiaGameCommandResult.INVALID;
            }
        }

        return MafiaGameCommandResult.SKIP_TO_PHASE_SIX;
    }

}