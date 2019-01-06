package pl.gd.itstartup.core.cards.actioncards;

public class Holiday extends ActionCard {
    public Holiday(int id) {
        super(id);
    }

    public int getPrice() {
        return 3;
    }

    protected String getName() {
        return "Urlop w Bieszczadach";
    }
}
