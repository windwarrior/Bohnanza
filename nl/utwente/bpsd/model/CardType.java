package nl.utwente.bpsd.model;

public class CardType {
  private final String typeName;
  private final int numberOfType;
  private final Map<Integer, Integer> beanOMeter;
  /**
  * Creates a CardType with a given name and a map between number of beans and value
  */
  public CardType(final String typeName, final Map<Integer, Integer> beanOMeter) {
    //NYI
  }

  public String getTypeName()  {
    return this.typeName;
  }

  public Map<Integer, Integer> getValueMap() {
    return this.beanOMeter;
  }
}
