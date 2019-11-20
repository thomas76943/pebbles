import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PebbleGame {

    private AtomicInteger currentPlayer = new AtomicInteger(1);
    private AtomicBoolean gameWon = new AtomicBoolean(false);
    private HashMap<Integer, BlackBag> lastBlackUsed = new HashMap<Integer, BlackBag>();
    private int playerNum;
    private BlackBag bb0;
    private BlackBag bb1;
    private BlackBag bb2;
    private WhiteBag wb0;
    private WhiteBag wb1;
    private WhiteBag wb2;

    /**
     * main() Method: Main Thread of the Program. The program's instance of PebbleGame is created here.
     * getNumberOfPlayers is called here. Entering 'E'/'e' exits the program. The method getBagFileLocations() is called
     * from here until the inputs are seen as valid and then initialiseBags is then called.
     * Players are then created and corresponding threads are set up alongside the player move output files.
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        final PebbleGame game = new PebbleGame();
        boolean validPlayerNum = false;
        ArrayList<Integer> pebbles0 = new ArrayList<Integer>();
        ArrayList<Integer> pebbles1 = new ArrayList<Integer>();
        ArrayList<Integer> pebbles2 = new ArrayList<Integer>();

        System.out.println("Welcome to PebbleGame.");
        System.out.println("You will be asked to enter the number of players.");
        System.out.println("You will then be asked for the locations of three files containing comma separated integers for the pebble weights.");
        System.out.println("The game will then be simulated.");
        System.out.println("Every move taken by each player will be written to files in this directory.");
        System.out.println("Entering 'e'/'E' at any input will exit the program.");

        game.playerNum = game.getNumberOfPlayers();


        //Call to method to set file locations for pebble sizes
        File f1 = game.getBagFileLocations(0);
        while (!game.readFile(f1, pebbles0))
            f1 = game.getBagFileLocations(0);

        File f2 = game.getBagFileLocations(1);
        while (!game.readFile(f2, pebbles1))
            f2 = game.getBagFileLocations(1);

        File f3 = game.getBagFileLocations(2);
        while (!game.readFile(f3, pebbles2))
            f3 = game.getBagFileLocations(2);

        //Lists of values taken by Black Bags 0, 1 and 2 are passed into initialiseBags()
        //along with the number of players in the game

        game.initialiseBags(pebbles0, pebbles1, pebbles2, game.playerNum);

        for (int i = 1; i <= game.playerNum; i++) {
            String outputFileName = "player" + i + "_output.txt";
            Player player = new Player(game, i, game.playerNum, outputFileName, new ArrayList<Integer>());
            Thread playerThread = new Thread(player);
            playerThread.start();
            try {
                FileOutputStream fos = new FileOutputStream(outputFileName);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
                Writer writer = new BufferedWriter(osw);
            } catch (IOException e) {
                System.out.println("Could not create Output File");
            }
        }
    }

    /**
     * This method returns the boolean result of validating the user's input for how many players they would
     * like in the game. Entering 'e'/'E' will allow the user to exit the program. If the user enters a character that
     * is not an integer or an integer that is not between 1-10, the program will ask again.
     * @return
     */
    public int getNumberOfPlayers() {
        Scanner input = new Scanner(System.in);
        int output = 0;
        while (true) {
            try {
                System.out.println("Enter Number of Players (1-10): ");
                String playerNumS = input.nextLine();
                if (playerNumS.equalsIgnoreCase("e")) {
                    System.out.println("Exiting");
                    System.exit(0);
                }
                output = Integer.parseInt(playerNumS);

                if (output < 1 || output > 10) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Must be Integer between 1-10");
            }
        }
        return output;
    }

    /**
     * This method is called from main() and is used to ask the user for the locations of the files containing the
     * values for pebbles in the 3 Black Bags. The input is validated to make sure: it is a file, it can be can read,
     * it is not a directory and it is not empty. The user may exit by entering "E'/'e'.
     * @param bagNum - The number corresponding to the first, second or third Black Bag.
     */
    public File getBagFileLocations(int bagNum) {

        Scanner input = new Scanner(System.in);
        File f;
        String bagFile;

        //A do-While structure is used here to ensure the inputs are asked at least once
        do {
            System.out.println("Please enter location of bag " + bagNum + " to load: ");
            bagFile = input.nextLine();
            //Program Allows User to Exit
            if (bagFile.equalsIgnoreCase("e")) {
                System.out.println("Exiting");
                System.exit(0);
            }
            f = new File(bagFile);
        } while (!(f.isFile()) || !(f.canRead()) || f.isDirectory() || !(f.length()>0)); //File Validation Checks
        return f;
    }

    /**
     * This method employs a FileReader object that is passed into a BufferedReader object. The bagFile is read and
     * split at every comma value. The output ArrayList< Integer> is added to throughout and cleared if any illegal
     * cases are found. This method provides further validation checks: that a pebble can only be an integer and it
     * ensures only values greater than 0 are considered.
     * @param bagFile - The file containing the values of pebbles
     * @param output - The ArrayList to which the pebbles are added
     * @return result - The method returns the ArrayList of values that pebbles can be generated from
     */
    public boolean readFile(File bagFile, ArrayList<Integer> output) {

        //BufferedReader makes use of FileReader. Try-Catch exists to catch any file-related IOException
        try (BufferedReader r = new BufferedReader(new FileReader(bagFile))) {
            String read = r.readLine();
            String[] lines = read.split(","); //Splitting each value by "," works for both .txt and .csv files

            for (String line : lines) {
                if (line == "") {
                    //Further validation checks: value must be an integer
                    System.out.println("All pebbles must be integers and greater than 0");
                    output.clear();
                    return false;
                }
                try {
                    int number = Integer.parseInt(line);
                    if (number > 0) {
                        //Further validation checks: value only added if greater than 0
                        output.add(number);
                    }
                    else {
                        System.out.println("All pebbles > 0");
                        output.clear();
                        return false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("NumberFormatException. Must be Integer > 0");
                    output.clear();
                    return false;
                }
            }
        }
        catch ( IOException e)  {
            System.out.println("BufferedReader/FileReader Error Occurred. File May Not Exist");
            output.clear();
            return false;
        }
        return true;
    }

    /**
     * This method accepts the ArrayLists containing the pebbles to be placed in each Black Bag as parameters. Three
     * while loop structures determine if the size of each bag's contents ArrayList is at least equal to the number of
     * players multiplied by 11. The pebbles are added continually until this satisfies as true. Three White Bags are
     * then instantiated before being passed as constructor parameters when defining three Black Bags.
     * @param range0 - the contents of the csv/txt file for Black Bag 0 / X
     * @param range1 - the contents of the csv/txt file for Black Bag 1 / Y
     * @param range2 - the contents of the csv/txt file for Black Bag 2 / Z
     * @param playerNum - the number of players in the game, used to determine the minimum size for the Black Bags
     */
    public void initialiseBags(ArrayList<Integer> range0, ArrayList<Integer> range1,
                                ArrayList<Integer> range2, int playerNum) {

        ArrayList<Integer> contents0 = new ArrayList<Integer>();
        ArrayList<Integer> contents1 = new ArrayList<Integer>();
        ArrayList<Integer> contents2 = new ArrayList<Integer>();

        //Black Bag sizes must be at least 11 times the number of players
        while (contents0.size() < playerNum * 11) {
            //System.out.println("Bag 0 was too small. " + "Size: " + contents0.size() + ". Adding again.");
            contents0.addAll(range0);
        }

        //Black Bag sizes must be at least 11 times the number of players
        while (contents1.size() < playerNum * 11) {
            //System.out.println("Bag 1 was too small. " + "Size: " + contents1.size() + ". Adding again.");
            contents1.addAll(range1);
        }

        //Black Bag sizes must be at least 11 times the number of players
        while (contents2.size() < playerNum * 11) {
            //System.out.println("Bag 2 was too small. " + "Size: " + contents2.size() + ". Adding again.");
            contents2.addAll(range2);
        }

        //Three White Bags, A, B and C, are instantiated with empty contents
        wb0 = new WhiteBag(new ArrayList<Integer>(), "Bag A");
        wb1 = new WhiteBag(new ArrayList<Integer>(), "Bag B");
        wb2 = new WhiteBag(new ArrayList<Integer>(), "Bag C");

        //Three Black Bags, X, Y and Z, are instantiated with the contents from the files and are
        //each given a corresponding White Bag to which they are linked
        bb0 = new BlackBag(contents0, "Bag X", wb0);
        bb1 = new BlackBag(contents1, "Bag Y", wb1);
        bb2 = new BlackBag(contents2, "Bag Z", wb2);
    }

    /**
     * The takeTurn(Player player) method serves as the method wherein the game is primarily played. Initially, if the
     * player's hand is not empty, a pebble is discarded to the white bag linked to the last Black Bag used. A random
     * Black Bag is then chosen and a random pebble is added to the player's hand from it. This Black Bag is recorded in
     * the lastBlackUsed HashMap for their next move. checkWin(Player player) is called after a pebble is drawn.
     * @param player - the current player whose turn it is
     */
    public void takeTurn(Player player) {

        Random r = new Random();

        if (player.hand.size() != 0) { //Checks if hand is not empty: if so, a pebble must be discarded
            int index = r.nextInt(player.hand.size());
            int pebble = player.hand.get(index);
            player.hand.remove(index);
            BlackBag bb = lastBlackUsed.get(player.playerNum);
            WhiteBag wb = bb.getLinkedWhite(); //Discards pebble to White Bag corresponding to last Black Bag drawn from
            wb.addToWhite(pebble);
            writeToFile(player, pebble, wb); //Records this move in the output file
        }

        switch (r.nextInt(3)) { //Switch-case structure represents each of the 3 Black Bags
            case 0:
                if (player.hand.size() == 0) {
                    //Initialising the bag with 10 pebbles from Black Bag 0
                    for (int i = 0; i < 10; i++) {
                        int pebble = bb0.drawFromBlack();
                        player.hand.add(pebble);
                    }
                    lastBlackUsed.put(player.playerNum, bb0); //Records this Black Bag being drawn from
                }
                else {
                    int pebble = bb0.drawFromBlack();
                    player.hand.add(pebble);
                    lastBlackUsed.put(player.playerNum, bb0); //Records this Black Bag being drawn from
                    writeToFile(player, pebble, bb0); //Records this move in the output file
                }
                //Prints turn to Console
                System.out.println("Player " + this.currentPlayer + "'s turn. " + bb0.getBagName()
                        + " chosen. Hand: " + player.hand);
                checkWin(player);
                break;

            case 1:
                if (player.hand.size() == 0) {
                    //Initialising the bag with 10 pebbles from Black Bag 1
                    for (int i = 0; i < 10; i++) {
                        int pebble = bb1.drawFromBlack();
                        player.hand.add(pebble);
                    }
                    lastBlackUsed.put(player.playerNum, bb1); //Records this Black Bag being drawn from
                }
                else {
                    int pebble = bb1.drawFromBlack();
                    player.hand.add(pebble);
                    lastBlackUsed.put(player.playerNum, bb1); //Records this Black Bag being drawn from
                    writeToFile(player, pebble, bb1); //Records this move in the output file
                }
                //Prints turn to Console
                System.out.println("Player " + this.currentPlayer + "'s turn. " + bb1.getBagName()
                        + " chosen. Hand: " + player.hand);
                checkWin(player);
                break;

            case 2:
                if (player.hand.size() == 0) {
                    //Initialising the bag with 10 pebbles from Black Bag 2
                    for (int i = 0; i < 10; i++) {
                        int pebble = bb2.drawFromBlack();
                        player.hand.add(pebble);
                    }
                    lastBlackUsed.put(player.playerNum, bb2);; //Records this Black Bag being drawn from
                }
                else {
                    int pebble = bb2.drawFromBlack();
                    player.hand.add(pebble);
                    lastBlackUsed.put(player.playerNum, bb2); //Records this Black Bag being drawn from
                    writeToFile(player, pebble, bb2); //Records this move in the output file
                }
                //Prints turn to Console
                System.out.println("Player " + this.currentPlayer+"'s turn. "+bb2.getBagName()
                        + " chosen. Hand: " + player.hand);
                checkWin(player);
                break;
        }
    }

    /**
     * This method is called after every draw and discard. moveLog and handLog Strings are appended to the player's
     * output file detailing the last move they took and the contents of their hand after the move took place. The
     * method determines if the move is drawing or discarding by identifying if the Bag is of type BlackBag or WhiteBag
     * @param player - the current player whose move it is
     * @param pebble - the pebble being drawn/discarded
     * @param bag - the bag that the pebble is being drawn from/discarded to
     */
    public void writeToFile(Player player, int pebble, Bag bag) {

        String moveLog = "";
        String handLog = "player" + player.playerNum + " hand is " + player.hand + "\n";

        if (bag instanceof BlackBag) {
            //The Bag being a BlackBag means a pebble is being drawn
            moveLog = "player" + player.playerNum + " has drawn a " + pebble + " from " + bag.getBagName() + "\n";

        }
        else if (bag instanceof WhiteBag) {
            //The Bag being a WhiteBag means a pebble is being discarded
            moveLog = "player" + player.playerNum + " has discarded a " + pebble + " to " + bag.getBagName() + "\n";
        }

        try {
            //The move being performed and the new state of the player's hand are appended to the player's output file
            Files.write(Paths.get(player.outputFileName), moveLog.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(player.outputFileName), handLog.getBytes(), StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.out.println("Could not read move to file");
        }
    }

    /**
     * This method changes the PebbleGame's AtomicBoolean attribute gameWon to true if the
     * combined total size of pebbles in the player's hand is exactly 100.
     * @param player - the player whose hand is being evaluated
     */
    public void checkWin(Player player) {
        int total = 0;
        //Values of Pebbles in player's hand are summed
        for (int i = 0; i <= player.hand.size() - 1; i++) {
            total += player.hand.get(i);
        }
        if (total == 100) {
            //Win condition is met
            System.out.println("Player " + player.playerNum + " has won. Hand: " + player.hand);
            gameWon.set(true);
        }
    }

    public static class Player implements Runnable {

        private PebbleGame game;
        private int playerNum;
        private int maxPlayers;
        private String outputFileName;
        private ArrayList<Integer> hand = new ArrayList<Integer>();

        /**
         * The constructor for the Player subclass. It takes an instance of PebbleGame, the current player's number,
         * the number of players in the game and the name of the current player's output file.
         * @param game - instance of game shared between every player
         * @param playerNum - current player's number
         * @param maxPlayers - total number of players in the game
         * @param outputFileName - name of current player's output file
         */
        public Player (PebbleGame game, int playerNum, int maxPlayers, String outputFileName, ArrayList<Integer> hand) {
            this.game = game;
            this.playerNum = playerNum;
            this.maxPlayers = maxPlayers;
            this.outputFileName = outputFileName;
            this.hand = hand;
        }

        /**
         * This method is called directly from playerThread.start() in the main thread. A single instance of PebbleGame
         * is passed between the players. If the game has not been won and it is the current player's turn, access to
         * the game object is locked from all other threads. While locked, takeTurn() is called and the current player
         * is incremented. If it is the last player's turn, this counter is reset to 0. After completing the move, the
         * lock on the game object is lifted and the other threads can begin listening to see if it is their turn next.
          */
        public void run() {
            //Checks if the game is not won before allowing a move
            while (!game.gameWon.get()) {
                //Checks if it is current player's turn because all Threads are listening
                if (this.playerNum == game.currentPlayer.get()) {
                    //Locks the game object to the current player, nobody else has access
                    synchronized (game) {
                            try {
                                //Main game play. Increments the current player after a move is made
                                game.takeTurn(this);
                                game.currentPlayer.getAndIncrement();
                                //Checks if it is currently the last player's turn and resets the player turn counter
                                if (this.playerNum == this.maxPlayers) {
                                    game.currentPlayer.set(1);
                                    //Releases the lock, all threads are awake
                                    game.notifyAll();
                                }
                                else {
                                    //Releases the lock by suspending the current Thread
                                    game.wait();
                                }
                            } catch (InterruptedException e) {
                                System.out.println("Thread Finished: InterruptedException");
                                e.printStackTrace();
                            }
                    }
                }
            }
            //Game is over if this is reached
            Thread.currentThread().interrupt();
            System.exit(0);
        }
    }
}