package pl.gd.itstartup.core.cards.programercards;

import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.CardType;

import java.util.List;

public abstract class ProgrammerCard extends Card {

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
