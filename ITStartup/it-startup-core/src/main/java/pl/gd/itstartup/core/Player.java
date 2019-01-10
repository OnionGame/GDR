package pl.gd.itstartup.core;

import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.Worker;
import pl.gd.itstartup.core.cards.hrcards.HRCard;
import pl.gd.itstartup.core.cards.knownlagecards.KnowledgeCard;
import pl.gd.itstartup.core.cards.programercards.ProgrammerCard;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Player implements Serializable {

    private String name;
    private List<Card> cardsOnHands = new ArrayList<Card>();
    private List<Card> cardsOnTable = new ArrayList<Card>();
    private int points = 0;
    private int resources = 0;
    private Color color;
    private int tourNumber = 1;

    public Player(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
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

    public void addCardsToHand(List<Card> cards) {
        this.cardsOnHands.addAll(cards);
    }

    public void removeCardsFromHand(List<Card> cards) {
        this.cardsOnHands.removeAll(cards);
    }
    public void removeCardsFromTable(List<Card> cards) {
        this.cardsOnTable.removeAll(cards);
    }

    public void addCardsToTable(List<Card> cards) {
        this.cardsOnTable.addAll(cards);
    }

    public void addPoints(int points) {
        this.points += points;
    }


    public void addResources(int resources) {
        this.resources += resources;
    }

    public void removeResources(int resources) {
        this.resources -= resources;
    }

    public void putCard(Card selectedCard) {
        cardsOnHands.remove(selectedCard);
        cardsOnTable.add(selectedCard);
        removeResources(selectedCard.getPrice());
    }

    public List<Card> endTour() {
        tourNumber++;
        List<ProgrammerCard> programmers = getProgrammerCards();

        addPoints(programmers.stream()
                .mapToInt(ProgrammerCard::getPoints)
                .sum());
        addPoints(getKnowledgeCards().stream()
                .mapToInt(KnowledgeCard::getPoints)
                .sum());
        getCardsOnTable().forEach(Card::addBurnoutPoint);
        List<Card> burtCards = getCardsOnTable().stream().filter(Card::isBurnt).collect(Collectors.toList());
        cardsOnTable.removeAll(burtCards);
        return burtCards;
    }
    public void clearTable(){
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

    public List<HRCard> getHRCards() {
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
}
