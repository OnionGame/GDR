package pl.gd.itstartup.core.cards.programercards;

public class Darek extends ProgrammerCard {

    @Override
    public int getPrice() {
        return 1;
    }

    @Override
    protected String getName() {
        return "Demotywyjący Darek";
    }

    @Override
    public int getPoints() {
        return 3;
    }


    public int getMaxBurnoutPoints() {
        return 5;
    }
}
