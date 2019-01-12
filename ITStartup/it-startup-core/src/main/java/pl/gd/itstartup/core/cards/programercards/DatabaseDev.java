package pl.gd.itstartup.core.cards.programercards;

import com.google.common.collect.ImmutableList;
import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.DoOnStart;
import pl.gd.itstartup.core.cards.actioncards.ActionCard;

import java.util.List;

public class DatabaseDev extends ProgrammerCard implements DoOnStart {

    @Override
    public int getPrice() {
        return 4;
    }

    @Override
    protected String getName() {
        return "Database Developer";
    }

    @Override
    public int getPoints() {
        return 2;
    }


    public int getMaxBurnoutPoints() {
        return 4;
    }

    @Override
    public void doStaff(Player player, Game game) {
        List<Card> cards = game.getCardsFromStack(2);
        cards.stream().filter(c -> c instanceof ActionCard).forEach(c -> player.addPromotion(c, c.getPrice()));
        player.addCardsToHand(cards);
    }
}
