import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class PebbleGame {

    AtomicInteger currentPlayer = new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {

        final PebbleGame game = new PebbleGame();
        int playerNum = 0;
        int p;

        Scanner input = new Scanner(System.in);
        System.out.println("Enter Number of Players:  ");
        try {
            String inputLine = input.nextLine();
            //checkExit(inputLine);
            playerNum = Integer.parseInt(inputLine);
        }
        catch (NumberFormatException e) {
            System.out.println("Input Invalid. Must be Integer");
        }

        for (int i = 1; i <= playerNum; i++) {
            Player player = new Player(game, i, playerNum);
            Thread playerThread = new Thread(player);
            playerThread.start();
        }
    }

    public void takeTurn(ArrayList<Integer> hand) {
        //if hand empty, take 10 pebbles from random black bag
        System.out.println(this.currentPlayer + " is taking a turn. Hand: " + hand);
    }

    /*
    public void getBagFileLocations() {

        Scanner input = new Scanner(System.in);
        File f;
        String bagFile;

        do {
            System.out.println("Please enter location of bag number 0 to load: ");
            bagFile = input.nextLine();
            checkExit(bagFile);
            f = new File(bagFile);
        } while (!(f.isFile()) || !(f.canRead()) || f.isDirectory());
        ArrayList<Integer> blackBag0Range = readFile(bagFile);
     
        do {
            System.out.println("Please enter location of bag number 1 to load: "); 
            bagFile = input.nextLine();
            checkExit(bagFile);
            f = new File(bagFile);
        } while (!(f.isFile()) || !(f.canRead()) || f.isDirectory());
        ArrayList<Integer> blackBag1Range = readFile(bagFile);

        do {
            System.out.println("Please enter location of bag number 2 to load: ");
            bagFile = input.nextLine();
            checkExit(bagFile);
            f = new File(bagFile);
        } while (!(f.isFile()) || !(f.canRead()) || f.isDirectory());
        ArrayList<Integer> blackBag2Range = readFile(bagFile);


            if !(positiveBag){
            System.out.print("All files must include strictly positive numbers.");
            System.exit(0);
            }

        

    }

    public void checkExit(String input) {
        if (input.equalsIgnoreCase("e")) {
            System.out.println("Exiting");
            System.exit(0);
        }
    }
    
    public ArrayList<Integer> readFile(String bagFile) {

        ArrayList<Integer> result = new ArrayList<Integer>();
        try (BufferedReader r = new BufferedReader(new FileReader(bagFile))) {
            String line = r.readLine();
            String[] lines = line.split(",");
            for (String i : lines) {
                if !(i > 0) {
                    positiveBag = false;
                        }else {
                        result.add(Integer.parseInt(i));
                        }
                        result.add(Integer.parseInt(i));
            }
        }
        catch (IOException e)  {
            System.out.println("Error Occurred");
            return null;
        }
        return result;
    }

    public void initialiseBlackBags(ArrayList<Integer> blackBagRange0, ArrayList<Integer> blackBagRange1, ArrayList<Integer> blackBagRange2) {
        ArrayList<Integer> contents0 = new ArrayList<Integer>();
        ArrayList<Integer> contents1 = new ArrayList<Integer>();
        ArrayList<Integer> contents2 = new ArrayList<Integer>();

        Random r = new Random();

        for (int i = 0; i < (PLAYERS*11)-1; i++) {
            int randomIndex0 = r.nextInt(blackBagRange0.size());
            contents0.add(blackBagRange0.get(randomIndex0));
            int randomIndex1 = r.nextInt(blackBagRange1.size());
            contents1.add(blackBagRange1.get(randomIndex1));
            int randomIndex2 = r.nextInt(blackBagRange2.size());
            contents2.add(blackBagRange2.get(randomIndex2));
        }

        bb0 = new BlackBag(contents0);
        bb1 = new BlackBag(contents1);
        bb2 = new BlackBag(contents2);

        System.out.println(contents0);
        System.out.println(contents1);
        System.out.println(contents2);

    }
    */
    public static class Player implements Runnable {

        PebbleGame game;
        private int state;
        private int maxplayers;
        private ArrayList<Integer> hand = new ArrayList<Integer>();

        public Player (PebbleGame game, int state, int maxplayers) {
            this.game = game;
            this.state = state;
            this.maxplayers = maxplayers;
            hand.add(state);
            this.hand = hand;
        }

        public void run() { //called from .start()
            while (true) {  //check current player has not won
                if (this.state == game.currentPlayer.get()) { //if it is current players turn
                    synchronized (game) { //locking game object to you, nobody else has access
                        try {
                            game.takeTurn(this.hand); //main gameplay
                            game.currentPlayer.getAndIncrement(); //next player set
                            if (this.state == this.maxplayers) { //check if last player
                                game.currentPlayer.set(1); //reset to player 1
                                game.notifyAll(); //releases lock, all threads awake
                            }
                            else {
                                game.wait(); //if not last play and taken turn, release lock on game object
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

