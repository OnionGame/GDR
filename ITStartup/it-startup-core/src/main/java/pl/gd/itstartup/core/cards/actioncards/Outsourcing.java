package pl.gd.itstartup.core.cards.actioncards;

import pl.gd.itstartup.core.cards.TransferCard;

public class Outsourcing extends ActionCard implements TransferCard {

    public int getPrice() {
        return 2;
    }

    protected String getName() {
        return "Outsourcing";
    }
}
