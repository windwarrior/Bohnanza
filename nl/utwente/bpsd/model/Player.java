package nl.utwente.bpsd.model;

public class Player {
    private String name;
    private List<Pile> fields;
    private Pile hand;
    private Pile treasury;
    private Pile trading;
    private Game game;

    // This is for possible expansions.
    private Map<String, Pile> additionalPiles;

    public Player(String name) {}

    public void drawIntoHand();

    public void drawIntoTrading();

    public void plant(Card card, int fieldIndex);

    //Still needs consideration
    public void trade();

    public void harvest(int fieldIndex);

    public void buyField();

    public Pile getHand() {}

    public Pile getTreasury() {}

    public Pile getTrading() {}

    public List<Pile> getAllFields() {}

    public void extendFields(){}

    //For possible extension with new Piles
    public Pile getAdditionalPileByName(String name) {}
}