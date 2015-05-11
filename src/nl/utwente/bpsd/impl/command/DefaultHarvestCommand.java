package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.Command;

import java.util.List;
import java.util.TreeMap;
import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.GameCommandResult;

public class DefaultHarvestCommand extends DefaultGameCommand {

    DefaultPlayer player;
    int fieldIndex;

    /**
     * Sells beans from the player's selected bean field
     *
     * @requires this.player != null this.fieldIndex != null && g != null;
     */
    @Override
    public GameCommandResult execute(Game game) {
        super.execute(game); // force a check that this is indeed a defaultgame
        DefaultGame dg = (DefaultGame) game; // Cast it because it is now indeed a DefaultGame

        // TODO: checking if this.fieldIndex is in range and this.player != null this.fieldIndex != null && g != null
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

            int earnedCoins;
            Integer previousKey = beanOMeter.firstKey();
            for (Integer key : beanOMeter.keySet()) {
                if (key <= fieldSize) {
                    previousKey = key;
                }
            }
            earnedCoins = beanOMeter.get(previousKey);

            int numberOfDiscarded = fieldSize - earnedCoins;
            for (int i = numberOfDiscarded; i > 0; --i) {
                // TODO do something with this result
                DefaultGameCommandResult res = field.pop().map((Card x) -> {
                    dg.getDiscardPile().append(x);
                    return DefaultGameCommandResult.HARVEST;
                }).orElse(DefaultGameCommandResult.INVALID);
            }

            for (int i = earnedCoins; i > 0; --i) {
                // TODO do something with this result
                DefaultGameCommandResult res = field.pop().map((Card x) -> {
                    player.getTreasury().append(x);
                    return DefaultGameCommandResult.HARVEST;
                }).orElse(DefaultGameCommandResult.INVALID);
            }

            return DefaultGameCommandResult.HARVEST;
        } else {
            //maybe other error handling?
            return DefaultGameCommandResult.INVALID;
        }

    }

    public void setPlayer(DefaultPlayer player) {
        this.player = player;
    }

    public void setFieldIndex(int index) {
        this.fieldIndex = index;
    }

}
