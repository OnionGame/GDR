package pl.gd.itstartup.core.cards.knownlagecards;

public class DomainKnowledge extends KnowledgeCard {
    public DomainKnowledge(int id) {
        super(id);
    }
    @Override
    public int getPrice() {
        return 3;
    }
    @Override
    protected String getName() {
        return "Wiedza domenowa";
    }
}
