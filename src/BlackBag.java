import java.util.ArrayList;

class BlackBag extends Bag {

    private WhiteBag linkedWhite;

    public BlackBag(ArrayList<Integer> contents, WhiteBag linkedWhite) {
        super(contents);
        this.linkedWhite = linkedWhite;
    }

    public void drawFromBlack() {

    }

    public WhiteBag getLinkedWhite() {
        return linkedWhite;
    }

}