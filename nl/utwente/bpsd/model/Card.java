package nl.utwente.bpsd.model;

public class Card {
  private final CardType cardType;

  public Card(final CardType ct) {
      this.cardType = ct;
  }

  public CardType getCardType() {
    return this.cardType;
  }
}
