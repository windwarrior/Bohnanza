package nl.utwente.bpsd;

import nl.utwente.bpsd.impl.command.DefaultBuyFieldCommandTest;
import nl.utwente.bpsd.impl.command.DefaultDrawHandCommandTest;
import nl.utwente.bpsd.impl.command.DefaultDrawTradeCommandTest;
import nl.utwente.bpsd.model.CardTypeTest;
import nl.utwente.bpsd.model.PlayerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Jochem Elsinga on 5/9/2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CardTypeTest.class,
        PlayerTest.class,
        DefaultBuyFieldCommandTest.class,
        DefaultDrawHandCommandTest.class,
        DefaultDrawTradeCommandTest.class
})
public class JunitTestSuite {
}
