package nl.utwente.bpsd.model.piles;

import java.util.List;
import nl.utwente.bpsd.model.Card;

/**
 * In this pile it is possible to shuffle then empty the deck
 */
public class DiscardPile extends Pile{

    public DiscardPile(List<Card> cardList) {
        super(cardList);
    }

    /**
     * Returns a new shuffled list of cards and empties this.cardList
     */
    public List<Card> shuffleRemove(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}