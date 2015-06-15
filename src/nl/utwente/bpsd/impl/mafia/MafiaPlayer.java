package nl.utwente.bpsd.impl.mafia;

import nl.utwente.bpsd.impl.mafia.command.*;
import nl.utwente.bpsd.impl.standard.StandardPlayer;

public class MafiaPlayer extends StandardPlayer {

    public MafiaPlayer(String name) {
        super(name);
    }

    public boolean plantFromHandToMafia(int fieldIndex, int handIndex){
        MafiaPlantFromHandToMafiaCommand dc = new MafiaPlantFromHandToMafiaCommand();
        dc.setFieldIndex(fieldIndex);
        dc.setHandIndex(handIndex);
        return super.executeCommand(dc);
    }

    public boolean plantFromRevealToMafia(int fieldIndex, int revealIndex){
        MafiaPlantFromRevealToMafiaCommand dc = new MafiaPlantFromRevealToMafiaCommand();
        dc.setFieldIndex(fieldIndex);
        dc.setRevealIndex(revealIndex);
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

    public boolean giveBeansToMafia(){
        MafiaGiveBeansToMafiaCommand dc = new MafiaGiveBeansToMafiaCommand ();
        return super.executeCommand(dc);
    }
}
