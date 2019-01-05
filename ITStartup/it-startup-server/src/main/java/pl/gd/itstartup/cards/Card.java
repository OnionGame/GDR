package pl.gd.itstartup.cards;

import java.util.Objects;

public abstract class Card {
    protected final int id;


    public Card(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract int getPrice();

    public abstract String getName();

    public abstract CardType getType();

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
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + ". " + getType().getPolishName() + " " + getName();
    }
}
