package pl.gd.itstartup.cards.actioncards;

import pl.gd.itstartup.cards.Card;
import pl.gd.itstartup.cards.CardType;

public abstract class ActionCard extends Card {


    public ActionCard(int id) {
        super(id);
    }

    public CardType getType() {
        return CardType.ACTON;
    }
}
