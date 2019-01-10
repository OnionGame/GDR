package pl.gd.itstartup.core.cards.hrcards;

import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.DoOnStart;

public class OfficeManager extends HRCard implements DoOnStart {


    @Override
    public int getPrice() {
        return 2;
    }

    @Override
    protected String getName() {
        return "Office Manager";
    }

    @Override
    public void doStaff(Player player, Game game) {
        player.getCardsOnTable().forEach(Card::removeBurnoutPoint);
    }
}
