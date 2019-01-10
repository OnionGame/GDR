package pl.gd.itstartup.core.cards.hrcards;

import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.CardType;
import pl.gd.itstartup.core.cards.Worker;

public abstract class HRCard extends Card implements Worker {

    @Override
    public CardType getType() {
        return CardType.HR;
    }
}
