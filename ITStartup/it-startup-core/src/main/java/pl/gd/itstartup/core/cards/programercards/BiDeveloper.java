package pl.gd.itstartup.core.cards.programercards;

import com.google.common.collect.ImmutableList;
import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.DoOnStart;
import pl.gd.itstartup.core.cards.knownlagecards.KnowledgeCard;

import java.util.List;

public class BiDeveloper extends ProgrammerCard implements DoOnStart {

    @Override
    public int getPrice() {
        return 3;
    }

    @Override
    protected String getName() {
        return "Bi Developer";
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
        List<Card> cards = game.getCardsFromStack(1);
        cards.stream().filter(c -> c instanceof KnowledgeCard).forEach(c -> player.addPromotion(c, 2));
        player.addCardsToHand(cards);
    }
}
