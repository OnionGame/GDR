package pl.gd.itstartup.core.cards.knownlagecards;

public class UnitTests extends KnowledgeCard {
    @Override
    public int getPrice() {
        return 3;
    }
    @Override
    protected String getName() {
        return "Testy jednostkowe";
    }
}
