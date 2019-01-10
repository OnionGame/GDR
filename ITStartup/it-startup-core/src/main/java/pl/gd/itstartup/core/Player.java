package pl.gd.itstartup.core;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import pl.gd.itstartup.core.cards.AdditionalPoints;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.Worker;
import pl.gd.itstartup.core.cards.hrcards.HRCard;
import pl.gd.itstartup.core.cards.knownlagecards.KnowledgeCard;
import pl.gd.itstartup.core.cards.programercards.ProgrammerCard;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Player implements Serializable {

    private String name;
    private List<Card> cardsOnHands = new ArrayList<Card>();
    private List<Card> cardsOnTable = new ArrayList<Card>();
    private Multimap<Player, Card> outsourcing = HashMultimap.create();
    private int points = 0;
    private int resources = 0;
    private Color color;
    private int tourNumber = 1;


    public Player(String name) {
        this.name = name;
    }

    void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public List<Card> getCardsOnHands() {
        return cardsOnHands;
    }

    public Color getColor() {
        return color;
    }

    public List<Card> getCardsOnTable() {
        return cardsOnTable;
    }

    public int getPoints() {
        return points;
    }

    public int getResources() {
        return resources;
    }

    void addCardsToHand(List<Card> cards) {
        this.cardsOnHands.addAll(cards);
    }


    void removeCardsFromTable(List<Card> cards) {
        this.cardsOnTable.removeAll(cards);
    }

    boolean hasCard(Card card) {
        return this.cardsOnTable.contains(card);
    }

    void addCardsToTable(Card card) {
        this.cardsOnTable.add(card);
    }

    void addPoints(int points) {
        this.points += points;
    }


    public void addResources(int resources) {
        this.resources += resources;
    }

    private void removeResources(int resources) {
        this.resources -= resources;
    }

    void putCard(Card selectedCard) {
        removeResources(selectedCard.getPrice());
        cardsOnHands.remove(selectedCard);
        cardsOnTable.add(selectedCard);
    }

    List<Card> endTour() {
        tourNumber++;
        List<ProgrammerCard> programmers = getProgrammerCards();

        addPoints(programmers.stream()
                .mapToInt(ProgrammerCard::getPoints)
                .sum());
        addPoints(getKnowledgeCards().stream()
                .mapToInt(KnowledgeCard::getPoints)
                .sum());
        int programersSize = getProgrammerCards().size();
        getAdditionalCards().forEach(c -> addPoints(c.getAdditionalPoints() * programersSize));

        getCardsOnTable().forEach(Card::addBurnoutPoint);
        List<Card> burtCards = getCardsOnTable().stream().filter(Card::isBurnt).collect(Collectors.toList());
        cardsOnTable.removeAll(burtCards);
        return burtCards;
    }

    void clearTable() {
        cardsOnTable.clear();
    }

    public int getTourNumber() {
        return tourNumber;
    }

    public List<ProgrammerCard> getProgrammerCards() {
        return getCardsOnTable().stream()
                .filter(card -> card instanceof ProgrammerCard)
                .map(card -> (ProgrammerCard) card)
                .collect(Collectors.toList());
    }

    private List<KnowledgeCard> getKnowledgeCards() {
        return getCardsOnTable().stream()
                .filter(card -> card instanceof KnowledgeCard)
                .map(card -> (KnowledgeCard) card)
                .collect(Collectors.toList());
    }

    List<HRCard> getHRCards() {
        return getCardsOnTable().stream()
                .filter(card -> card instanceof HRCard)
                .map(card -> (HRCard) card)
                .collect(Collectors.toList());
    }

    public List<Card> getWorkerCards() {
        return getCardsOnTable().stream()
                .filter(card -> card instanceof Worker)
                .collect(Collectors.toList());
    }

    private List<AdditionalPoints> getAdditionalCards() {
        return getCardsOnTable().stream()
                .filter(card -> card instanceof AdditionalPoints)
                .map(card -> (AdditionalPoints) card)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", cardsOnHands=" + cardsOnHands.size() +
                ", cardsOnTable=" + cardsOnTable.size() +
                '}';
    }

    void addOutsourcingCard(Card card, Player opponent) {
        outsourcing.put(opponent, card);
    }

    public void giveOtsourcingCard(Player opponent) {
//        if (outsourcing.containsKey(opponent)) {
//            Card card = outsourcing.get(opponent);
//            removeCardsFromTable(ImmutableList.of(card));
//            opponent.addCardsToTable(card);
//            outsourcing.remove(opponent, card);
//        }
    }
}
