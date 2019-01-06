package pl.gd.itstartup.core.cards.hrcards;

public class Rockstar extends HRCard {
    public Rockstar(int id) {
        super(id);
    }


    @Override
    public int getPrice() {
        return 3;
    }
    @Override
    protected String getName() {
        return "Rockstar Recruiter";
    }
}
