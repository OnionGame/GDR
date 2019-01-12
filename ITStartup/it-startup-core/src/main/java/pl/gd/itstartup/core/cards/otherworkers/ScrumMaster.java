package pl.gd.itstartup.core.cards.otherworkers;

import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.*;
import pl.gd.itstartup.core.cards.knownlagecards.KnowledgeCard;

import java.util.List;

public class ScrumMaster extends Card implements Worker, AdditionalPoints, DoOnStart {

    public int getMaxBurnoutPoints() {
        return 4;
    }

    public int getPrice() {
        return 4;
    }

    protected String getName() {
        return "Scrum Master";
    }

    @Override
    public CardType getType() {
        return CardType.SCRUM_MASTER;
    }

    @Override
    public int howManyExistInPack() {
        return 2;
    }

    @Override
    public int getAdditionalPoints() {
        return 1;
    }

    @Override
    public void doStaff(Player player, Game game) {
        List<Card> cards = game.getCardsFromStack(player.getProgrammerCards().size());
        player.addCardsToHand(cards);
    }
}
