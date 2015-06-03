package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.List;
import java.util.TreeMap;
import nl.utwente.bpsd.impl.standard.StandardGame;

public class StandardHarvestCommand extends StandardGameCommand {

    int fieldIndex;

    /**
     * Sells beans from the player's selected bean field
     *
     * @requires this.player != null this.fieldIndex != null && g != null;
     */
    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        StandardGame game = (StandardGame) g; // Cast it because it is now indeed a StandardGame
        StandardPlayer player = (StandardPlayer) p;

        // TODO: checking if this.fieldIndex is in range and this.player != null this.fieldIndex != null && g != null
        if(player.getAllFields().size() <= fieldIndex || fieldIndex < 0) return StandardGameCommandResult.INVALID;
        List<Pile> fields = player.getAllFields();
        Pile field = fields.get(fieldIndex);

        /*
         * Player can sell from any bean field where are at least 2 cards
         * Field with single card can be sell only when all his bean fields have just one card
         */
        boolean singleCard = true;
        if (field.pileSize() == 1) {
            for (int i = 0; i < fields.size() && singleCard; ++i) {
                singleCard = false;
                if (fields.get(i).pileSize() == 1) {
                    singleCard = true;
                }
            }
        }

        if (field.peek().isPresent() && singleCard) {
            int fieldSize = field.pileSize();
            TreeMap<Integer, Integer> beanOMeter = new TreeMap<Integer, Integer>(field.peek().get().getCardType().getBeanOMeter());

            int earnedCoins = 0;
            if(field.pileSize() > 1) {
                Integer previousKey = beanOMeter.firstKey();
                for (Integer key : beanOMeter.keySet()) {
                    if (key <= fieldSize) {
                        previousKey = key;
                    }
                }
                earnedCoins = beanOMeter.get(previousKey);
            }

            int numberOfDiscarded = fieldSize - earnedCoins;
            for (int i = numberOfDiscarded; i > 0; --i) {
                // TODO do something with this result
                StandardGameCommandResult res = field.pop().map((Card x) -> {
                    game.getDiscardPile().append(x);
                    return StandardGameCommandResult.HARVEST;
                }).orElse(StandardGameCommandResult.INVALID);
            }

            for (int i = earnedCoins; i > 0; --i) {
                // TODO do something with this result
                StandardGameCommandResult res = field.pop().map((Card x) -> {
                    player.getTreasury().append(x);
                    return StandardGameCommandResult.HARVEST;
                }).orElse(StandardGameCommandResult.INVALID);
            }

            return StandardGameCommandResult.HARVEST;
        } else {
            return StandardGameCommandResult.INVALID;
        }

    }

    public void setFieldIndex(int index) {
        this.fieldIndex = index;
    }

}
