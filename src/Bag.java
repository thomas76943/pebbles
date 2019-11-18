import java.util.ArrayList;

public class Bag {

    protected volatile ArrayList<Integer> contents = new ArrayList<Integer>();
    private String bagName;

    public Bag (ArrayList<Integer> contents, String bagName) {
        this.contents = contents;
        this.bagName = bagName;
    }

    public ArrayList<Integer> getContents() {
        return contents;
    }

    public String getBagName() {
        return bagName;
    }

}