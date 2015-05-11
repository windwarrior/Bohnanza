package nl.utwente.bpsd.impl.command;

import nl.utwente.bpsd.impl.DefaultGame;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.impl.DefaultGameCommandResult;
import nl.utwente.bpsd.impl.DefaultPlayer;
import nl.utwente.bpsd.model.pile.Pile;
import nl.utwente.bpsd.model.Command;
import nl.utwente.bpsd.model.GameCommandResult;

public class DefaultDrawTradeCommand extends DefaultGameCommand {

    DefaultPlayer player;

    @Override
    public GameCommandResult execute(Game game) {
        super.execute(game); // force a check that this is indeed a defaultgame
        DefaultGame dg = (DefaultGame) game; // Cast it because it is now indeed a DefaultGame
        
        Pile gamePile = dg.getGamePile();
        if(gamePile.pileSize() < 2) {
            // TODO: Implement reshuffle functionality
            return DefaultGameCommandResult.FINISHED;
        }
        Card firstDraw = gamePile.pop();
        Card secondDraw = gamePile.pop();
        player.getTrading().append(firstDraw);
        player.getTrading().append(secondDraw);
        return DefaultGameCommandResult.DRAWN_TO_TRADING;
    }

    public void setPlayer(DefaultPlayer player){
        this.player = player;
    }
}
