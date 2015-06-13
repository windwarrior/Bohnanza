package nl.utwente.bpsd.impl.mafia.command;

public class MafiaPlantFromHandToFieldCommand extends MafiaGameCommand {

    private int fieldIndex;

    public void setFieldIndex(int index){
        this.fieldIndex = index;
    }
}
