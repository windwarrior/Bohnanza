package nl.utwente.bpsd.model.pile;

import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;

/**
 *
 * @author lennart
 */
public class HarvestablePile extends Pile {
    private final Pile treasury;
    private final Pile discard;
    
    /**
     * Create a new HarvestablePile with a treasury and discard pile to loot to
     * @param treasury 
     * @param discard 
     */
    public HarvestablePile(Pile treasury, Pile discard) {
        this.treasury = treasury;
        this.discard = discard;
    }
    
    /**
     * Harvests this pile into its treasury
     * 
     * Since this single field has no relationship to the other two fields, 
     * we cannot check if a single card field can be harvested, so just assume 
     * it to be checked.
     * @return true if a harvest was done
     */
    public boolean harvest() {
        if (this.pileSize() == 0)
           return false; // short, an empty field cannot be harvested
        
        // First, we will seek the closest amount of beens
        CardType c = this.peek().get(); // we know for sure there is a card
        boolean found = false;
        int amountOfCards = this.pileSize();
        
        while (found = amountOfCards > 0 && (c.getBeanOMeter().get(amountOfCards + 1) != null)) {
            amountOfCards--;
        };
        
        // in the variable amountOfCards we now have the amount of cards to harvest
        
        int amountOfCardsToDiscard = (this.pileSize()) - amountOfCards;
        
        while(amountOfCards >= 0) {
            this.pop().ifPresent(x -> this.treasury.append(x));
            amountOfCards--;
        }
        
        boolean iterate = true;
        
        while(iterate) {
            this.pop().map(x -> { discard.append(x); return true; }).orElse(iterate = false);
        }
        
        return true;
    }

    public int isWorth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
