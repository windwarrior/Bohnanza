package nl.utwente.bpsd.model;

/**
 * This class will model a bohnanza card based on a card type
 */
public class Card{
  private final CardType cardType;
  //Distinguishes cards between the same CardTypes
  private final int cardNumber;

  public Card(final CardType ct, final int n) {
      this.cardType = ct;
      this.cardNumber = n;
  }

  public CardType getCardType() {
    return this.cardType;
  }

  public int getCardNumber() {
    return this.cardNumber;
  }

  @Override
  public String toString(){
    return String.format("Bean of type %s with number %d", cardType.getTypeName(), cardNumber);
  }

  @Override
  public boolean equals(Object other){
    boolean result = false;
    if(other instanceof Card){
      result = (this.cardType.equals(((Card)other).getCardType()) && this.cardNumber==((Card)other).getCardNumber());
    }
    return result;
  }

    @Override
    public int hashCode() {
      int result = cardType.hashCode();
      result = 31 * result + cardNumber;
      return result;
    }
}
