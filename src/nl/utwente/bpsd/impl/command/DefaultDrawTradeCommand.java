package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.pile.Pile;

public class DefaultDrawTradeCommand extends DefaultGameCommand {

    public static final int DRAW_TRADING_AMOUNT = 2;

    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        DefaultGame game = (DefaultGame) g; // Cast it because it is now indeed a DefaultGame
        DefaultPlayer player = (DefaultPlayer) p;

        GameCommandResult result = DefaultGameCommandResult.RESHUFFLE;

        Pile gamePile = game.getGamePile();
        if(gamePile.pileSize() > DRAW_TRADING_AMOUNT) {
            for (int i = 0; i < DRAW_TRADING_AMOUNT && result != DefaultGameCommandResult.INVALID; i++) {
                result = gamePile.pop().map((Card c) -> {
                    player.getTrading().append(c);
                    return DefaultGameCommandResult.DRAWN_TO_TRADING;
                }).orElse(DefaultGameCommandResult.INVALID);
            }
        }
        return result;
    }
}
