package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaBoss;
import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;

import java.util.ArrayList;
import java.util.List;
import nl.utwente.bpsd.model.pile.HarvestablePile;

public class MafiaPlantFromRevealToMafiaCommand extends MafiaPlantFromRevealToFieldCommand {

    @Override
    protected List<HarvestablePile> getFields(MafiaPlayer player, MafiaGame game){
        List<HarvestablePile> mafiaFields = new ArrayList<>();
        for (MafiaBoss mafiaBoss : game.getMafia())
            mafiaFields.add(mafiaBoss.getPile());
        return mafiaFields;
    }
}
