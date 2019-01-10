package pl.gd.itstartup.core.cards.actioncards;

import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.DoOnStart;

public class Debt extends ActionCard implements DoOnStart {

    @Override
    public int getPrice() {
        return 4;
    }

    @Override
    protected String getName() {
        return "Dług technologiczny";
    }

    @Override
    public int howManyExistInPack() {
        return 1;
    }

    @Override
    public void doStaff(Player player, Game game) {
        game.restart();
    }
}
