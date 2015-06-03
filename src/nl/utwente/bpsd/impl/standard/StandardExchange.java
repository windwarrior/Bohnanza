package nl.utwente.bpsd.impl.standard;

import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.Exchange;
import nl.utwente.bpsd.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes an exchange in standard game.
 * Players are distinguished by their names - names should be unique!
 */
public class StandardExchange implements Exchange {

    /**
     * Name of the player that suggested exchange
     */
    private final String firstSideName;
    /**
     * Name of the player that was invited to exchange
     */
    private final String secondSideName;

    private final List<Card> offeredByFirstSide = new ArrayList<>();
    private final List<Card> offeredBySecondSide = new ArrayList<>();

    /**
     * Current state in exchange of invitingSide Player
     */
    private SideState firstSideState;

    /**
     * Current state in exchange of invitedSide Player
     */
    private SideState secondSideState;

    public StandardExchange(Player first, Player second) {
        this.firstSideName = ((StandardPlayer)first).getName();
        this.secondSideName = ((StandardPlayer)second).getName();
    }

    public SideState getFirstSideState() {return firstSideState;}
    public SideState getSecondSideState() {return secondSideState;}
    public String getFirstSideName() {return firstSideName;}
    public String getSecondSideName() {return secondSideName;}

    @Override
    public boolean isPlayerInExchange(Player player) {
        return firstSideName.equals(((StandardPlayer)player).getName()) ||
                secondSideName.equals(((StandardPlayer)player).getName());
    }

    @Override
    public Exchange.SideState getPartyState(Player player) {
        SideState res = null;
        if (((StandardPlayer)player).getName().equals(firstSideName))
            res = firstSideState;
        if (((StandardPlayer)player).getName().equals(secondSideName))
            res = secondSideState;
        return res;
    }

    @Override
    public List<Card> getOfferedCards(Player p){
        List<Card> res = null;
        if (((StandardPlayer)p).getName().equals(firstSideName))
            res = offeredByFirstSide;
        if (((StandardPlayer)p).getName().equals(secondSideName))
            res = offeredBySecondSide;
        return res;
    }

    public boolean isStarted(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isStopped(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isAccepted(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isDeclined(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isFinished(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
