package nl.utwente.bpsd.impl.mafia;

import nl.utwente.bpsd.impl.mafia.command.*;
import nl.utwente.bpsd.impl.standard.StandardGame;
import nl.utwente.bpsd.impl.standard.StandardGameCommandResult;
import nl.utwente.bpsd.impl.standard.command.*;
import nl.utwente.bpsd.model.*;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.*;
import nl.utwente.bpsd.model.pile.HarvestablePile;
import nl.utwente.bpsd.model.state.State;
import nl.utwente.bpsd.model.state.StateManager;

public class MafiaGame extends StandardGame{

    public static final int NUM_REVEAL_PILES = 3;

    private ArrayList<MafiaBoss> mafia;
    private Pile mafiaTreasury;
    private ArrayList<Pile> revealArray;

    public MafiaGame(){
        super();
    }

    @Override
    public void initialize(){
        mafia = new ArrayList<>();
        //Order in game: "Al Cabohne", "Don Corlebohne" and optionally "Joe Bohnano"
        
        MafiaBoss alCabone = new MafiaBoss("Al Cabone", new HarvestablePile(this.getMafiaTreasury(), this.getDiscardPile()), 3);
        MafiaBoss donCorlebohne = new MafiaBoss("Don Corlebohne", new HarvestablePile(this.getMafiaTreasury(), this.getDiscardPile()), 2);
        MafiaBoss joeBohnano = new MafiaBoss("Joe Bohnano", new HarvestablePile(this.getMafiaTreasury(), this.getDiscardPile()), 1);
        
        
        this.mafia.add(alCabone);
        this.mafia.add(donCorlebohne);
        
        for (Player p : this.getPlayers()) {
            for (int i = 0; i < 3; i++) {
                p.addField(new HarvestablePile(p.getTreasury(), this.getDiscardPile()));
            }
        }
        
        if (this.getPlayers().size() == 1) {
            this.mafia.add(joeBohnano);
            this.number_start_cards = 7;
            this.draw_hand_amount = 2;
            //Add third field to solo player
            ((MafiaPlayer)this.getPlayers().get(0)).getAllFields().add(new HarvestablePile(this.getPlayers().get(0).getTreasury(), this.getDiscardPile()));
        }
        revealArray = new ArrayList<>();
        for (int i = 0; i < this.NUM_REVEAL_PILES; i++) {
            revealArray.add(new Pile());
        }
        mafiaTreasury = new Pile();
        this.setDiscardPile(new Pile());
        this.generateGameDeck();
        this.setupPhase();
        this.generateStateManager();
    }

    private void setupPhase(){
        //Players get cards
        for(Player p:getPlayers()){
            for (int i = 0; i < number_start_cards; i++) {
                getGamePile().pop().ifPresent((Card c) -> ((MafiaPlayer)p).getHand().append(c));
            }
        }

        do {
            getGamePile().pop().ifPresent((Card c) -> mafia.get(0).getPile().append(c));
        } while (getGamePile().peek().equals(mafia.get(0).getPile().peek()));
        getGamePile().pop().ifPresent((Card c) -> mafia.get(1).getPile().append(c));
        if(this.getPlayers().size() == 1){
            while (mafia.get(2).getPile().pileSize() == 0) {
                Optional<CardType> topDeck = this.getGamePile().peek();
                boolean added = false;
                for(int i=0; i < mafia.size()-1 && !added; i++) {
                    if (topDeck.equals(mafia.get(i).getPile().peek())) {
                        Pile mafiaPile = mafia.get(i).getPile();
                        getGamePile().pop().ifPresent((Card c) -> mafiaPile.append(c));
                        added = true;
                    }
                }
                if(!added)  getGamePile().pop().ifPresent((Card c) -> mafia.get(2).getPile().append(c));
            }
        }
    }

