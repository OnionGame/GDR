package pl.gd.itstartup.core.cards.hrcards;

import pl.gd.itstartup.core.cards.TransferCard;

public class Rockstar extends HRCard implements TransferCard {


    @Override
    public int getPrice() {
        return 3;
    }

    @Override
    protected String getName() {
        return "Rockstar Recruiter";
    }
}
