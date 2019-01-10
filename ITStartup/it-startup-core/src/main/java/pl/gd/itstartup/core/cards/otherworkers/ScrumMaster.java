package pl.gd.itstartup.core.cards.otherworkers;

import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.CardType;
import pl.gd.itstartup.core.cards.Worker;

public class ScrumMaster extends Card implements Worker {

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
}