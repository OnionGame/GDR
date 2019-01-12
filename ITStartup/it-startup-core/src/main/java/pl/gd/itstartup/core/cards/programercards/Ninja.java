package pl.gd.itstartup.core.cards.programercards;

public class Ninja extends ProgrammerCard {

    @Override
    public int getPrice() {
        return 4;
    }

    @Override
    protected String getName() {
        return "Full Stack Ninja";
    }

    @Override
    public int getPoints() {
        return 3;
    }

}
