package nl.utwente.bpsd.impl.mafia;

import nl.utwente.bpsd.impl.mafia.command.*;
import nl.utwente.bpsd.impl.standard.StandardPlayer;

public class MafiaPlayer extends StandardPlayer {

    public MafiaPlayer(String name) {
        super(name);
    }

    public boolean plantFromHandToMafia(){
        MafiaPlantFromHandToMafiaCommand dc = new MafiaPlantFromHandToMafiaCommand();
        return super.executeCommand(dc);
    }

    public boolean plantFromRevealToMafia(){
        MafiaPlantFromRevealToMafiaCommand dc = new MafiaPlantFromRevealToMafiaCommand();
        return super.executeCommand(dc);
    }

    public boolean plantFromRevealToField(int fieldIndex, int revealIndex){
        MafiaPlantFromRevealToFieldCommand dc = new MafiaPlantFromRevealToFieldCommand();
        dc.setFieldIndex(fieldIndex);
        dc.setRevealIndex(revealIndex);
        return super.executeCommand(dc);
    }

    public boolean plantFromHandToField(int fieldIndex){
        MafiaPlantFromHandToFieldCommand dc = new MafiaPlantFromHandToFieldCommand();
        dc.setFieldIndex(fieldIndex);
        return super.executeCommand(dc);
    }
}
