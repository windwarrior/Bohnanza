package nl.utwente.bpsd.impl.standard.command;

import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.StandardPlayer;
import nl.utwente.bpsd.model.pile.Pile;

public class StandardDrawTradeCommand extends StandardGameCommand {

    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        StandardGame game = (StandardGame) g; // Cast it because it is now indeed a StandardGame
        StandardPlayer player = (StandardPlayer) p;

        GameCommandResult result = StandardGameCommandResult.RESHUFFLE;

        Pile gamePile = game.getGamePile();
        if(gamePile.pileSize() > StandardGame.DRAW_TRADING_AMOUNT) {
            for (int i = 0; i < StandardGame.DRAW_TRADING_AMOUNT && result != StandardGameCommandResult.INVALID; i++) {
                result = gamePile.pop().map((Card c) -> {
                    player.getTrading().append(c);
                    return StandardGameCommandResult.DRAWN_TO_TRADING;
                }).orElse(StandardGameCommandResult.INVALID);
            }
        }
        return result;
    }
}
