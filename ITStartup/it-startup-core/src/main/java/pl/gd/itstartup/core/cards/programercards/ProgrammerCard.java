package pl.gd.itstartup.core.cards.programercards;

import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.CardType;
import pl.gd.itstartup.core.cards.Worker;

public abstract class ProgrammerCard extends Card implements Worker {

    public abstract int getPoints();

    @Override
    public CardType getType() {
        return CardType.PROGRAMMER;
    }
}