    private void generateStateManager(){
        boolean onePlayer = this.getPlayers().size() == 1;
        //ALL STATES
        State<GameCommandResult, Class<? extends Command>> phaseThreeA = new State<>("Phase 3a", MafiaPlantFromHandToFieldCommand.class, StandardBuyFieldCommand.class, StandardHarvestCommand.class);
        State<GameCommandResult, Class<? extends Command>> phaseThreeB = new State<>("Phase 3b", StandardSkipCommand.class, MafiaPlantFromHandToFieldCommand.class, StandardBuyFieldCommand.class, StandardHarvestCommand.class);
        State<GameCommandResult, Class<? extends Command>> phaseFour = new State<>("Phase 4", MafiaDrawCardsToRevealCommand.class, StandardBuyFieldCommand.class);
        State<GameCommandResult, Class<? extends Command>> phaseFive = new State<>("Phase 5", StandardBuyFieldCommand.class, StandardHarvestCommand.class,
                MafiaPlantFromRevealToFieldCommand.class, MafiaPlantFromRevealToMafiaCommand.class, MafiaPlantFromHandToMafiaCommand.class,
                MafiaSkipToPhaseSixCommand.class);
        State<GameCommandResult, Class<? extends Command>> phaseSix = new State<>("Phase 6", StandardDrawHandCommand.class, StandardBuyFieldCommand.class, StandardHarvestCommand.class);
        //ONE PLAYER STATE
        State<GameCommandResult, Class<? extends Command>> phaseOne1Player = new State<>("Phase 1", MafiaGiveBeansToMafiaCommand.class, StandardBuyFieldCommand.class, StandardHarvestCommand.class);
        //TWO PLAYER STATE
        State<GameCommandResult, Class<? extends Command>> phaseOne2Player = new State<>("Phase 1", MafiaPlantFromRevealToFieldCommand.class, MafiaGiveBeansToMafiaCommand.class, StandardBuyFieldCommand.class, StandardHarvestCommand.class);


        //GENERAL TRANSITIONS
        phaseThreeA.addTransition(StandardGameCommandResult.HARVEST,phaseThreeA);
        phaseThreeA.addTransition(StandardGameCommandResult.BOUGHT_FIELD,phaseThreeA);
        phaseThreeA.addTransition(MafiaGameCommandResult.PLANT_HAND_FIELD,phaseThreeB);

        phaseThreeB.addTransition(StandardGameCommandResult.HARVEST,phaseThreeB);
        phaseThreeB.addTransition(StandardGameCommandResult.BOUGHT_FIELD,phaseThreeB);
        phaseThreeB.addTransition(MafiaGameCommandResult.PLANT_HAND_FIELD,phaseFour);
        phaseThreeB.addTransition(StandardGameCommandResult.SKIP,phaseFour);

        phaseFour.addTransition(StandardGameCommandResult.BOUGHT_FIELD,phaseFour);
        phaseFour.addTransition(MafiaGameCommandResult.DRAW_REVEAL,phaseFive);

        phaseFive.addTransition(StandardGameCommandResult.HARVEST,phaseFive);
        phaseFive.addTransition(StandardGameCommandResult.BOUGHT_FIELD,phaseFive);
        phaseFive.addTransition(MafiaGameCommandResult.PLANT_HAND_MAFIA,phaseFive);
        phaseFive.addTransition(MafiaGameCommandResult.PLANT_REVEAL,phaseFive);
        phaseFive.addTransition(MafiaGameCommandResult.SKIP_TO_PHASE_SIX,phaseSix);

        phaseSix.addTransition(StandardGameCommandResult.HARVEST,phaseSix);
        phaseSix.addTransition(StandardGameCommandResult.BOUGHT_FIELD,phaseSix);

        //1PLAYER SPECIFIC TRANSITIONS
        if(onePlayer) {
            phaseOne1Player.addTransition(MafiaGameCommandResult.GIVE_BEANS,phaseThreeA);
            phaseOne1Player.addTransition(StandardGameCommandResult.HARVEST,phaseOne1Player);
            phaseOne1Player.addTransition(StandardGameCommandResult.BOUGHT_FIELD, phaseOne1Player);

            phaseSix.addTransition(StandardGameCommandResult.DRAWN_TO_HAND, phaseOne1Player);
        }
        //2PLAYER SPECIFIC TRANSITIONS
        else {
            phaseOne2Player.addTransition(MafiaGameCommandResult.GIVE_BEANS,phaseThreeA);
            phaseOne2Player.addTransition(StandardGameCommandResult.HARVEST,phaseOne2Player);
            phaseOne2Player.addTransition(StandardGameCommandResult.BOUGHT_FIELD, phaseOne2Player);
            phaseOne2Player.addTransition(MafiaGameCommandResult.PLANT_REVEAL,phaseOne2Player);

            phaseSix.addTransition(StandardGameCommandResult.DRAWN_TO_HAND, phaseOne2Player);
        }

        StateManager<GameCommandResult, Class<? extends Command>> sm = new StateManager<>(phaseThreeA);
        this.setStateManager(sm);
    }

