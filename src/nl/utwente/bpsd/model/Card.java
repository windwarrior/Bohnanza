package nl.utwente.bpsd.model;

/**
 * This class will model a bohnanza card based on a card type
 */
public class Card{
  private final CardType cardType;

  public Card(final CardType ct) {
      this.cardType = ct;
  }

  public CardType getCardType() {
    return this.cardType;
  }


  @Override
  public String toString(){
    return String.format("Bean of type %s", cardType.getTypeName());
  }

  @Override
  public boolean equals(Object other){
    boolean result = false;
    if(other instanceof Card){
      result = this.cardType.equals(((Card)other).getCardType());
    }
    return result;
  }

    @Override
    public int hashCode() {
        return cardType.hashCode();
    }
}
