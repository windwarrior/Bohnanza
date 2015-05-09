package nl.utwente.bpsd.model;

import java.util.Map;

public class CardType {
  private final String typeName;
  private final int numberOfType;
  private final Map<Integer, Integer> beanOMeter;
  /**
  * Creates a CardType with a given name and a map between number of beans and value
  */
  public CardType(final String typeName, final Map<Integer, Integer> beanOMeter, final int numberOfType) {
    //NYI
    this.typeName = typeName;
    this.numberOfType = numberOfType;
    this.beanOMeter = beanOMeter;
  }

  public String getTypeName()  {
    return this.typeName;
  }

  public Map<Integer, Integer> getBeanOMeter() {
    return this.beanOMeter;
  }

  public int getNumberOfType() {
    return numberOfType;
  }
}
