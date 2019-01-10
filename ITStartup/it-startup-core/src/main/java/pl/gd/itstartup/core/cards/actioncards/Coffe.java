package pl.gd.itstartup.core.cards.actioncards;

public class Coffe extends ActionCard {

    @Override
    public int getPrice() {
        return 2;
    }

    @Override
    protected String getName() {
        return "Ekspres do kawy";
    }

    @Override
    public int howManyExistInPack() {
        return 1;
    }
}
