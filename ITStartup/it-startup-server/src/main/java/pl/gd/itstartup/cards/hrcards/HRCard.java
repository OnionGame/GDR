package pl.gd.itstartup.cards.hrcards;

import pl.gd.itstartup.cards.CardType;
import pl.gd.itstartup.cards.otherworkers.WorkerCard;

public abstract class HRCard extends WorkerCard {
    public HRCard(int id) {
        super(id);
    }

    @Override
    public CardType getType() {
        return CardType.HR;
    }
}
