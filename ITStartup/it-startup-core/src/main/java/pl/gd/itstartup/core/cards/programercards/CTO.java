package pl.gd.itstartup.core.cards.programercards;

import com.google.common.collect.ImmutableList;
import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.DoOnStart;
import pl.gd.itstartup.core.cards.actioncards.ActionCard;
import pl.gd.itstartup.core.cards.knownlagecards.KnowledgeCard;

import java.util.List;

public class CTO extends ProgrammerCard implements DoOnStart {

    @Override
    public int getPrice() {
        return 5;
    }

    @Override
    public int howManyExistInPack() {
        return 1;
    }

    @Override
    protected String getName() {
        return "Asertywny CTO";
    }

    @Override
    public int getPoints() {
        return 4;
    }

    @Override
    public void doStaff(Player player, Game game) {
        List<Card> cards = game.getCardsFromStack(1);
        cards.stream().filter(c -> c instanceof ProgrammerCard).forEach(c -> player.addPromotion(c, c.getPrice()));
        player.addCardsToHand(cards);
    }
}
