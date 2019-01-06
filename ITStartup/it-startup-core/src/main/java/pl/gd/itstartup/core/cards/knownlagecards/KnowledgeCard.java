package pl.gd.itstartup.core.cards.knownlagecards;

import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.CardType;

import java.util.Optional;

public abstract class KnowledgeCard extends Card {

    private Card owner;

    public KnowledgeCard(int id) {
        super(id);
    }

    public int getPoints() {
        return getPrice() + 1;
    }

    @Override
    public CardType getType() {
        return CardType.KNOWLEDGE;
    }

    @Override
    public int howManyExistInPack() {
        return 2;
    }

    @Override
    public int getBurnoutPoints() {
        return owner == null ? 0 : owner.getBurnoutPoints();
    }

    public Optional<Card> getOwner() {
        return Optional.ofNullable(owner);
    }

    public void setOwner(Card owner) {
        this.owner = owner;
    }
}
