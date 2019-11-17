import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class PebbleGame {

    AtomicInteger currentPlayer = new AtomicInteger(1);
    private BlackBag bb0;
    private BlackBag bb1;
    private BlackBag bb2;
    private WhiteBag wb0;
    private WhiteBag wb1;
    private WhiteBag wb2;
    private HashMap<Integer, BlackBag> lastBlackUsed = new HashMap<Integer, BlackBag>();

    public static void main(String[] args) throws InterruptedException, IOException {

        final PebbleGame game = new PebbleGame();
        int playerNum = 0;

        Scanner input = new Scanner(System.in);
        System.out.println("Enter Number of Players: ");
        try {
            String inputLine = input.nextLine();
            if (inputLine.equalsIgnoreCase("e")) {
                System.out.println("Exiting");
                System.exit(0);
            }
            playerNum = Integer.parseInt(inputLine);
        }
        catch (NumberFormatException e) {
            System.out.println("Input Invalid. Must be Integer");
        }

        game.getBagFileLocations(playerNum);

        for (int i = 1; i <= playerNum; i++) {

            String outputFileName = "player" + i + "_output.txt";
            Player player = new Player(game, i, playerNum, outputFileName);
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

    public void takeTurn(Player player) {

        Random r = new Random();

        if (player.hand.size() != 0) {
            int index = r.nextInt(player.hand.size());
            int pebble = player.hand.get(index);
            player.hand.remove(index);
            BlackBag bb = lastBlackUsed.get(player.playerNum);
            WhiteBag wb = bb.getLinkedWhite();
            wb.addToWhite(pebble);
            writeToFile(player, pebble, wb);
        }

        switch (r.nextInt(3)) {
            case 0:
                if (player.hand.size() == 0) {
                    for (int i = 0; i < 10; i++) {
                        int pebble = bb0.drawFromBlack();
                        player.hand.add(pebble);
                    }
                    lastBlackUsed.put(player.playerNum, bb0);
                }
                else {
                    int pebble = bb0.drawFromBlack();
                    player.hand.add(pebble);
                    lastBlackUsed.put(player.playerNum, bb0);
                    writeToFile(player, pebble, bb0);
                }
                System.out.println(this.currentPlayer + " is taking a turn. Bag Chosen: 0. Hand: " + player.hand);
                break;

            case 1:
                if (player.hand.size() == 0) {
                    for (int i = 0; i < 10; i++) {
                        int pebble = bb1.drawFromBlack();
                        player.hand.add(pebble);
                    }
                    lastBlackUsed.put(player.playerNum, bb1);
                }
                else {
                    int pebble = bb1.drawFromBlack();
                    player.hand.add(pebble);
                    lastBlackUsed.put(player.playerNum, bb1);
                    writeToFile(player, pebble, bb1);
                }
                System.out.println(this.currentPlayer + " is taking a turn. Bag Chosen: 1. Hand: " + player.hand);
                break;

            case 2:
                if (player.hand.size() == 0) {
                    for (int i = 0; i < 10; i++) {
                        int pebble = bb2.drawFromBlack();
                        player.hand.add(pebble);
                    }
                    lastBlackUsed.put(player.playerNum, bb2);;
                }
                else {
                    int pebble = bb2.drawFromBlack();
                    player.hand.add(pebble);
                    lastBlackUsed.put(player.playerNum, bb2);
                    writeToFile(player, pebble, bb2);
                }
                System.out.println(this.currentPlayer + " is taking a turn. Bag Chosen: 2. Hand: " + player.hand);
                break;
        }

    }

    private void writeToFile(Player player, int pebble, Bag bag) {

        String moveLog = "";
        String handLog = "player" + player.playerNum + " hand is " + player.hand + "\n";

        if (bag instanceof BlackBag) {
            moveLog = "player" + player.playerNum + " has drawn a " + pebble + " from bag " + bag.getBagName() + "\n";

        }
        else if (bag instanceof WhiteBag) {
            moveLog = "player" + player.playerNum + " has discarded a " + pebble + " to bag " + bag.getBagName() + "\n";
        }

        try {
            Files.write(Paths.get(player.outputFileName), moveLog.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(player.outputFileName), handLog.getBytes(), StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.out.println("Could not read move to file");
        }
    }

    public boolean checkWin(Player player) {
        int total = 0;
        for (int i = 0; i <= player.hand.size() - 1; i++) {
            total += player.hand.get(i);
        }
        if (total == 100) {
            System.out.println("Player " + player.playerNum + " has won");
            System.out.println("Hand: " + player.hand);
            return true;
        }
        return false;
    }

    public void getBagFileLocations(int playerNum) {

        Scanner input = new Scanner(System.in);
        File f;
        String bagFile;

        do {
            System.out.println("Please enter location of bag number 0 to load: ");
            bagFile = input.nextLine();
            if (bagFile.equalsIgnoreCase("e")) {
                System.out.println("Exiting");
                System.exit(0);
            }
            f = new File(bagFile);
        } while (!(f.isFile()) || !(f.canRead()) || f.isDirectory());
        ArrayList<Integer> bag0Range = readFile(bagFile);

        do {
            System.out.println("Please enter location of bag number 1 to load: ");
            bagFile = input.nextLine();
            if (bagFile.equalsIgnoreCase("e")) {
                System.out.println("Exiting");
                System.exit(0);
            }
            f = new File(bagFile);
        } while (!(f.isFile()) || !(f.canRead()) || f.isDirectory());
        ArrayList<Integer> bag1Range = readFile(bagFile);

        do {
            System.out.println("Please enter location of bag number 2 to load: ");
            bagFile = input.nextLine();
            if (bagFile.equalsIgnoreCase("e")) {
                System.out.println("Exiting");
                System.exit(0);
            }
            f = new File(bagFile);
        } while (!(f.isFile()) || !(f.canRead()) || f.isDirectory());
        ArrayList<Integer> bag2Range = readFile(bagFile);

        initialiseBags(bag0Range, bag1Range, bag2Range, playerNum);

    }

    private void checkExit(String input) {
        if (input.equalsIgnoreCase("e")) {
            System.out.println("Exiting");
            System.exit(0);
        }
    }

    private ArrayList<Integer> readFile(String bagFile) {

        ArrayList<Integer> result = new ArrayList<Integer>();
        try (BufferedReader r = new BufferedReader(new FileReader(bagFile))) {
            String read = r.readLine();
            String[] lines = read.split(",");
            for (String line : lines) {

                try {
                    int number = Integer.parseInt(line);
                    if (number > 0) {
                        result.add(number);
                    }
                    else {
                        System.out.println("Pebble sie must be greater than 0");
                    }

                } catch (NumberFormatException e) {
                     System.out.println("Pebble must be an Integer");
                }
            }

        }
        catch ( IOException e)  {
            System.out.println("BufferedReader/FileReader Error Occurred");
        }
        return result;
    }

    public void initialiseBags(ArrayList<Integer> range0, ArrayList<Integer> range1, ArrayList<Integer> range2, int playerNum) {

        ArrayList<Integer> contents0 = new ArrayList<Integer>();
        ArrayList<Integer> contents1 = new ArrayList<Integer>();
        ArrayList<Integer> contents2 = new ArrayList<Integer>();

        Random r = new Random();

        for (int i = 1; i < (playerNum*11); i++) {
            int randomIndex0 = r.nextInt(range0.size());
            contents0.add(range0.get(randomIndex0));
            int randomIndex1 = r.nextInt(range1.size());
            contents1.add(range1.get(randomIndex1));
            int randomIndex2 = r.nextInt(range2.size());
            contents2.add(range2.get(randomIndex2));
        }

        wb0 = new WhiteBag(new ArrayList<Integer>(), "wb0");
        wb1 = new WhiteBag(new ArrayList<Integer>(), "wb1");
        wb2 = new WhiteBag(new ArrayList<Integer>(), "wb2");

        bb0 = new BlackBag(contents0, "bb0", wb0);
        bb1 = new BlackBag(contents1, "bb1", wb1);
        bb2 = new BlackBag(contents2, "bb2", wb2);
    }

    public static class Player implements Runnable {

        PebbleGame game;
        private int playerNum;
        private int maxPlayers;
        private String outputFileName;
        private ArrayList<Integer> hand = new ArrayList<Integer>();

        public Player (PebbleGame game, int playerNum, int maxPlayers, String outputFileName) {
            this.game = game;
            this.playerNum = playerNum;
            this.maxPlayers = maxPlayers;
            this.outputFileName = outputFileName;
            this.hand = hand;
        }

        public void run() {
            while (!(game.checkWin(this))) {  //check game is not won
                if (this.playerNum == game.currentPlayer.get()) { //if it is current players turn
                    synchronized (game) { //locking game object to you, nobody else has access
                        try {
                            game.takeTurn(this); //main gameplay
                            game.currentPlayer.getAndIncrement(); //next player set
                            if (this.playerNum == this.maxPlayers) { //check if last player
                                game.currentPlayer.set(1); //reset to player 1
                                game.notifyAll(); //releases lock, all threads awake
                            }
                            else {
                                game.wait(); //if not last play and taken turn, release lock on game object
                            }
                        } catch (InterruptedException e) {
                            System.out.println("Thread Finished: InterruptedException");
                            e.printStackTrace();
                        }
                    }
                }
            }
            Thread.currentThread().interrupt();
        }
    }
}

