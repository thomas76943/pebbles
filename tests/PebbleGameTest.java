import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class PebbleGameTest {

    //Declaring Test Objects and Empty Sample Attributes - allows them to be accessed by any Test
    private PebbleGame testGame;
    private PebbleGame.Player testPlayer;
    private ArrayList<Integer> testContents;
    private  ArrayList<Integer> expectedContents;
    private ArrayList<Integer> hand;

    @Before
    public void setUp() throws Exception {
        //Instantiating Test Objects and Empty Sample Attributes
        testGame = new PebbleGame();
        testContents = new ArrayList<Integer>();
        expectedContents = new ArrayList<Integer>();
        hand = new ArrayList<>();
    }

    @Test public void testGetNumberOfPlayers() {
        //testGame.getNumberOfPlayers();
    }

    @Test
    public void testGetBagLocations() throws Exception {
        String emptyFilePath = "EmptyFile.csv";
        //testGame.getBagFileLocations(emptyFilePath);

    }

    @Test
    public void testReadFile() throws Exception {
        File fileWithLetter = new File("FileContainingLetter.csv");
        File fileWithZero = new File("FileContaining0.csv");
        File fileWithInvalidFormat = new File("FileWithInvalidFormat.csv");
        File fileDoesNotExist = new File("ThisPathDoesNotExist.csv");

        ArrayList<Integer> testReadFileOutput = new ArrayList<Integer>();

        assertFalse(testGame.readFile(fileWithLetter, testReadFileOutput));
        assertFalse(testGame.readFile(fileWithZero, testReadFileOutput));
        assertFalse(testGame.readFile(fileWithInvalidFormat, testReadFileOutput));
        assertFalse(testGame.readFile(fileDoesNotExist, testReadFileOutput));
    }

    @Test
    public void testInitialiseBags() throws Exception {
        testContents.add(10);
        //Add 55 numbers to expectedContents because an input of 5 Players
        //means each Black Bag should have 55 entries
        for (int i = 0; i < 55; i++)
            expectedContents.add(10);

        //Reflection used on PebbleGame's bb0, bb1 and bb2 private attributes
        testGame.initialiseBags(testContents, testContents, testContents, 5);
        Field black0 = Class.forName("PebbleGame").getDeclaredField("bb0");
        Field black1 = Class.forName("PebbleGame").getDeclaredField("bb1");
        Field black2 = Class.forName("PebbleGame").getDeclaredField("bb2");
        //Fields made accessible
        black0.setAccessible(true);
        black1.setAccessible(true);
        black2.setAccessible(true);
        //Fields cast into BlackBag objects
        BlackBag bb0 = (BlackBag) black0.get(testGame);
        BlackBag bb1 = (BlackBag) black0.get(testGame);
        BlackBag bb2 = (BlackBag) black0.get(testGame);

        assertEquals(expectedContents, bb0.getContents());
        assertEquals(expectedContents, bb1.getContents());
        assertEquals(expectedContents, bb2.getContents());
    }

    @Test
    public void testTakeFirstTurn() throws Exception {
        testContents.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        hand.clear();
        testPlayer = new PebbleGame.Player(testGame, 0, 1, "testOutput.txt",hand);

        //Reflection used on PebbleGame's bb0, bb1 and bb2 private attributes
        Field black0 = Class.forName("PebbleGame").getDeclaredField("bb0");
        Field black1 = Class.forName("PebbleGame").getDeclaredField("bb1");
        Field black2 = Class.forName("PebbleGame").getDeclaredField("bb2");
        Field white0 = Class.forName("PebbleGame").getDeclaredField("wb0");
        Field white1 = Class.forName("PebbleGame").getDeclaredField("wb1");
        Field white2 = Class.forName("PebbleGame").getDeclaredField("wb2");

        //Fields made accessible
        black0.setAccessible(true);
        black1.setAccessible(true);
        black2.setAccessible(true);
        white0.setAccessible(true);
        white1.setAccessible(true);
        white2.setAccessible(true);

        //Fields cast into BlackBag and WhiteBag objects
        WhiteBag wb0 = (WhiteBag) white0.get(testGame);
        WhiteBag wb1 = (WhiteBag) white1.get(testGame);
        WhiteBag wb2 = (WhiteBag) white2.get(testGame);
        black0.set(testGame, new BlackBag(testContents, "testBlack0", wb0));
        black1.set(testGame, new BlackBag(testContents, "testBlack1", wb1));
        black2.set(testGame, new BlackBag(testContents, "testBlack2", wb2));

        testGame.takeTurn(testPlayer);

        BlackBag bb0 = (BlackBag) black0.get(testGame);
        BlackBag bb1 = (BlackBag) black1.get(testGame);
        BlackBag bb2 = (BlackBag) black2.get(testGame);

        assertEquals(10, hand.size());

        /*
        BlackBag[] BlackBags = {bb0, bb1, bb2};
        WhiteBag[] WhiteBags = {wb0, wb1, wb2};

        int blackBagsWithNinePebbles = 0;
        int blackBagsWithTenPebbles = 0;
        int whiteBagsWithOnePebble = 0;
        int whiteBagsWithZeroPebbles = 0;

        for (BlackBag bb : BlackBags) {
            if (bb.getContents().size() == 9)
                blackBagsWithNinePebbles += 1;
            else
                blackBagsWithTenPebbles += 1;
        }

        for (WhiteBag wb : WhiteBags) {
            if (wb.getContents().size() == 1)
                whiteBagsWithOnePebble += 1;
            else
                whiteBagsWithZeroPebbles += 1;
        }

        assertEquals(1, blackBagsWithNinePebbles);
        assertEquals(2, blackBagsWithTenPebbles);
        assertEquals(1, whiteBagsWithOnePebble);
        assertEquals(2, whiteBagsWithZeroPebbles);

        */
    }

    @Test
    public void testWriteToFile() throws Exception {
        testPlayer = new PebbleGame.Player(testGame, 0, 1, "testOutput.txt", hand);
    }

    @Test
    public void testCheckWin () throws Exception {
        hand.add(50);
        hand.add(30);
        hand.add(20);
        testPlayer = new PebbleGame.Player(testGame, 0, 1, "testOutput.txt", hand);
        testGame.checkWin(testPlayer);

        Field win = Class.forName("PebbleGame").getDeclaredField("gameWon");
        win.setAccessible(true);
        AtomicBoolean result = (AtomicBoolean) win.get(testGame);

        assertTrue(result.get());
    }

    @Test
    public void testCheckWinFalse () throws Exception {
        hand.add(50);
        testPlayer = new PebbleGame.Player(testGame, 0, 1, "testOutput.txt", hand);
        testGame.checkWin(testPlayer);

        Field win = Class.forName("PebbleGame").getDeclaredField("gameWon");
        win.setAccessible(true);
        AtomicBoolean result = (AtomicBoolean) win.get(testGame);

        assertFalse(result.get());
    }

    @After
    public void tearDown() throws Exception {
        testGame = null;
        assertNull(testGame);
    }

    /*

    @Test(expected = NumberFormatException.class)
    public void testStringPlayerNum() {
        int playerNum;
        String playerNumS = "three";
        playerNum = Integer.parseInt(playerNumS);
    }

    @Test(expected = NumberFormatException.class)
    public void testLargerPlayerNum() {
        int playerNum;
        String playerNumS = "28";
        playerNum = Integer.parseInt(playerNumS);
        if (playerNum < 1 || playerNum > 25)
            throw new NumberFormatException();
    }

    @Test(expected = NumberFormatException.class)
    public void testNoPlayerNum() {
        int playerNum;
        String playerNumS = "";
        playerNum = Integer.parseInt(playerNumS);
    }

    */
}