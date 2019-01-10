package pl.gd.itstartup.core.cards.actioncards;

public class Debt extends ActionCard {

    @Override
    public int getPrice() {
        return 4;
    }

    @Override
    protected String getName() {
        return "Dług technologiczny";
    }

    @Override
    public int howManyExistInPack() {
        return 1;
    }
}