    private void generateGameDeck() {
        List<CardType> allCardType = new ArrayList<>();

        Map<Integer, Integer> blueBeanOMeter = new HashMap<>();
        blueBeanOMeter.put(4, 1);
        blueBeanOMeter.put(6, 2);
        blueBeanOMeter.put(8, 3);
        blueBeanOMeter.put(10, 4);
        CardType blueBean = new CardType("Blue Bean", blueBeanOMeter, 20);
        allCardType.add(blueBean);

        Map<Integer, Integer> kidneyBeanOMeter = new HashMap<>();
        kidneyBeanOMeter.put(3, 1);
        kidneyBeanOMeter.put(6, 2);
        kidneyBeanOMeter.put(7, 3);
        kidneyBeanOMeter.put(8, 4);
        CardType kidneyBean = new CardType("Kidney Bean", kidneyBeanOMeter, 19);
        allCardType.add(kidneyBean);

        Map<Integer, Integer> fireBeanOMeter = new HashMap<>();
        fireBeanOMeter.put(3, 1);
        fireBeanOMeter.put(6, 2);
        fireBeanOMeter.put(8, 3);
        fireBeanOMeter.put(9, 4);
        CardType fireBean = new CardType("Fire Bean", fireBeanOMeter, 18);
        allCardType.add(fireBean);

        Map<Integer, Integer> puffBeanOMeter = new HashMap<>();
        puffBeanOMeter.put(4, 1);
        puffBeanOMeter.put(5, 2);
        puffBeanOMeter.put(6, 3);
        puffBeanOMeter.put(7, 4);
        CardType puffBean = new CardType("Puff Bean", puffBeanOMeter, 16);
        allCardType.add(puffBean);

        Map<Integer,Integer> broadBeanOMeter = new HashMap<>();
        broadBeanOMeter.put(3, 1);
        broadBeanOMeter.put(5, 2);
        broadBeanOMeter.put(7, 3);
        broadBeanOMeter.put(8, 4);
        CardType broadBean = new CardType("Broad Bean", broadBeanOMeter, 16);
        allCardType.add(broadBean);

        Map<Integer, Integer> frenchBeanOMeter = new HashMap<>();
        frenchBeanOMeter.put(3, 1);
        frenchBeanOMeter.put(5, 2);
        frenchBeanOMeter.put(6, 3);
        frenchBeanOMeter.put(7, 4);
        CardType frenchBean = new CardType("French Bean", frenchBeanOMeter, 14);
        allCardType.add(frenchBean);

        Map<Integer,Integer> runnerBeanOMeter = new HashMap<>();
        runnerBeanOMeter.put(3, 1);
        runnerBeanOMeter.put(4, 2);
        runnerBeanOMeter.put(5, 3);
        runnerBeanOMeter.put(6, 4);
        CardType runnerBean = new CardType("Runner Bean", runnerBeanOMeter, 13);
        allCardType.add(runnerBean);

        List<Card> allCards = new ArrayList<>();
        for (CardType ct : allCardType) {
            for (int i = 0; i < ct.getNumberOfType(); i++) {
                allCards.add(new Card(ct,i));
            }
        }

        Collections.shuffle(allCards);
        Pile gameCards = new Pile();
        for(Card c : allCards) gameCards.append(c);
        this.setGamePile(gameCards);
    }

    public ArrayList<Pile> getRevealArray() { return revealArray; }

    public ArrayList<MafiaBoss> getMafia() { return mafia; }

    public Pile getMafiaTreasury() { return mafiaTreasury; }

}
