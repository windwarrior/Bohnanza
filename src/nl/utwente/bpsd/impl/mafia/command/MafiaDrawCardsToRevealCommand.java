package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaBoss;
import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaGameCommandResult;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.ArrayList;
import java.util.Optional;

public class MafiaDrawCardsToRevealCommand extends MafiaGameCommand{

    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a MafiaGame
        MafiaGame game = (MafiaGame) g;

        MafiaGameCommandResult result = MafiaGameCommandResult.DRAW_REVEAL;

        ArrayList<Pile> reveal = game.getRevealArray();
        ArrayList<MafiaBoss> mafiaBosses = game.getMafia();
        int revealCounter = 0;
        boolean mafiaPlant;
        boolean discardMatch;

        while(revealCounter < MafiaGame.NUM_REVEAL_PILES) {
            do {
                mafiaPlant = false;
                //1. Look at the top bean from the draw pile (if empty reshuffle -> if reshuffle fails go to step 4)
                Optional<CardType> topDeck = game.getGamePile().peek();
                if (!topDeck.isPresent()) {
                }//TODO: reshuffle

                //2. compare to the beans planted in mafia fields -> if equal plant to mafia (harvest if possible)
                for (MafiaBoss mafiaBoss : mafiaBosses) {
                    if (mafiaBoss.getPile().peek().equals(topDeck)) {
                        game.getGamePile().pop().ifPresent((Card c) -> mafiaBoss.getPile().append(c));
                        mafiaPlant = true;
                        //TODO: possible harvest of MafiaPile
                    }
                }

                //2b. else draw to reveal pile
                if (!mafiaPlant) {
                    //This may be a bit ugly :(
                    Optional<Card> toAdd = game.getGamePile().pop();
                    if(!toAdd.isPresent()) return MafiaGameCommandResult.INVALID;
                    reveal.get(revealCounter).append(toAdd.get());
                    revealCounter++;
                }
            } while (mafiaPlant);

            //3. Look at the top bean from the discard pile
            do {
                discardMatch = false;
                Optional<CardType> topDeck = game.getDiscardPile().peek();
                if (topDeck.isPresent()){
                    for(MafiaBoss mafiaBoss : mafiaBosses) {
                        if (!discardMatch && mafiaBoss.getPile().peek().equals(topDeck)) {
                            game.getDiscardPile().pop().ifPresent((Card c) -> mafiaBoss.getPile().append(c));
                            discardMatch = true;
                            //TODO: possible harvest of MafiaPile
                        }
                    }
                    for(Pile revealPile : reveal) {
                        if (!discardMatch && revealPile.peek().equals(topDeck)) {
                            game.getDiscardPile().pop().ifPresent((Card c) -> revealPile.append(c));
                            discardMatch = true;
                        }
                    }
                }
            } while (discardMatch);
        }
        return result;
    }

}
