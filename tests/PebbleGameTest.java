import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class PebbleGameTest {

    PebbleGame testGame;

    @Before
    public void setUp() throws Exception {
        testGame = new PebbleGame();
    }

    @Test
    public void testGetBagLocations() throws Exception {
        String testFilePath = "FileContainingLetter.csv";
    }

    @Test
    public void testReadFile() throws Exception {
        File fileWithLetter = new File("FileContainingLetter.csv");
        File emptyFile = new File("EmptyFile.csv");
        File testFile = new File("IncorrectFormatFile.csv");

        ArrayList<Integer> testReadFileOutput = new ArrayList<Integer>();

        assertFalse(testGame.readFile(testFile, testReadFileOutput));
        assertFalse(testGame.readFile(testFile, testReadFileOutput));
        assertFalse(testGame.readFile(testFile, testReadFileOutput));


    }

    @Test
    public void testInitialiseBags() throws Exception {

    }

    @Test
    public void testTakeTurn() throws Exception {

    }

    @Test
    public void testWriteToFile() throws Exception {

    }

    @Test
    public void testCheckWin () {
        ArrayList<Integer> hand = new ArrayList<>();
        hand.add(50);
        hand.add(30);
        hand.add(20);
        PebbleGame.Player testPlayer = new PebbleGame.Player(testGame, 0, 1, "testOutput.txt", hand);
        boolean result = false;
        if (testGame.checkWin(testPlayer))
            result = true;
        assertTrue(result);
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