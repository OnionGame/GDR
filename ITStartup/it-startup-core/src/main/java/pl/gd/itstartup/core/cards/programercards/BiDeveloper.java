package pl.gd.itstartup.core.cards.programercards;

public class BiDeveloper extends ProgrammerCard {

    @Override
    public int getPrice() {
        return 3;
    }

    @Override
    protected String getName() {
        return "Bi Developer";
    }

    @Override
    public int getPoints() {
        return 2;
    }


    public int getMaxBurnoutPoints() {
        return 4;
    }
}
