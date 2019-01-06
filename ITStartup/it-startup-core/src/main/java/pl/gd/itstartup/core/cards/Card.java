package pl.gd.itstartup.core.cards;

import java.io.Serializable;
import java.util.Objects;

public abstract class Card implements Serializable, Comparable<Card> {
    protected final int id;
    private int burnoutPoints = 0;

    public Card(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public abstract int getPrice();

    protected abstract String getName();

    public abstract CardType getType();

    public int addBurnoutPoint() {
        return burnoutPoints++;
    }

    public int removeBurnoutPoint() {
        return burnoutPoints--;
    }

    public int getMaxBurnoutPoints() {
        return 3;
    }

    public int getBurnoutPoints() {
        return getMaxBurnoutPoints() - burnoutPoints;
    }

    public boolean isBurnt() {
        return getBurnoutPoints() == 0;
    }

    public int howManyExistInPack() {
        return 2;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id;
    }

    @Override
    public int compareTo(Card o) {
        int typeCompare = getType().compareTo(o.getType());
        if (typeCompare != 0) {
            return typeCompare;
        }
        return getId().compareTo(o.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + ". " + getName();
    }
}
