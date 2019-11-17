import java.util.ArrayList;

class WhiteBag extends Bag {

    public WhiteBag(ArrayList<Integer> contents, String bagName) {
        super(contents, bagName);
    }

    public void addToWhite(int pebble) {
        contents.add(pebble);
    }

}
