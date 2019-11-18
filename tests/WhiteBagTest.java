import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WhiteBagTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addToWhite() {
        ArrayList<Integer> testContents = new ArrayList<Integer>();
        ArrayList<Integer> expectedContents = new ArrayList<Integer>();

        for (int i = 0; i < 5; i++) {
            testContents.add(i);
            expectedContents.add(i);
        }

        WhiteBag testWhite = new WhiteBag(testContents, "myWhite");
        int pebble = 5;
        expectedContents.add(pebble);
        testWhite.addToWhite(pebble);
        assertEquals(expectedContents, testWhite.contents);

    }
}