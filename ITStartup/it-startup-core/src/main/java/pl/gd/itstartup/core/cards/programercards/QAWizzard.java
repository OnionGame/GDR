package pl.gd.itstartup.core.cards.programercards;

import pl.gd.itstartup.core.Player;

import java.util.List;

public class QAWizzard extends ProgrammerCard {

    public QAWizzard(int id) {
        super(id);
    }

    @Override
    public int getPrice() {
        return 3;
    }

    @Override
    protected String getName() {
        return "QA Wizzard Engineer";
    }

    @Override
    public int getPoints() {
        return 2;
    }

    @Override
    public void onTable(List<Player> players, Player owner) {

    }

    @Override
    public void onStart(List<Player> players, Player owner) {

    }


}
