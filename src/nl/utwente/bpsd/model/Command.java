package nl.utwente.bpsd.model;

public interface Command {
    public GameCommandResult execute(Player p, Game g);
}
