import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class BagTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetContents() {
        ArrayList<Integer> testContents = new ArrayList<Integer>();
        ArrayList<Integer> expectedContents = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            testContents.add(i);
            expectedContents.add(i);
        }
        Bag testBag = new Bag(testContents, "testBag");
        assertEquals(expectedContents, testBag.getContents());
    }

    @Test
    public void testGetBagName() {
        ArrayList<Integer> testContents = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++)
            testContents.add(i);
        Bag testBag = new Bag(testContents, "myBag");
        assertEquals("myBag", testBag.getBagName());
    }

}