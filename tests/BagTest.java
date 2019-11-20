import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class BagTest {

    Bag testBag;

    @Before
    public void setUp() throws Exception {
        ArrayList<Integer> testContents = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++)
            testContents.add(i);
        testBag = new Bag(testContents, "myBag"); ;
    }

    @After
    public void tearDown() throws Exception {
        testBag = null;
        assertNull(testBag);
    }

    @Test
    public void testGetContents() {
        assertEquals(Arrays.asList(0,1,2,3,4), testBag.getContents());
    }

    @Test
    public void testGetBagName() {
        assertEquals("myBag", testBag.getBagName());
    }
}