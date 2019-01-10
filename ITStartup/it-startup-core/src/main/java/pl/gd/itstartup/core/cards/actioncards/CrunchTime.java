package pl.gd.itstartup.core.cards.actioncards;

public class CrunchTime extends ActionCard {

    @Override
    public int getPrice() {
        return 2;
    }

    @Override
    protected String getName() {
        return "Crunch Time";
    }

    @Override
    public int howManyExistInPack() {
        return 1;
    }
}
