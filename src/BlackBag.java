import javax.swing.text.AbstractDocument;
import java.io.Console;
import java.util.ArrayList;
import java.util.Random;

class BlackBag extends Bag {

    private WhiteBag linkedWhite;

    public BlackBag(ArrayList<Integer> contents, String bagName, WhiteBag linkedWhite) {
        super(contents, bagName);
        this.linkedWhite = linkedWhite;
    }

    public WhiteBag getLinkedWhite() {
        return linkedWhite;
    }

    public int drawFromBlack() {

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

    public void fillBlackFromWhite() {

        for (int x : linkedWhite.contents) {
            contents.add(x);
        }
        linkedWhite.contents.clear();
    }

}