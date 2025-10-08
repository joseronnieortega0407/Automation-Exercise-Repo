package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.test.TC1;
import com.test.TC2;
import com.test.TC3;
import com.test.TC4;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    TC1.class,
    TC2.class,
    TC3.class,
    TC4.class
})

public class TestSuite {
    // This will run RegisterTest and LoginTest in one go
}
