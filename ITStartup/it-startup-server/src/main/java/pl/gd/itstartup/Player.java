package pl.gd.itstartup;

import pl.gd.itstartup.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private List<Card> cardsOnHands = new ArrayList<Card>();
    private List<Card> cardsOnTable = new ArrayList<Card>();
    private int points = 0;
    private int resources = 0;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Card> getCardsOnHands() {
        return cardsOnHands;
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

    public void addCards(List<Card> cards) {
        this.cardsOnHands.addAll(cards);
    }

    public void removeCards(List<Card> cards) {
        this.cardsOnTable.removeAll(cards);
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

}
