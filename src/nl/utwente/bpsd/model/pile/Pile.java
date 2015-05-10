package nl.utwente.bpsd.model.pile;

import java.util.ArrayList;
import java.util.List;
import nl.utwente.bpsd.model.Card;

public class Pile {
    private List<Card> cardList;

    public Pile(){
        this.cardList = new ArrayList<>();
    }

    public Pile(List<Card> cardList ) {
        this.cardList = cardList;
    }

    /**
     * Used to make a copy a Pile (used for testing)
     * @param p - Pile to be copied
     */
    public Pile(Pile p){
        cardList = new ArrayList<>();
        for(Card c : p.getCardList())
            cardList.add(c);
    }

    /**
    * Add a card to this pile, last in first out.
    */
    public void append(Card c) {
        cardList.add(c);
    }

    /**
    * Take the top card of this pile.
    */
    public Card pop() {
        return cardList.remove(0);
    }

    /**
    * Look a the top card without removing it.
    */
    public Card peek() {
      return cardList.get(0);
    }

    /**
     * Get this size of the pile.
     */
    public int pileSize(){
        return cardList.size();
    }

    protected List<Card> getCardList(){
        return cardList;
    }


}
