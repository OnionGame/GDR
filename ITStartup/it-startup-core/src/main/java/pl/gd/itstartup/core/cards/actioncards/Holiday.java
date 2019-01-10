package pl.gd.itstartup.core.cards.actioncards;

import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.DoOnStart;

public class Holiday extends ActionCard implements DoOnStart {

    public int getPrice() {
        return 3;
    }

    protected String getName() {
        return "Urlop w Bieszczadach";
    }

    @Override
    public void doStaff(Player player, Game game) {
        player.getCardsOnTable().forEach(Card::removeBurnoutPoint);
    }
}
