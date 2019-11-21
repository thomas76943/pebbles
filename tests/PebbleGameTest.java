import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class PebbleGameTest {

    //Declaring Test Objects and Empty Sample Attributes - allows them to be accessed by any Test
    private PebbleGame testGame;
    private PebbleGame.Player testPlayer;
    private ArrayList<Integer> testContents;
    private ArrayList<Integer> expectedContents;
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
        String validInput = "5";
        InputStream in = new ByteArrayInputStream(validInput.getBytes());
        System.setIn(in);
        //Asserting that a valid number of players is identified
        assertEquals(5, testGame.getNumberOfPlayers());
    }

    @Test
    public void testBagLocations() throws Exception {
        String validFilePath = "pebblerange_1_20.csv";
        //Emulating user input with an InputStream
        File validFile = new File(validFilePath);
        InputStream in = new ByteArrayInputStream(validFilePath.getBytes());
        System.setIn(in);
        File result = testGame.getBagFileLocations(1);

        //Asserting that the valid file returns True when trying to read
        assertEquals(validFile, result);
    }

    @Test
    public void testReadFile() throws Exception {
        File fileWithLetter = new File("FileContainingLetter.csv");
        File fileWithZero = new File("FileContaining0.csv");
        File fileWithInvalidFormat = new File("FileWithInvalidFormat.csv");
        File fileDoesNotExist = new File("ThisPathDoesNotExist.csv");

        ArrayList<Integer> testReadFileOutput = new ArrayList<Integer>();

        //Asserting that each invalid file returns False when trying to read
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
    public void testTakeTurn() throws Exception {
        testContents.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        hand.clear();
        testPlayer = new PebbleGame.Player(testGame, 0, 1, "testOutput.txt", hand);

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

        //Asserting that the player's hand is now of size 10 after takeTurn()
        assertEquals(10, hand.size());
    }

    @Test
    public void testWriteDrawToFile() throws Exception {
        //Test File Created
        FileOutputStream fos = new FileOutputStream("testOutput.txt");
        OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
        Writer writer = new BufferedWriter(osw);

        //Test Player and Bags Initialised
        testPlayer = new PebbleGame.Player(testGame, 0, 1, "testOutput.txt", hand);
        WhiteBag testWhite = new WhiteBag(testContents, "testWhite");
        BlackBag testBlack = new BlackBag(testContents, "testBlack", testWhite);

        testGame.writeToFile(testPlayer, 5, testBlack);

        BufferedReader r = new BufferedReader(new FileReader("testOutput.txt"));
        String read = r.readLine();
        String[] lines = read.split("\\r?\\n");
        //Asserting that the draw move has been written to the file
        assertEquals("player0 has drawn a 5 from testBlack", lines[0]);
    }

    @Test
    public void testWriteDiscardToFile() throws Exception {
        //Test File Created
        FileOutputStream fos = new FileOutputStream("testOutput.txt");
        OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
        Writer writer = new BufferedWriter(osw);

        //Test Player and Bags Initialised
        testPlayer = new PebbleGame.Player(testGame, 0, 1, "testOutput.txt", hand);
        WhiteBag testWhite = new WhiteBag(testContents, "testWhite");
        BlackBag testBlack = new BlackBag(testContents, "testBlack", testWhite);

        testGame.writeToFile(testPlayer, 5, testWhite);

        BufferedReader r = new BufferedReader(new FileReader("testOutput.txt"));
        String read = r.readLine();
        String[] lines = read.split("\\r?\\n");

        //Asserting that the discard move has been written to the file
        assertEquals("player0 has discarded a 5 to testWhite", lines[0]);
    }

    @Test
    public void testCheckWin() throws Exception {
        hand.add(50);
        hand.add(30);
        hand.add(20);
        testPlayer = new PebbleGame.Player(testGame, 0, 1, "testOutput.txt", hand);
        testGame.checkWin(testPlayer);

        //Reflection used to obtain the private gameWon attribute
        Field win = Class.forName("PebbleGame").getDeclaredField("gameWon");
        win.setAccessible(true);
        AtomicBoolean result = (AtomicBoolean) win.get(testGame);

        //Asserting that the hand can be identified as a winning hand
        assertTrue(result.get());

    }

    @Test
    public void testCheckWinFalse() throws Exception {
        hand.add(50);
        testPlayer = new PebbleGame.Player(testGame, 0, 1, "testOutput.txt", hand);
        testGame.checkWin(testPlayer);

        //Reflection used to obtain the private gameWon attribute
        Field win = Class.forName("PebbleGame").getDeclaredField("gameWon");
        win.setAccessible(true);
        AtomicBoolean result = (AtomicBoolean) win.get(testGame);

        //Asserting that the hand is not identified as a winning hand
        assertFalse(result.get());
    }

    @After
    public void tearDown() throws Exception {
        testGame = null;
        assertNull(testGame);
    }
}