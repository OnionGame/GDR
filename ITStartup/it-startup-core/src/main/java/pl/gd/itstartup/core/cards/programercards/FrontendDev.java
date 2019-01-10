package pl.gd.itstartup.core.cards.programercards;

public class FrontendDev extends ProgrammerCard {

    @Override
    public int getPrice() {
        return 2;
    }

    @Override
    protected String getName() {
        return "Nieasertywny Frontend Dev";
    }

    @Override
    public int getPoints() {
        return 1;
    }
    @Override
    public int howManyExistInPack() {
        return 1;
    }
}
