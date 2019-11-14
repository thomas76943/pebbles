import java.util.ArrayList;

public class Bag {

    public volatile ArrayList<Integer> contents = new ArrayList<Integer>();

    public Bag (ArrayList<Integer> contents) {
        this.contents = contents;
    }

    public synchronized ArrayList<Integer> getContents() {
        return contents;
    }


}