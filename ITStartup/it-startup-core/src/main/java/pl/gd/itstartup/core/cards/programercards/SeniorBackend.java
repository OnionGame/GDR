package pl.gd.itstartup.core.cards.programercards;

public class SeniorBackend extends ProgrammerCard {

    @Override
    public int getPrice() {
        return 4;
    }

    @Override
    protected String getName() {
        return "Senior Backend Developer";
    }

    @Override
    public int getPoints() {
        return 2;
    }

}
