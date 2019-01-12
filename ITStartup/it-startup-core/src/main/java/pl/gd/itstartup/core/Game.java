package pl.gd.itstartup.core;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.DoOnStart;
import pl.gd.itstartup.core.cards.actioncards.Outsourcing;
import pl.gd.itstartup.core.cards.knownlagecards.KnowledgeCard;
import pl.gd.itstartup.core.cards.programercards.AmbitiousIntern;
import pl.gd.itstartup.core.cards.programercards.Ninja;

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
    private List<String> names = ImmutableList.of("Grzesio", "Natalcia", "Jaś", "Majłgosia");
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


    public List<Player> getPlayers() {
        return players;
    }

    public Player addPlayer(String playerName) {
        Player player = new Player(names.get(players.size()));
        player.setColor(colors.get(players.size()));
        player.addResources(4);
        player.addCardsToHand(getCardsFromStack(5));
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

    public void putDarekCard(String playerName, Card selectedCard, Card worker) {
        putCard(playerName, selectedCard);
        worker.addBurnoutPoint();
        getKnownlageCards(worker).forEach(Card::addBurnoutPoint);
        burntOpponents(playerName);
    }

    public void putAditionalCards(String playerName, Card selectedCard, List<Card> cardsFromStack, List<Card> aditionalCards) {
        putCard(playerName, selectedCard);
        returnCardsOnStack(cardsFromStack);
        getPlayerByName(playerName).addCardsToHand(aditionalCards);
        countAllCards();
    }

    public void putBolekCard(String playerName, Card selectedCard, Card worker) {
        putCard(playerName, selectedCard);
        if (worker != null) {
            Preconditions.checkArgument(getOutsourcingCards().contains(worker), "Outsourcing Card:" + worker.toString());
            Player player = getPlayerByName(playerName);
            player.removeCardsFromTable(ImmutableList.of(worker));
            player.addCardsToHand(ImmutableList.of(worker));
            List<KnowledgeCard> knowlageCards = getKnownlageCards(worker);
            player.removeCardsFromTable(knowlageCards);
            player.addCardsToHand(knowlageCards);
        }
        countAllCards();
    }

    public void putCardWithTransfer(String playerName, Card selectedCard, Card worker) {
        Player opponent = getOpponent(playerName, worker);
        Player player = getPlayerByName(playerName);
        if (selectedCard instanceof Outsourcing) {
            player.addOutsourcingCard(worker, opponent);
            opponent.addResources(selectedCard.getPrice());
        }
        transferCard(playerName, worker);
        putCard(playerName, selectedCard);
    }

    public void transferCard(String playerName, Card card) {
        Player opponent = getOpponent(playerName, card);
        Player player = getPlayerByName(playerName);
        opponent.removeCardsFromTable(ImmutableList.of(card));
        player.addCardsToTable(card);

        List<KnowledgeCard> knowlageCards = getKnownlageCards(card);
        opponent.removeCardsFromTable(knowlageCards);
        player.addAllCardsToTable(knowlageCards);

        countAllCards();
    }

    public void putKnowledgeCard(String playerName, KnowledgeCard selectedCard, Card programmerCard) {
        if (programmerCard instanceof AmbitiousIntern || programmerCard instanceof Ninja) {
            int resourcesToAdd = selectedCard.getPrice() > 2 ? 2 : selectedCard.getPrice();
            getPlayerByName(playerName).addResources(resourcesToAdd);
        }
        putCard(playerName, selectedCard);
        selectedCard.setOwner(programmerCard);
    }

    public void end(String playerName) {
        Player player = getPlayerByName(playerName);
        player.endTour();
        returnCardsOnStack(player.getBurntCards());

        player.addCardsToHand(getCardsFromStack(player.getHRCards().size()));
        makeMovePlayer = players.get(i % players.size());
        makeMovePlayer.addResources(getAmountOfResources());
        makeMovePlayer.addCardsToHand(getCardsFromStack(1));
        i++;

        getOpponentsOf(playerName).forEach(opponent -> opponent.giveMyOutsourcingCard(player));
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

    public void burntOpponents(String playerName) {
        List<Card> burntCards = getOpponentsOf(playerName).stream()
                .map(Player::getBurntCards)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        returnCardsOnStack(burntCards);
    }

    private int getAmountOfResources() {
        int tourNumber = makeMovePlayer.getTourNumber();
        if (tourNumber > 8) {
            return 8;
        }
        return tourNumber;
    }

    private Player getOpponent(String playerName, Card worker) {
        return getOpponentsOf(playerName).stream().filter(player -> player.hasCard(worker)).findFirst().get();
    }

    public List<Card> getCardsFromStack(int i) {
        List<Card> firstCards = ImmutableList.copyOf(cardsOnStack.subList(0, i));
        cardsOnStack.removeAll(firstCards);
        return firstCards;
    }

    private void returnCardsOnStack(List<Card> cards) {
        cards.forEach(Card::clearBurnout);
        cardsOnStack.addAll(cards);
        countAllCards();
    }

    public void putCardsOnStartStack(List<Card> cards) {
        cardsOnStack.addAll(0, cards);
        countAllCards();
    }

    private void countAllCards() {
        long onTable = players.stream().map(Player::getCardsOnTable).mapToLong(Collection::size).sum();
        long onHands = players.stream().map(Player::getCardsOnHands).mapToLong(Collection::size).sum();
        long all = cardsOnStack.size() + onTable + onHands;
        if (all != 64) {
            System.err.println("Liczba kard się nie zgadza. Jest:" + all);
        }
    }

    private List<KnowledgeCard> getKnownlageCards(Card owner) {
        return players.stream()
                .map(Player::getKnowledgeCards)
                .flatMap(Collection::stream)
                .filter(knowledgeCard -> owner.equals(knowledgeCard.getOwner().orElse(null)))
                .collect(Collectors.toList());
    }

    private List<Card> getOutsourcingCards() {
        return players.stream()
                .map(Player::getOutsourcingCards)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public void moveKnowlage(String playerName, KnowledgeCard knowlage, Card programer) {
        Player player = getPlayerByName(playerName);
        player.removeResources(player.calculateMovePrice(knowlage));
        knowlage.setOwner(programer);
    }
}
