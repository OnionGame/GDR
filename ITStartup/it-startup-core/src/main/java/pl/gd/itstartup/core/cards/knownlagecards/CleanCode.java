package pl.gd.itstartup.core.cards.knownlagecards;

public class CleanCode extends KnowledgeCard {
    public CleanCode(int id) {
        super(id);
    }
    @Override
    public int getPrice() {
        return 2;
    }
    @Override
    protected String getName() {
        return "Clean Code";
    }
}
