package pl.gd.itstartup.core.cards.actioncards;

public class Investor extends ActionCard {

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    protected String getName() {
        return "Investor";
    }

    @Override
    public int howManyExistInPack() {
        return 4;
    }
}
