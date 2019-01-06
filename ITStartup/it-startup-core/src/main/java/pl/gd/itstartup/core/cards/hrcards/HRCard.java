package pl.gd.itstartup.core.cards.hrcards;

import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.CardType;

public abstract class HRCard extends Card {
    public HRCard(int id) {
        super(id);
    }

    @Override
    public CardType getType() {
        return CardType.HR;
    }
}
