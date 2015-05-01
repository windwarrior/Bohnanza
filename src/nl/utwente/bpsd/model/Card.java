package nl.utwente.bpsd.model;

/**
 * This class will model a bohnanza card based on a card type
 */
public class Card {
  private final CardType cardType;

  public Card(final CardType ct) {
      this.cardType = ct;
  }

  public CardType getCardType() {
    return this.cardType;
  }
}
