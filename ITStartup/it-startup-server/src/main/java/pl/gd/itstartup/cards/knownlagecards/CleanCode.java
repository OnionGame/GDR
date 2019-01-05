package pl.gd.itstartup.cards.knownlagecards;

public class CleanCode extends KnownlageCard {
    public CleanCode(int id) {
        super(id);
    }
    @Override
    public int getPrice() {
        return 2;
    }
    @Override
    public String getName() {
        return "Clean Code";
    }
}
