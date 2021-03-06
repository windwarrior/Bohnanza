package nl.utwente.bpsd;

import nl.utwente.bpsd.impl.standard.StandardExchangeTest;
import nl.utwente.bpsd.impl.standard.StandardPlayerTest;
import nl.utwente.bpsd.impl.standard.command.*;
import nl.utwente.bpsd.impl.mafia.command.*;
import nl.utwente.bpsd.integration.InternalCommandTest;
import nl.utwente.bpsd.model.CardTest;
import nl.utwente.bpsd.model.CardTypeTest;
import nl.utwente.bpsd.model.pile.HandPileTest;
import nl.utwente.bpsd.model.pile.HarvestablePileTest;
import nl.utwente.bpsd.model.pile.PileTest;
import nl.utwente.bpsd.model.state.StateTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PileTest.class,
        HandPileTest.class,
        CardTest.class,
        CardTypeTest.class,
        StandardPlayerTest.class,
        StandardBuyFieldCommandTest.class,
        StandardDrawHandCommandTest.class,
        StandardDrawTradeCommandTest.class,
        StandardHarvestCommandTest.class,
        StandardPlantCommandTest.class,
        StandardAcceptExchangeCommandTest.class,
        StandardAddHandCardToExchangeCommandTest.class,
        StandardAddTradingAreaCardToExchangeCommandTest.class,
        StandardRemoveCardFromExchangeCommandTest.class,
        StandardDeclineExchangeCommandTest.class,
        StandardStartExchangeCommandTest.class,
        StandardExchangeTest.class,
        StateTest.class,
        MafiaPlantFromRevealCommandTest.class,
        MafiaPlantFromHandToMafiaCommandTest.class,
        MafiaPlantFromHandToFieldCommandTest.class,
        MafiaSkipToPhaseSixCommandTest.class,
        MafiaDrawCardsToRevealCommandTest.class,
        HarvestablePileTest.class,
        InternalCommandTest.class
})
public class JunitTestSuite {
}
