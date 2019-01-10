package pl.gd.itstartup.core.cards.hrcards;

public class NiceGentleman extends HRCard {


    @Override
    public int getMaxBurnoutPoints() {
        return 4;
    }

    @Override
    public int getPrice() {
        return 2;
    }

    @Override
    public int howManyExistInPack() {
        return 1;
    }

    @Override
    protected String getName() {
        return "Miły Pan z HR";
    }
}
