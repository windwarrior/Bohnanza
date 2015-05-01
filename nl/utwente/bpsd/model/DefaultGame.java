public class DefaultGame implements Game{

    private List<Player> players;
    private Pile discardPile;
    private Pile gamePile;
    private GameState currentTurnState;
    private Player currentPlayer;
    private int reshuffleCounter;

    public DefaultGame(){

    }

    enum GameState{
        PREPERATION;
        PLAY;
        END;
        MUST_PLANT_FROM_HAND;
        MAY_PLANT_FROM_HAND;
        DRAW_FOR_TRADING;
        TRADE;
        PLANT_FROM_TRADING;
        DRAW_FOR_HAND;
    }
}