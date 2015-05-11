package nl.utwente.bpsd.model;

public interface Command {
    public GameCommandResult execute(Game g);
}
