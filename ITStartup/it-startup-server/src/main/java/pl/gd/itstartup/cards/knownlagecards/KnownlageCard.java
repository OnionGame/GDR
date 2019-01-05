package pl.gd.itstartup.cards.knownlagecards;

import pl.gd.itstartup.cards.Card;
import pl.gd.itstartup.cards.CardType;

public abstract class KnownlageCard extends Card {

    public KnownlageCard(int id) {
        super(id);
    }
    public int getPoints() {
        return getPrice() + 1;
    }

    @Override
    public CardType getType() {
        return CardType.KNOWNLAGE;
    }
    @Override
    public int howManyExistInPack() {
        return 2;
    }
}
