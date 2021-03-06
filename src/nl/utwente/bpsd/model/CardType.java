package nl.utwente.bpsd.model;

import java.util.Map;

public class CardType {
  private final String typeName;
  private final int numberOfType;
  private final Map<Integer, Integer> beanOMeter;
  /**
  * Creates a CardType with a given name, a map between number of beans and value, and number of bean cards in deck
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
          CardType ct = (CardType) other;
          result = this.typeName.equals(ct.getTypeName())
            && this.numberOfType == ct.getNumberOfType()
            && this.beanOMeter.equals(ct.getBeanOMeter());
      }
      return result;
  }

    @Override
    public int hashCode() {
        int result = typeName.hashCode();
        result = 31 * result + numberOfType;
        result = 31 * result + beanOMeter.hashCode();
        return result;
    }

    @Override
    public String toString(){
        return String.format("CardType: %s, NumberOfType: %d, BeanOMeter: %s",typeName,numberOfType,beanOMeter.toString());
    }
}
