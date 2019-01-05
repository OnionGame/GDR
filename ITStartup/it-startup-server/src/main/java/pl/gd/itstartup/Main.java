package pl.gd.itstartup;

import pl.gd.itstartup.cards.Card;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String a[]) {
        try {
            Player grzesio = new Player("Grzesio");
            Player natalcia = new Player("Natalcia");
            grzesio.addResources(5);
            natalcia.addResources(5);
            List<Card> cards = CardsFactory.createCards();
            cards.forEach(System.out::println);

            Collections.shuffle(cards);
            grzesio.addCards(cards.subList(0,4));
            grzesio.addCards(cards.subList(0,4));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
