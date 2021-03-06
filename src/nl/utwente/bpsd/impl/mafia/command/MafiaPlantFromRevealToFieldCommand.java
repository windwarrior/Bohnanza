package nl.utwente.bpsd.impl.mafia.command;

import nl.utwente.bpsd.impl.mafia.MafiaBoss;
import nl.utwente.bpsd.impl.mafia.MafiaGame;
import nl.utwente.bpsd.impl.mafia.MafiaGameCommandResult;
import nl.utwente.bpsd.impl.mafia.MafiaPlayer;
import nl.utwente.bpsd.model.CardType;
import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.GameCommandResult;
import nl.utwente.bpsd.model.Player;
import nl.utwente.bpsd.model.pile.Pile;

import java.util.ArrayList;
import java.util.List;
import nl.utwente.bpsd.model.pile.HarvestablePile;

/** Current player choose card from any of reveal piles
 * and plant it on any of his/her fields.
 * in Phase 1 & 5 from Al Cabohne game rules
 */
public class MafiaPlantFromRevealToFieldCommand extends MafiaGameCommand {
    int fieldIndex;
    int revealIndex;



    @Override
    public GameCommandResult execute(Player p, Game g) {
        super.execute(p,g); // force a check that this is indeed a MafiaGame
        MafiaGame game = (MafiaGame) g;
        MafiaPlayer player = (MafiaPlayer) p;

        List<HarvestablePile> fields = this.getFields(player,game);

        if(fields.size() <= fieldIndex || fieldIndex < 0
                || game.getRevealArray().size() <= revealIndex || revealIndex < 0)
            return MafiaGameCommandResult.INVALID;

        Pile field = fields.get(fieldIndex);
        Pile reveal = game.getRevealArray().get(revealIndex);
        GameCommandResult result;

        //check if there is any card in chosen reveal Pile
        result = reveal.peek().map((CardType ct) -> {
            /*
             * Cards of the same type must be plant on the same field.
             * Player can only plant card on empty field or on
             * field with matching card types.
             */
            if(isOtherFieldWithCardType(ct, fields) || (!(field.pileSize() == 0)
                    && field.peek().isPresent() && !field.peek().get().equals(ct)))
                return MafiaGameCommandResult.INVALID;
            field.append(reveal.pop().get());
            return MafiaGameCommandResult.PLANT_REVEAL;
        }).orElse(MafiaGameCommandResult.INVALID);

        return result;
    }

    public void setFieldIndex(int index){
        this.fieldIndex = index;
    }
    public void setRevealIndex(int index){
        this.revealIndex = index;
    }

    protected List<HarvestablePile> getFields(MafiaPlayer player, MafiaGame game){
        return player.getAllFields();
    }

    /**
     *@return true if player has other field with the same card types
     * otherwise returns false
     */
    private boolean isOtherFieldWithCardType(CardType ct, List<HarvestablePile> fields){
        for (int i=0; i<fields.size(); ++i) {
            if(i!=fieldIndex && fields.get(i).peek().isPresent() && fields.get(i).peek().get().equals(ct))
                return true;
        }
        return false;
    }
}
