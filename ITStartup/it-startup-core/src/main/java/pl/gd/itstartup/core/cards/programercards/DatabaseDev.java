package pl.gd.itstartup.core.cards.programercards;

public class DatabaseDev extends ProgrammerCard {

    @Override
    public int getPrice() {
        return 4;
    }

    @Override
    protected String getName() {
        return "Database Developer";
    }

    @Override
    public int getPoints() {
        return 2;
    }


    public int getMaxBurnoutPoints() {
        return 4;
    }
}
