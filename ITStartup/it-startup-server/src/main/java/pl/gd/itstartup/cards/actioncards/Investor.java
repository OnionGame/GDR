package pl.gd.itstartup.cards.actioncards;

public class Investor extends ActionCard {
    public Investor(int id) {
        super(id);
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getName() {
        return "Investor";
    }

    @Override
    public int howManyExistInPack() {
        return 4;
    }
}
