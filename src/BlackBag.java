import javax.swing.text.AbstractDocument;
import java.io.Console;
import java.util.ArrayList;
import java.util.Random;

class BlackBag extends Bag {

    private WhiteBag linkedWhite;

    public BlackBag(ArrayList<Integer> contents, WhiteBag linkedWhite) {
        super(contents);
        this.linkedWhite = linkedWhite;
    }

    public int drawFromBlack() {


        if (contents.size() == 0) {
            System.out.println("Contents of White: " + linkedWhite.contents);
            fillBlackFromWhite();
            System.out.println("Refilled. Contents of White: " + linkedWhite.contents);

            fillBlackFromWhite();
        }
        Random r = new Random();
        int index = r.nextInt(contents.size());
        int pebble = contents.get(index);
        contents.remove(index);
        return pebble;

    }

    public WhiteBag getLinkedWhite() {
        return linkedWhite;
    }

    public void fillBlackFromWhite() {

        for (int x : linkedWhite.contents) {
            contents.add(x);
        }
        linkedWhite.contents.clear();
    }

}