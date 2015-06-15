package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaBoss;
import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.ArrayList;
import java.util.List;

public class MafiaPlantFromRevealToMafiaCommand extends MafiaPlantFromRevealToFieldCommand {

    @Override
    protected List<Pile> getFields(MafiaPlayer player, MafiaGame game){
        List<Pile> mafiaFields = new ArrayList<>();
        for (MafiaBoss mafiaBoss : game.getMafia())
            mafiaFields.add(mafiaBoss.getPile());
        return mafiaFields;
    }
}
