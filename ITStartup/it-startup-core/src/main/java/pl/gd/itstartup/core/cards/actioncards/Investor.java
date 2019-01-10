package pl.gd.itstartup.core.cards.actioncards;

import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.DoOnStart;

public class Investor extends ActionCard implements DoOnStart {

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    protected String getName() {
        return "Investor";
    }

    @Override
    public int howManyExistInPack() {
        return 4;
    }

    @Override
    public void doStaff(Player player, Game game) {
        player.addResources(2);
    }
}
