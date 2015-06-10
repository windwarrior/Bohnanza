package nl.utwente.bpsd.impl.mafia;

import nl.utwente.bpsd.impl.mafia.command.MafiaPlanFromHandToMafiaCommand;
import nl.utwente.bpsd.impl.mafia.command.MafiaPlantFromRevealToFieldCommand;
import nl.utwente.bpsd.impl.mafia.command.MafiaPlantFromRevealToMafiaCommand;
import nl.utwente.bpsd.impl.standard.StandardPlayer;

public class MafiaPlayer extends StandardPlayer {

    public MafiaPlayer(String name) {
        super(name);
    }

    public boolean plantFromHandToMafia(){
        MafiaPlanFromHandToMafiaCommand dc = new MafiaPlanFromHandToMafiaCommand();
        return super.executeCommand(dc);
    }

    public boolean plantFromRevealToMafia(){
        MafiaPlantFromRevealToMafiaCommand dc = new MafiaPlantFromRevealToMafiaCommand();
        return super.executeCommand(dc);
    }

    public boolean plantFromRevealToField(){
        MafiaPlantFromRevealToFieldCommand dc = new MafiaPlantFromRevealToFieldCommand();
        return super.executeCommand(dc);
    }
}
