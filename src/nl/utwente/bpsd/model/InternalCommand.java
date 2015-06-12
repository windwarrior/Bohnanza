package nl.utwente.bpsd.model;

/**
 * InternalCommand is a command that can be invoked internally
 * This interface itself does not (yet) provide additional methods, but it 
 * does force a new type so that we can check with `instanceof`.
 * @author lennart
 */
public interface InternalCommand extends Command {
    
}
