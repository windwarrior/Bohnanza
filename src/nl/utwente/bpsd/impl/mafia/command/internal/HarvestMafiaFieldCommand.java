package nl.utwente.bpsd.impl.mafia.command.internal;

import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.command.MafiaGameCommand;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.InternalCommand;
import nl.utwente.bpsd.model.Player;

/**
 *
 * @author lennart
 */
public class HarvestMafiaFieldCommand extends MafiaGameCommand implements InternalCommand {

    @Override
    public GameCommandResult execute(Player player, Game game) {
        super.execute(player, game); // First lets force player and game to be of the Mafia type
        MafiaGame mafiaGame = (MafiaGame) game;
                
        mafiaGame.getMafia().stream().forEach((boss) -> {
            if (boss.getPile().isWorth() >= boss.getCoinConditionToHarvest()) {
                boss.getPile().harvest();
            }
        });
        
        return StandardGameCommandResult.PROGRESS;
    }
    
}
