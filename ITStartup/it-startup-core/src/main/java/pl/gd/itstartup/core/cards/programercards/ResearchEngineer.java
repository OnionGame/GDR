package pl.gd.itstartup.core.cards.programercards;

public class ResearchEngineer extends ProgrammerCard {

    @Override
    public int getPrice() {
        return 2;
    }

    @Override
    protected String getName() {
        return "Research Engineer";
    }

    @Override
    public int getPoints() {
        return 2;
    }

}
