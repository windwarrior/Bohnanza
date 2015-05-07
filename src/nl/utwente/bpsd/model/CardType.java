package nl.utwente.bpsd.model;

import java.util.Map;

public class CardType {
  private final String typeName;
  private final int numberOfType;
  private final Map<Integer, Integer> beanOMeter;
  /**
  * Creates a CardType with a given name and a map between number of beans and value
  */
  public CardType(final String typeName, final Map<Integer, Integer> beanOMeter) {
    //NYI
    this.typeName = typeName;
    this.numberOfType = 0;
    this.beanOMeter = beanOMeter;
  }

  public String getTypeName()  {
    return this.typeName;
  }

  public Map<Integer, Integer> getBeanOMeter() {
    return this.beanOMeter;
  }
}
