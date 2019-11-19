import javax.swing.text.AbstractDocument;
import java.io.Console;
import java.util.ArrayList;
import java.util.Random;

class BlackBag extends Bag {

    private WhiteBag linkedWhite;

    /**
     * The constructor for the BlackBag subclass.
     * @param contents - the black bag's contents are constructed from the Bag superclass
     * @param bagName - the black bag's name is constructed from the Bag superclass
     * @param linkedWhite - the black bag's linked white bag is constructed
     */
    public BlackBag(ArrayList<Integer> contents, String bagName, WhiteBag linkedWhite) {
        super(contents, bagName);
        this.linkedWhite = linkedWhite;
    }

    /**
     * This method is a getter for the WhiteBag linkedWhite attribute.
     * @return - linkedWhite
     */
    public WhiteBag getLinkedWhite() {
        return linkedWhite;
    }

    /**
     * This method selects a random pebble from the Black Bag, removes the pebble from the contents attribute
     * and returns it for use in the takeTurn() method in PebbleGame. If the size of the contents attribute is 0,
     * the fillBlackFromWhite() method is called and the rest of the method can be performed.
     * @return - the pebble removed from the Black Bag's contents
     */
    public int drawFromBlack() {

        //Black Bag filled from Linked White Bag
        if (contents.size() == 0) {
            fillBlackFromWhite();
            System.out.println("Black Bag " + this.getBagName() + " Refilled");
        }

        Random r = new Random();
        int index = r.nextInt(contents.size());
        int pebble = contents.get(index);
        contents.remove(index);
        return pebble;
    }

    /**
     * This method empties the linked White Bag and fills the Black Bag through the use of the linkedWhite
     * attribute. The linkedWhite Bag's contents are then cleared.
     */
    public void fillBlackFromWhite() {

        for (int x : linkedWhite.contents) {
            contents.add(x);
        }
        linkedWhite.contents.clear();
    }

}