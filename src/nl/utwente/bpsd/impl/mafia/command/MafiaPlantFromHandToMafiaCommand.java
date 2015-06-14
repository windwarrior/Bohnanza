package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaGameCommandResult;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.HandPile;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.List;

public class MafiaPlantFromHandToMafiaCommand extends MafiaGameCommand{

    private int handIndex;
    private int fieldIndex;

    @Override
    public GameCommandResult execute(Player p, Game g){
        super.execute(p,g);
        MafiaGame game = (MafiaGame) g;
        MafiaPlayer player = (MafiaPlayer) p;

        List<MafiaBoss> fields = game.getMafia();
        HandPile hand = (HandPile) player.getHand();
        Pile field = fields.get(fieldIndex);

        if(fields.size() <= fieldIndex || fieldIndex < 0
                || hand.pileSize() <= handIndex || handIndex < 0)
            return MafiaGameCommandResult.INVALID;

        GameCommandResult result;

        result = hand.getCardType(handIndex).map((CardType ct) -> {
            if(isOtherFieldWithCardType(ct,fields) || (!(field.pileSize() == 0)
                    && field.peek().isPresent() && !field.peek().get().equals(ct)))
                return MafiaGameCommandResult.INVALID;
            field.append(hand.getCard(handIndex).get());
            return MafiaGameCommandResult.PLANT_HAND_MAFIA;
        }).orElse(MafiaGameCommandResult.INVALID);

        return result;
    }

    public void setHandIndex(int index){ this.handIndex = index;}
    public void setFieldIndex(int index){ this.fieldIndex = index;}

    //Taken from MafiaPlantFromRevealCommand
    private boolean isOtherFieldWithCardType(CardType ct, List<Pile> fields){
        for (int i=0; i<fields.size(); ++i) {
            if(i!=fieldIndex && fields.get(i).peek().isPresent() && fields.get(i).peek().get().equals(ct))
                return true;
        }
        return false;
    }
}
