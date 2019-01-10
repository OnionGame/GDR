package pl.gd.itstartup.core.cards.programercards;

public class Intern extends ProgrammerCard {

    @Override
    public int getPrice() {
        return 1;
    }

    @Override
    protected String getName() {
        return "Stażysta";
    }

    @Override
    public int getPoints() {
        return 1;
    }

}
