package nl.utwente.bpsd.model.pile;

import java.util.List;
import java.util.Optional;
import nl.utwente.bpsd.model.Card;
import nl.utwente.bpsd.model.CardType;

/**
 * in this class we have a method which allows a card to be taken from the middle of the hand
 */
public class HandPile extends Pile{

    public HandPile(){super();}

    /**
     * returns the card at index index from the pile, this card is removed from the pile
     */
    public Optional<Card> getCard(int index){
        Optional<Card> result = Optional.empty();
        if(0 <= index && index < getCardList().size()){
            result = Optional.of(getCardList().get(index));
            getCardList().remove(index);
        }
        return result;
    }

    /**
     * returns the CardType of the card at index index (the card is not removed for the hand)
     * this method might be important during the trading phase
     */
    public Optional<CardType> getCardType(int index){
        Optional<CardType> result = Optional.empty();
        if(0 <= index && index < getCardList().size())
            result = Optional.of(getCardList().get(index).getCardType());
        return result;
    }
}