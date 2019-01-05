package pl.gd.itstartup.cards.programercards;

import pl.gd.itstartup.Player;
import pl.gd.itstartup.cards.CardType;
import pl.gd.itstartup.cards.otherworkers.WorkerCard;

import java.util.List;

public abstract class ProgrammerCard extends WorkerCard {

    public ProgrammerCard(int id) {
        super(id);
    }

    public abstract int getPoints();

    public abstract void onTable(List<Player> players, Player owner);

    public abstract void onStart(List<Player> players, Player owner);

    @Override
    public CardType getType() {
        return CardType.PROGRAMMER;
    }
}
