package pl.gd.itstartup.cards.otherworkers;

import pl.gd.itstartup.cards.CardType;

public class ScrumMaster extends WorkerCard {
    public ScrumMaster(int id) {
        super(id);
    }

    public int getMaxBurnoutPoints() {
        return 4;
    }

    public int getPrice() {
        return 4;
    }

    public String getName() {
        return "Scrum Master";
    }

    @Override
    public CardType getType() {
        return CardType.SCRUM_MASTER;
    }
    @Override
    public int howManyExistInPack() {
        return 0;
    }
}
