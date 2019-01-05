package pl.gd.itstartup.cards.knownlagecards;

public class DomainKnownlage extends KnownlageCard {
    public DomainKnownlage(int id) {
        super(id);
    }
    @Override
    public int getPrice() {
        return 3;
    }
    @Override
    public String getName() {
        return "Wiedza domenowa";
    }
}
