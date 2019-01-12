package pl.gd.itstartup.core.cards.programercards;

import pl.gd.itstartup.core.cards.AdditionalCards;

public class SeniorBackend extends ProgrammerCard implements AdditionalCards {

    @Override
    public int getPrice() {
        return 4;
    }

    @Override
    protected String getName() {
        return "Senior Backend Developer";
    }

    @Override
    public int getPoints() {
        return 2;
    }

    @Override
    public int getAll() {
        return 5;
    }

    @Override
    public int getChoose() {
        return 2;
    }
}
