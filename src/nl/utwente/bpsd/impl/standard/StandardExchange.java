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
        this.firstSideState = SideState.IDLE;
        this.secondSideState = SideState.IDLE;
    }

    public SideState getFirstSideState() {return firstSideState;}
    public SideState getSecondSideState() {return secondSideState;}

    @Override
    public boolean isPlayerInExchange(Player player) {
        return firstSideName.equals(((StandardPlayer)player).getName()) ||
                secondSideName.equals(((StandardPlayer)player).getName());
    }

    @Override
    public Exchange.SideState getSideState(Player player) {
        SideState res = null;
        if (((StandardPlayer)player).getName().equals(firstSideName))
            res = firstSideState;
        if (((StandardPlayer)player).getName().equals(secondSideName))
            res = secondSideState;
        return res;
    }

    public void setSideState(Player p, SideState newState) {
        if (firstSideName.equals(((StandardPlayer) p).getName()))
            this.firstSideState = newState;
        else if (secondSideName.equals(((StandardPlayer)p).getName()))
            this.secondSideState = newState;
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

    @Override
    public boolean isStarted(){
        //both exchange sides need to be in NEGOTIATING state
        return firstSideState == SideState.NEGOTIATING
                && secondSideState == SideState.NEGOTIATING;
    }

    @Override
    public boolean isAccepted(){
        //both exchange sides have already accepted exchange -> both sides are in ACCEPTING state
        return firstSideState == SideState.ACCEPTING
                && secondSideState == SideState.ACCEPTING;
    }

    @Override
    public boolean isDeclined(){
        //if any of exchange sides declined it -> is in DECLINING state
        return firstSideState == SideState.DECLINING
                || secondSideState == SideState.DECLINING;
    }

    @Override
    public boolean isFinished(){
        //both sides accepted the exchange or one of them declined it
        return isAccepted() || isDeclined();
    }
}
