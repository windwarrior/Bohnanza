package nl.utwente.bpsd;

import nl.utwente.bpsd.impl.standard.StandardPlayerTest;
import nl.utwente.bpsd.impl.standard.command.*;
import nl.utwente.bpsd.model.CardTest;
import nl.utwente.bpsd.model.CardTypeTest;
import nl.utwente.bpsd.model.pile.DiscardPileTest;
import nl.utwente.bpsd.model.pile.HandPileTest;
import nl.utwente.bpsd.model.pile.PileTest;
import nl.utwente.bpsd.model.state.StateTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PileTest.class,
        HandPileTest.class,
        DiscardPileTest.class,
        CardTest.class,
        CardTypeTest.class,
        StandardPlayerTest.class,
        StandardBuyFieldCommandTest.class,
        StandardDrawHandCommandTest.class,
        StandardDrawTradeCommandTest.class,
        StandardHarvestCommandTest.class,
        StandardPlantCommandTest.class,
        StandardReshuffleCommandTest.class,
        StateTest.class
})
public class JunitTestSuite {
}
