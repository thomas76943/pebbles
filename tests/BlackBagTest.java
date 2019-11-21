import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class BlackBagTest {

    BlackBag testBlack;
    WhiteBag testWhite;

    @Before
    public void setUp() throws Exception {
        ArrayList<Integer> testContents = new ArrayList<Integer>();
        testWhite = new WhiteBag(testContents, "testWhite");
        for (int i = 0; i < 5; i++)
            testContents.add(i);
        testBlack = new BlackBag(testContents, "testBlack", testWhite);
    }

    @After
    public void tearDown() throws Exception {
        testBlack = null;
        testWhite = null;
        assertNull(testBlack);
        assertNull(testWhite);
    }

    @Test
    public void testGetLinkedWhite() {
        assertEquals(testWhite, testBlack.getLinkedWhite());
    }

    @Test
    public void testDrawFromBlack() {
        ArrayList<Integer> testContents = new ArrayList<>();
        testContents.add(1);
        testBlack = new BlackBag(testContents, "testBlackDraw", testWhite);
        testBlack.drawFromBlack();

        assertEquals(new ArrayList<Integer>(), testBlack.getContents());

    }

    @Test
    public void testFillBlackFromWhite() {
        BlackBag testBlack = new BlackBag(new ArrayList<Integer>(), "testBlackEmpty", testWhite);

        ArrayList<Integer> expectedContents = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++)
            expectedContents.add(i);

        WhiteBag testWhiteFull = new WhiteBag(expectedContents, "testWhiteFull");
        testBlack.fillBlackFromWhite();
        assertEquals(expectedContents, testBlack.getContents());
        assertEquals(new ArrayList<Integer>(), testWhite.getContents());
    }

    @Test
    public void testDrawFromBlackAndFillBlackFromWhite() {
        //Combining the above two tests
        ArrayList<Integer> drawAndRefill = new ArrayList<>();
        testBlack = new BlackBag(drawAndRefill, "testBlackDrawAndRefill", testWhite);
        testBlack.drawFromBlack();
        assertEquals(new ArrayList<Integer>(), testWhite.getContents());
    }

}