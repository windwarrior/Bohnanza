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

  @Override
  public boolean equals(Object other){
      boolean result = false;
      if(other instanceof CardType){
          result = this.typeName.equals(((CardType) other).getTypeName());
      }
      return result;
  }

    @Override
    public String toString(){
        return String.format("CardType: %s, NumberOfType: %d, BeanOMeter: %s",typeName,numberOfType,beanOMeter.toString());
    }
}
