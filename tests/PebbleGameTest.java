import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;

public class PebbleGameTest {

    @Before
    public void setUp() throws Exception {
        PebbleGame game = new PebbleGame();
    }

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

    @Test
    public void testWin (){
        int total = 0;
        ArrayList<Integer> hand = new ArrayList<>();
        hand.add(1);
        hand.add(7);
        hand.add(8);
        hand.add(9);
        hand.add(10);
        hand.add(11);
        hand.add(12);
        hand.add(13);
        hand.add(14);
        hand.add(15);


        PebbleGame testGame = new PebbleGame();
        Class player = PebbleGame.Player.class;
        PebbleGame.Player testPlayer = new PebbleGame.Player(testGame, 0, 1, "testOutput.txt");
        //testPlayer.h

        testGame.checkWin(testPlayer);

        //Values of Pebbles in player's hand are summed
        for (int i = 0; i <= hand.size() - 1; i++) {
            total += hand.get(i);
        }

       assertEquals(100, total);
    }

    @Test
    public void testBagSize() {


    }

    @After
    public void tearDown() throws Exception {

    }



}