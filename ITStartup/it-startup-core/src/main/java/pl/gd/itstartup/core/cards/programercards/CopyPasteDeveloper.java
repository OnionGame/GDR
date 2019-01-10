package pl.gd.itstartup.core.cards.programercards;

public class CopyPasteDeveloper extends ProgrammerCard {

    @Override
    public int getPrice() {
        return 1;
    }

    @Override
    protected String getName() {
        return "Copy Paste Developer";
    }

    @Override
    public int getMaxBurnoutPoints() {
        return 3;
    }

    @Override
    public int getPoints() {
        return 2;
    }

}
