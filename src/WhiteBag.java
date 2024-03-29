import java.util.ArrayList;

class WhiteBag extends Bag {

    /**
     * The constructor for the WhiteBag subclass.
     * @param contents - the White Bag's contents are constructed from the Bag superclass
     * @param bagName - the White Bag's name is constructed from the Bag superclass
     */
    public WhiteBag(ArrayList<Integer> contents, String bagName) {
        super(contents, bagName);
    }

    /**
     * This method adds a given integer (representing a pebble) to the White Bag's contents. It is synchronized as
     * players may be discarding to a WhiteBag at the same time the WhiteBag is emptying to a BlackBag.
     * @param pebble - the pebble to be added.
     */
    public void addToWhite(int pebble) {
        synchronized (contents) {
            contents.add(pebble);
        };
    }

}
