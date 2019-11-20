import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class WhiteBagTest {

    WhiteBag testWhite;

    @Before
    public void setUp() throws Exception {
        ArrayList<Integer> testContents = new ArrayList<Integer>();
        testWhite = new WhiteBag(testContents, "testWhite");
    }

    @After
    public void tearDown() throws Exception {
        testWhite = null;
        assertNull(testWhite);
    }

    @Test
    public void addToWhite() {
        int pebble = 1;
        testWhite.addToWhite(pebble);
        pebble = 5;
        testWhite.addToWhite(pebble);
        assertEquals(Arrays.asList(1,5), testWhite.contents);

    }
}