package nl.utwente.bpsd;

import nl.utwente.bpsd.impl.DefaultPlayerTest;
import nl.utwente.bpsd.impl.command.*;
import nl.utwente.bpsd.model.CardTest;
import nl.utwente.bpsd.model.CardTypeTest;
import nl.utwente.bpsd.model.pile.DiscardPileTest;
import nl.utwente.bpsd.model.pile.HandPileTest;
import nl.utwente.bpsd.model.pile.PileTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Jochem Elsinga on 5/9/2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        PileTest.class,
        HandPileTest.class,
        DiscardPileTest.class,
        CardTest.class,
        CardTypeTest.class,
        DefaultPlayerTest.class,
        DefaultBuyFieldCommandTest.class,
        DefaultDrawHandCommandTest.class,
        DefaultDrawTradeCommandTest.class,
        DefaultHarvestCommandTest.class,
        DefaultPlantCommandTest.class,
        DefaultReshuffleCommandTest.class
})
public class JunitTestSuite {
}
