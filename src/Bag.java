import java.util.ArrayList;

public class Bag {

    private volatile ArrayList<Integer> contents = new ArrayList<Integer>();
    int p;

    public synchronized void setContents(ArrayList<Integer> bagContents) {
        contents = bagContents;
    }

    public synchronized ArrayList<Integer> getContents() {
        return contents;
    }

}