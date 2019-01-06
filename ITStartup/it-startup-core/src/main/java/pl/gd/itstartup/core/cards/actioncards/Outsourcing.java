package pl.gd.itstartup.core.cards.actioncards;

public class Outsourcing extends ActionCard {
    public Outsourcing(int id) {
        super(id);
    }

    public int getPrice() {
        return 2;
    }

    protected String getName() {
        return "Outsourcing";
    }
}
