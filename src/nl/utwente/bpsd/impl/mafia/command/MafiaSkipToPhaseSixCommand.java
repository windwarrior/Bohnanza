package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaBoss;
import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaGameCommandResult;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MafiaSkipToPhaseSixCommand extends MafiaGameCommand {

    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p, g); // force a check that this is indeed a MafiaGame
        MafiaGame game = (MafiaGame) g;
        MafiaPlayer player = (MafiaPlayer) p;

        MafiaGameCommandResult result = MafiaGameCommandResult.INVALID;

        List<Pile> empties = new ArrayList<>();
        List<Pile> filled = new ArrayList<>();

        for (MafiaBoss mafia : game.getMafia()) {
            if (mafia.getPile().pileSize() == 0) empties.add(mafia.getPile());
            else filled.add(mafia.getPile());
        }

        //An empty field but player does not have valid card to give mafia:

        for(Pile mafia : filled){
            boolean match = true;
            Optional<CardType> mafiaCT = mafia.peek();
            for (int i = 0; i < player.getHand().pileSize() && match; i++) {
                Optional<CardType> handCT = ((HandPile)player.getHand()).getCardType(i);
                match = mafiaCT.equals(handCT);
            }
        }

        //Additional 1Player check
        boolean OnePlayerCheck = true;
        if(game.getPlayers().size() == 1){
            for(Pile field : game.getRevealArray()){
                OnePlayerCheck = OnePlayerCheck && field.pileSize() == 0;
            }
        }

        //All fields full or no cards in hand (for 1 player reveal is also empty):
        if (OnePlayerCheck && (empties.size() == 0 || player.getHand().pileSize() == 0)) {
            result = MafiaGameCommandResult.SKIP_TO_PHASE_SIX;
        }

        return result;
    }

}