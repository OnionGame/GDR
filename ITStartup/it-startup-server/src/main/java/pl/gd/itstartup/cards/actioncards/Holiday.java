package pl.gd.itstartup.cards.actioncards;

import pl.gd.itstartup.cards.Card;

public class Holiday extends ActionCard {
    public Holiday(int id) {
        super(id);
    }

    public int getPrice() {
        return 3;
    }

    public String getName() {
        return "Urlop w Bieszczadach";
    }
}
