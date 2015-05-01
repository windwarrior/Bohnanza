/**
 * in this class we have a method which allows a card to be taken from the middle of the hand
 */
public class HandPile extends Pile{

    /**
     * returns the card at index index from the pile, this card is removed from the pile
     */
    public Card getCard(int index){}

    /**
     * returns the CardType of the card at index index (the card is not removed for the hand)
     * this method might be important during the trading phase
     */
    public CardType getCardType(int index){}
}