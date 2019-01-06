package pl.gd.itstartup.core.cards.actioncards;

import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.CardType;

public abstract class ActionCard extends Card {


    public ActionCard(int id) {
        super(id);
    }
    @Override
    public int getMaxBurnoutPoints() {
        return 1;
    }

    public CardType getType() {
        return CardType.ACTON;
    }
}
