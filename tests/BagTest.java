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
    public void getContents() {
        ArrayList<Integer> testContents = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++)
            testContents.add(i);

        //ArrayList<Integer> expected = [1, 2]

        Bag testBag = new Bag(testContents, "testBag");
        //assertEquals([1,2,3,4,5], testBag.getContents());

    }

    @Test
    public void getBagName() {
    }
}