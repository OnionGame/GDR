package pl.gd.itstartup.core.cards.programercards;

public class CTO extends ProgrammerCard {

    @Override
    public int getPrice() {
        return 5;
    }

    @Override
    public int howManyExistInPack() {
        return 1;
    }

    @Override
    protected String getName() {
        return "Asertywny CTO";
    }

    @Override
    public int getPoints() {
        return 4;
    }

}
