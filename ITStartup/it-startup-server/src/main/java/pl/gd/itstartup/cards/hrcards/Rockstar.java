package pl.gd.itstartup.cards.hrcards;

public class Rockstar extends HRCard {
    public Rockstar(int id) {
        super(id);
    }


    @Override
    public int getPrice() {
        return 3;
    }
    @Override
    public String getName() {
        return "Rockstar Recruiter";
    }
}
