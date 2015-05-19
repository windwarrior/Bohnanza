package nl.utwente.bpsd.model.pile;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import nl.utwente.bpsd.model.Card;

/**
 * In this pile it is possible to shuffle then empty the deck
 */
public class DiscardPile extends Pile{

    public DiscardPile() {
        super();
    }

    /**
     * Returns a new shuffled list of cards and empties this.cardList
     */
    public List<Card> shuffleRemove(){

        List<Card> result = new ArrayList<>(this.getCardList());
        //Collections.shuffle(result);
        /* Fisher–Yates shuffle:
         * going from the bottom of deck,
         * swap card with the pseudo randomly selected one from
         * the cards which are at that position or above in the deck
         */
        Random rand = new Random();
        int j;
        Card temp;
        for(int i = result.size()-1; i>0; --i)
        {
            j = rand.nextInt(i + 1);
            if(j != i) {
                temp = result.get(j);
                result.set(j, result.get(i));
                result.set(i, temp);
            }
        }
        this.getCardList().clear();
        return result;
    }
}