package pl.gd.itstartup.core.cards.programercards;

import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.DoOnStart;

public class CopyPasteDeveloper extends ProgrammerCard implements DoOnStart {

    @Override
    public int getPrice() {
        return 1;
    }

    @Override
    protected String getName() {
        return "Copy Paste Developer";
    }

    @Override
    public int getMaxBurnoutPoints() {
        return 3;
    }

    @Override
    public int getPoints() {
        return 2;
    }

    @Override
    public void doStaff(Player player, Game game) {
        player.getCardsOnHands().forEach(Card::addBurnoutPoint);
    }
}
