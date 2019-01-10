package pl.gd.itstartup.core;

import com.google.common.collect.ImmutableList;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.DoOnStart;
import pl.gd.itstartup.core.cards.actioncards.Outsourcing;
import pl.gd.itstartup.core.cards.knownlagecards.KnowledgeCard;
import pl.gd.itstartup.core.cards.programercards.ProgrammerCard;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        player.addCardsToHand(getCardsFromStack(10));
        players.add(player);
        makeMovePlayer = player;
        return player;
    }

    public Player getPlayerByName(String name) {
        return players.stream()
                .filter(player -> player.getName().equals(name))
                .findFirst().orElseThrow(() -> new RuntimeException("No player with name " + name));
    }

    public List<Player> getOpponentsOf(String name) {
        return players.stream()
                .filter(player -> !player.getName().equals(name))
                .collect(Collectors.toList());
    }


    public void putCard(String playerName, Card selectedCard) {
        Player player = getPlayerByName(playerName);
        player.putCard(selectedCard);
        if (selectedCard instanceof DoOnStart) {
            ((DoOnStart) selectedCard).doStaff(player, this);
        }
    }

    public void putCardWithTransfer(String playerName, Card selectedCard, Card worker) {
        Player opponent = getOpponentsOf(playerName).stream().filter(player -> player.hasCard(selectedCard)).findFirst().get();
        Player player = getPlayerByName(playerName);
        if (selectedCard instanceof Outsourcing) {
            player.addOutsourcingCard(worker, opponent);
        }
        opponent.removeCardsFromTable(ImmutableList.of(worker));
        putCard(playerName, selectedCard);

        player.addCardsToTable(worker);

    }

    public void putKnowledgeCard(String playerName, KnowledgeCard selectedCard, ProgrammerCard programmerCard) {
        putCard(playerName, selectedCard);
        selectedCard.setOwner(programmerCard);
    }

    public void end(String playerName) {
        Player player = getPlayerByName(playerName);
        List<Card> burtCards = player.endTour();
        returnCardsOnStack(burtCards);

        player.addCardsToHand(getCardsFromStack(player.getHRCards().size()));
        makeMovePlayer = players.get(i % players.size());
        makeMovePlayer.addResources(getAmountOfResources());
        makeMovePlayer.addCardsToHand(getCardsFromStack(1));
        i++;

        getOpponentsOf(playerName).forEach(p -> p.giveOtsourcingCard(player));
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

    public void restart() {
        List<Card> cards = players.stream()
                .map(Player::getCardsOnTable)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        returnCardsOnStack(cards);
        players.forEach(Player::clearTable);

    }

    private void returnCardsOnStack(List<Card> cards) {
        cards.forEach(Card::removeBurnout);
        cardsOnStack.addAll(cards);
    }
}
