package pl.gd.itstartup.cards.actioncards;

import pl.gd.itstartup.cards.Card;

public class Outsourcing extends ActionCard {
    public Outsourcing(int id) {
        super(id);
    }

    public int getPrice() {
        return 2;
    }

    public String getName() {
        return "Outsourcing";
    }
}
