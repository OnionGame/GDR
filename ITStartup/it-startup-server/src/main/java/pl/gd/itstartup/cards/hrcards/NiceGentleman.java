package pl.gd.itstartup.cards.hrcards;

public class NiceGentleman extends HRCard {


    public NiceGentleman(int id) {
        super(id);
    }
    @Override
    public int getMaxBurnoutPoints() {
        return 4;
    }
    @Override
    public int getPrice() {
        return 2;
    }
    @Override
    public String getName() {
        return "Miły Pan z HR";
    }
}
