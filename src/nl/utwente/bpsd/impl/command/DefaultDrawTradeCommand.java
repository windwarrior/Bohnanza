package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.pile.Pile;

public class DefaultDrawTradeCommand extends DefaultGameCommand {

    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a defaultgame
        DefaultGame game = (DefaultGame) g; // Cast it because it is now indeed a DefaultGame
        DefaultPlayer player = (DefaultPlayer) p;

        Pile gamePile = game.getGamePile();
        if(gamePile.pileSize() < 2) {
            // TODO: Implement reshuffle functionality
            return DefaultGameCommandResult.FINISHED;
        }
        Card firstDraw = gamePile.pop().get();
        Card secondDraw = gamePile.pop().get();
        player.getTrading().append(firstDraw);
        player.getTrading().append(secondDraw);
        return DefaultGameCommandResult.DRAWN_TO_TRADING;
    }
}
