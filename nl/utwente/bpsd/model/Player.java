package nl.utwente.bpsd.model;

public class Player {
  private String name;
  private List<Pile> fields;
  private Pile hand;
  private Pile treasury;
  private Pile trading;

  // This is for possible expansions.
  private Map<String, Pile> additionalPiles;

  public Player(String name) {}
  
  public Pile getHand() {}

  public Pile getTreasury() {}

  public Pile getTrading() {}

  public List<Pile> getAllFields() {}

  public void extendFields(){}

  public Pile getAdditionalPileByName(String name) {}
}
