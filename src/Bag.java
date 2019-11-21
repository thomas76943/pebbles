import java.util.ArrayList;

public class Bag {

    protected volatile ArrayList<Integer> contents = new ArrayList<Integer>();
    private String bagName;

    /**
     * The constructor for the Bag base class.
     * @param contents - the bag's contents is constructed
     * @param bagName- the bag's name is constructed
     */
    public Bag (ArrayList<Integer> contents, String bagName) {
        this.contents = contents;
        this.bagName = bagName;
    }

    /**
     * This method is a getter for the ArrayList contents attribute.
     * @return - contents
     */
    public ArrayList<Integer> getContents() {
        return contents;
    }

    /**
     * This method is a getter for the String bagName attribute.
     * @return - bagName
     */
    public String getBagName() {
        return bagName;
    }

    /**
     * This method is a setter for the ArrayList< Integer> contents attribute.
     */
    public void setContents(ArrayList<Integer> newContents) {
        contents = newContents;
    }

}