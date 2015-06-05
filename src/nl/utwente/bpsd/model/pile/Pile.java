package nl.utwente.bpsd.model.pile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;

public class Pile {
    private List<Card> cardList;

    public Pile(){
        this.cardList = new ArrayList<>();
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
    * Add a card to the bottom of this pile, first in first out.
    */
    public void append(Card c) {
        cardList.add(c);
    }

    /**
    * Take the top card of this pile.
     * @requires !this.cardList.size().empty();
    */
    public Optional<Card> pop() {
        return cardList.size() > 0 ? Optional.of(cardList.remove(0)) : Optional.empty();
    }

    /**
    * Look at the top card without removing it.
    */
    public Optional<CardType> peek() {
      return cardList.size() > 0 ? Optional.of(cardList.get(0).getCardType()) : Optional.empty();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pile pile = (Pile) o;

        return getCardList().equals(pile.getCardList());

    }

    @Override
    public int hashCode() {
        return getCardList().hashCode();
    }

    @Override
    public String toString() {
        return "Pile{" +
                "cardList=" + cardList +
                '}';
    }
}
