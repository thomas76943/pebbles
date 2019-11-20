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

    @Test
    public void addToWhite() { ;;
        testWhite.addToWhite(10);
        assertEquals(Arrays.asList(10), testWhite.contents);
    }

    @After
    public void tearDown() throws Exception {
        testWhite = null;
        assertNull(testWhite);
    }

}