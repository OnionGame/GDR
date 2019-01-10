package pl.gd.itstartup.core.cards.knownlagecards;

public class DependenyInjection extends KnowledgeCard {
    @Override
    public int getPrice() {
        return 2;
    }
    @Override
    protected String getName() {
        return "Wstrzykiwanie zależności";
    }
}
