package pl.gd.itstartup.cards.programercards;

import pl.gd.itstartup.Player;
import pl.gd.itstartup.cards.Card;

import java.util.List;

public class CopyPasteDeveloper extends ProgrammerCard {

    public CopyPasteDeveloper(int id) {
        super(id);
    }
    @Override
    public int getPrice() {
        return 1;
    }
    @Override
    public String getName() {
        return "Copy Paste Developer";
    }
    @Override
    public int getMaxBurnoutPoints() {
        return 3;
    }
    @Override
    public int getPoints() {
        return 2;
    }
    @Override
    public void onTable(List<Player> opponents, Player owner) {

    }
    @Override
    public void onStart(List<Player> opponents, Player owner) {
        for (Card myCard : owner.getCardsOnTable()) {
            if (myCard instanceof ProgrammerCard) {
                ((ProgrammerCard) myCard).addBurnoutPoint();
            }

        }
    }
}
