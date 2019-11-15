import java.util.ArrayList;
import java.util.Random;

class BlackBag extends Bag {

    private WhiteBag linkedWhite;

    public BlackBag(ArrayList<Integer> contents, WhiteBag linkedWhite) {
        super(contents);
        this.linkedWhite = linkedWhite;
    }

    public int drawFromBlack() {
        Random r = new Random();
        int index = r.nextInt(contents.size());
        int pebble = contents.get(index);
        contents.remove(index);
        return pebble;

    }

    public WhiteBag getLinkedWhite() {

        return linkedWhite;

    }

}