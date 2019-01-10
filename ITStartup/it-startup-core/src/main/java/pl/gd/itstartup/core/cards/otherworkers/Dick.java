package pl.gd.itstartup.core.cards.otherworkers;

import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.CardType;
import pl.gd.itstartup.core.cards.Worker;

public class Dick extends Card implements Worker {

    public int getPrice() {
        return 3;
    }

    protected String getName() {
        return "Tajniak z HR-u";
    }

    @Override
    public CardType getType() {
        return CardType.HR;
    }
    @Override
    public int howManyExistInPack() {
        return 2;
    }
}
