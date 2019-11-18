import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BlackBagTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetLinkedWhite() {
        ArrayList<Integer> testContents = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++)
            testContents.add(i);
        WhiteBag testWhite = new WhiteBag(testContents, "myWhite");
        BlackBag testBlack = new BlackBag(testContents, "myblack", testWhite);
        assertEquals(testWhite, testBlack.getLinkedWhite());
    }

    @Test
    public void testDrawFromBlack() {

    }

    @Test
    public void testFillBlackFromWhite() {
        ArrayList<Integer> testContents = new ArrayList<Integer>();
        ArrayList<Integer> expectedContents = new ArrayList<Integer>();

        for (int i = 0; i < 5; i++) {
            testContents.add(i);
            expectedContents.add(i);
        }

        WhiteBag testWhite = new WhiteBag(testContents, "myWhite");
        BlackBag testBlack = new BlackBag(new ArrayList<Integer>(), "myblack", testWhite);

        testBlack.fillBlackFromWhite();

        assertEquals(expectedContents, testBlack.contents);

    }

}