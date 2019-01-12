package pl.gd.itstartup.core.cards.actioncards;

import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.DoOnStart;

import java.util.Collection;

public class Wierd extends ActionCard implements DoOnStart {

    public int getPrice() {
        return 3;
    }

    protected String getName() {
        return "Dziwne... u mnie działa";
    }

    @Override
    public void doStaff(Player player, Game game) {
        game.getOpponentsOf(player.getName()).stream()
                .map(Player::getCardsOnTable)
                .flatMap(Collection::stream)
                .forEach(Card::addBurnoutPoint);
        game.burntOpponents(player.getName());
    }
}
