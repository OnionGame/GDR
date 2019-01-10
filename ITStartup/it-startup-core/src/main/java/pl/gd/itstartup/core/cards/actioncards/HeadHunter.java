package pl.gd.itstartup.core.cards.actioncards;

public class HeadHunter extends ActionCard {

    @Override
    public int getPrice() {
        return 5;
    }

    @Override
    protected String getName() {
        return "Head Hunter";
    }

    @Override
    public int howManyExistInPack() {
        return 1;
    }
}
