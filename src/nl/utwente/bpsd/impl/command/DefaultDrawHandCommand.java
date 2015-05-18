package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.model.pile.Pile;

public class DefaultDrawHandCommand extends DefaultGameCommand {

    public static final int DRAW_HAND_AMOUNT = 3;

    /**
     * Draw three cards from Game g gamePile into Player player hand
     */
    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        DefaultGame game = (DefaultGame) g; // Cast it because it is now indeed a DefaultGame
        DefaultPlayer player = (DefaultPlayer) p;

        GameCommandResult result = DefaultGameCommandResult.RESHUFFLE;

        Pile gamePile = game.getGamePile();
        if(gamePile.pileSize() > DRAW_HAND_AMOUNT) {
            for (int i = 0; i < DRAW_HAND_AMOUNT && result != DefaultGameCommandResult.INVALID; i++) {
                result = gamePile.pop().map((Card c) -> {
                    player.getHand().append(c);
                    return DefaultGameCommandResult.DRAWN_TO_HAND;
                }).orElse(DefaultGameCommandResult.INVALID);
            }
        }
        return result;
    }
    
}
