import java.util.ArrayList;

class WhiteBag extends Bag {

    public WhiteBag(ArrayList<Integer> contents) {
        super(contents);
    }

    public void emptyToBlack() {
    }

    public void addToWhite(ArrayList<Integer> contents, int pebble) {
        contents.add(pebble);
    }

}
