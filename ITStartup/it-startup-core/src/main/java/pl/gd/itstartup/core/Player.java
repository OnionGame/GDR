package pl.gd.itstartup.core;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import pl.gd.itstartup.core.cards.AdditionalPoints;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.Worker;
import pl.gd.itstartup.core.cards.actioncards.Holiday;
import pl.gd.itstartup.core.cards.actioncards.Outsourcing;
import pl.gd.itstartup.core.cards.hrcards.HRCard;
import pl.gd.itstartup.core.cards.knownlagecards.KnowledgeCard;
import pl.gd.itstartup.core.cards.otherworkers.Dick;
import pl.gd.itstartup.core.cards.programercards.FrontendDev;
import pl.gd.itstartup.core.cards.programercards.K15;
import pl.gd.itstartup.core.cards.programercards.ProgrammerCard;
import pl.gd.itstartup.core.cards.programercards.QAWizzard;

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
    private Map<Card, Integer> promotion = new HashMap<>();
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

    public void addPromotion(Card card, Integer price) {
        this.promotion.put(card, price);
    }

    public int getPoints() {
        return points;
    }

    public int getResources() {
        return resources;
    }

    public void addCardsToHand(Collection<? extends Card> cards) {
        this.cardsOnHands.addAll(cards);
    }

    public void removeCardsFromTable(Collection<? extends Card> cards) {
        this.cardsOnTable.removeAll(cards);
    }

    boolean hasCard(Card card) {
        return this.cardsOnTable.contains(card);
    }

    public void addCardsToTable(Card card) {
        this.cardsOnTable.add(card);
    }

    void addAllCardsToTable(Collection<? extends Card> cards) {
        this.cardsOnTable.addAll(cards);
    }

    void addPoints(int points) {
        this.points += points;
    }

    public void addResources(int resources) {
        this.resources += resources;
    }

    public void removeResources(int resources) {
        this.resources -= resources;
    }

    void putCard(Card selectedCard) {
        if (!(selectedCard instanceof Outsourcing)) {
            int price = calculatePrice(selectedCard);
            Preconditions.checkArgument(price <= getResources(), "Lack of resources");
            removeResources(price);
        }
        cardsOnHands.remove(selectedCard);
        cardsOnTable.add(selectedCard);
    }

    public int calculatePrice(Card selectedCard) {
        if (promotion.containsKey(selectedCard)) {
            return selectedCard.getPrice() - promotion.get(selectedCard);
        }
        if (selectedCard instanceof ProgrammerCard) {
            int wizards = getWizzardsards().size();
            return selectedCard.getPrice() <= wizards ? 0 : selectedCard.getPrice() - wizards;
        }

        return selectedCard.getPrice();
    }

    public int calculateMovePrice(Card selectedCard) {
        if (selectedCard instanceof KnowledgeCard && has15K()) {
            return selectedCard.getPrice() <= 2 ? 0 : selectedCard.getPrice() - 2;
        }
        return selectedCard.getPrice();
    }

    void endTour() {
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
        Optional.ofNullable(getFrontend()).ifPresent(frontendDev -> addPoints(frontendDev.getPoints()));

        if (!hasHoliday()) {
            getCardsOnTable().forEach(Card::addBurnoutPoint);
        }
        promotion.clear();
        Optional.ofNullable(getFrontend()).ifPresent(FrontendDev::clearPower);
    }

    List<Card> getBurntCards() {
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

    List<KnowledgeCard> getKnowledgeCards() {
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

    public List<Card> getNoOutsourcingProgrammerCards() {
        return getCardsOnTable().stream()
                .filter(card -> !outsourcing.values().contains(card))
                .filter(card -> card instanceof ProgrammerCard)
                .collect(Collectors.toList());
    }

    public List<Card> getNoOutsourcingKnowledgeCardsToMove() {
        return getCardsOnTable().stream()
                .filter(card -> card instanceof KnowledgeCard)
                .filter(card -> calculateMovePrice(card) <= getResources())
                .collect(Collectors.toList());
    }

    public List<Card> getNoOutsourcingWorkerCards() {
        return getCardsOnTable().stream()
                .filter(card -> !outsourcing.values().contains(card))
                .filter(card -> card instanceof Worker)
                .collect(Collectors.toList());
    }

    List<Card> getOutsourcingCards() {
        return ImmutableList.copyOf(outsourcing.values());
    }

    private List<AdditionalPoints> getAdditionalCards() {
        return getCardsOnTable().stream()
                .filter(card -> card instanceof AdditionalPoints)
                .map(card -> (AdditionalPoints) card)
                .collect(Collectors.toList());
    }


    void addOutsourcingCard(Card card, Player opponent) {
        outsourcing.put(opponent, card);
    }

    void giveMyOutsourcingCard(Player me) {
        if (outsourcing.containsKey(me)) {
            Collection<Card> cards = outsourcing.get(me);
            removeCardsFromTable(cards);
            me.addAllCardsToTable(cards);
            outsourcing.removeAll(me);
        }
    }

    public boolean hasDick() {
        return getCardsOnTable().stream().anyMatch(card -> card instanceof Dick);
    }

    public boolean hasFrontend() {
        return getCardsOnTable().stream().anyMatch(card -> card instanceof FrontendDev);
    }

    private boolean has15K() {
        return getCardsOnTable().stream().anyMatch(card -> card instanceof K15);
    }

    private boolean hasHoliday() {
        return getCardsOnTable().stream().anyMatch(card -> card instanceof Holiday);
    }

    public FrontendDev getFrontend() {
        return (FrontendDev) getCardsOnTable().stream().filter(card -> card instanceof FrontendDev).findFirst().orElse(null);
    }

    public List<QAWizzard> getWizzardsards() {
        return getCardsOnTable().stream()
                .filter(card -> card instanceof QAWizzard)
                .map(card -> (QAWizzard) card)
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
