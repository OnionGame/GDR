package pl.gd.itstartup.cards.otherworkers;

import pl.gd.itstartup.cards.Card;

public abstract class WorkerCard extends Card {

    private int burnoutPoints = 0;

    public WorkerCard(int id) {
        super(id);
    }

    public int addBurnoutPoint() {
        return burnoutPoints++;
    }

    public int removeBurnoutPoint() {
        return burnoutPoints--;
    }

    public int getMaxBurnoutPoints() {
        return 2;
    }

    public int getBurnoutPoints() {
        return burnoutPoints;
    }
}
