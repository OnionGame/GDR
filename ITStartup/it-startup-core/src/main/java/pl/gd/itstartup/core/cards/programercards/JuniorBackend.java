package pl.gd.itstartup.core.cards.programercards;

import pl.gd.itstartup.core.cards.AdditionalCards;

public class JuniorBackend extends ProgrammerCard implements AdditionalCards
{

    @Override
    public int getPrice() {
        return 2;
    }

    @Override
    protected String getName() {
        return "Junior Backend Developer";
    }

    @Override
    public int getPoints() {
        return 1;
    }

    @Override
    public int getAll() {
        return 3;
    }

    @Override
    public int getChoose() {
        return 1;
    }
}
