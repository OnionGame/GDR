package pl.gd.itstartup.core.cards.actioncards;

import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.AdditionalPoints;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.DoOnStart;

public class CrunchTime extends ActionCard implements DoOnStart, AdditionalPoints {


    @Override
    public int getPrice() {
        return 2;
    }

    @Override
    protected String getName() {
        return "Crunch Time";
    }

    @Override
    public int howManyExistInPack() {
        return 1;
    }

    @Override
    public int getAdditionalPoints() {
        return 2;
    }

    @Override
    public void doStaff(Player player, Game game) {
        player.getCardsOnTable().forEach(Card::addBurnoutPoint);
        player.getCardsOnTable().forEach(Card::addBurnoutPoint);
    }
}
