package pl.gd.itstartup.core;

import com.google.common.collect.ImmutableList;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.hrcards.HRCard;
import pl.gd.itstartup.core.cards.knownlagecards.KnowledgeCard;
import pl.gd.itstartup.core.cards.programercards.ProgrammerCard;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements Serializable {

    private List<Player> players = new ArrayList<>();
    private Player makeMovePlayer;
    private List<Card> cardsOnStack;
    private List<Color> colors = ImmutableList.of(Color.BLUE, Color.PINK, Color.GREEN, Color.RED);
    private int i = 0;

    public Game() {
        try {
            cardsOnStack = CardsFactory.createCards();
            Collections.shuffle(cardsOnStack);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private List<Card> getCardsFromStack(int i) {
        List<Card> firstCards = ImmutableList.copyOf(cardsOnStack.subList(0, i));
        cardsOnStack.removeAll(firstCards);
        return firstCards;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public Player addPlayer(String playerName) {
        Player player = new Player(playerName);
        player.setColor(colors.get(players.size()));
        player.addPoints(100);
        player.addCards(getCardsFromStack(10));
        players.add(player);
        makeMovePlayer = player;
        return player;
    }

    public Player getPlayerByName(String name) {
        return players.stream()
                .filter(player -> player.getName().equals(name))
                .findFirst().orElseThrow(() -> new RuntimeException("No player with name " + name));
    }

    public void putCard(String playerName, Card selectedCard) {
        getPlayerByName(playerName).putCard(selectedCard);
    }

    public void putKnowledgeCard(String playerName, KnowledgeCard selectedCard, ProgrammerCard programmerCard) {
        getPlayerByName(playerName).putCard(selectedCard);
        selectedCard.setOwner(programmerCard);
    }

    public void end(String playerName) {
        Player player = getPlayerByName(playerName);
        List<Card> burtCards = player.endTour();
        cardsOnStack.addAll(burtCards);

        player.addCards(getCardsFromStack(player.getHRCards().size()));
        makeMovePlayer = players.get(i % players.size());
        makeMovePlayer.addResources(getAmountOfResources());
        makeMovePlayer.addCards(getCardsFromStack(1));
        i++;
    }

    private int getAmountOfResources() {
        int tourNumber = makeMovePlayer.getTourNumber();
        if (tourNumber > 8) {
            return 8;
        }
        return tourNumber;
    }

    public Player getMakeMovePlayer() {
        return makeMovePlayer;
    }
}