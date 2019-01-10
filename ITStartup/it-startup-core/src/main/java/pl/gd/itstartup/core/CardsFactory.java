package pl.gd.itstartup.core;

import org.reflections.Reflections;
import pl.gd.itstartup.core.cards.Card;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CardsFactory {
    public static List<Card> createCards() {
        try {
            List<Card> cards = new ArrayList<Card>();
            Reflections reflections = new Reflections("pl.gd.itstartup.core.cards");
            List<Class<? extends Card>> classes = reflections.getSubTypesOf(Card.class).stream()
                    .sorted((Comparator.comparing(Class::getSimpleName)))
                    .collect(Collectors.toList());

            for (Class clazz : classes) {

                if (!Modifier.isAbstract(clazz.getModifiers())) {
                    Card card = (Card) clazz.getConstructors()[0].newInstance();
                    cards.add(card);
                    for (int i = 0; i < card.howManyExistInPack() - 1; i++) {
                        cards.add((Card) clazz.getConstructors()[0].newInstance());
                    }
                }

            }
            AtomicInteger id = new AtomicInteger();
            id.incrementAndGet();
            cards.stream().sorted().forEach(card -> card.setId(id.getAndIncrement()));

            return cards;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
