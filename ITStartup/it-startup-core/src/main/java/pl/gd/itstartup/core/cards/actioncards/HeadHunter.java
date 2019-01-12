package pl.gd.itstartup.core.cards.actioncards;

import pl.gd.itstartup.core.cards.TransferCard;

public class HeadHunter extends ActionCard implements TransferCard {

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
