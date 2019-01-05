package pl.gd.itstartup;

import com.google.common.base.Stopwatch;
import org.reflections.Reflections;
import pl.gd.itstartup.cards.Card;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CardsFactory {
    public static List<Card> createCards() {
        int id = 0;
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            List<Card> cards = new ArrayList<Card>();
            Reflections reflections = new Reflections("pl.gd.itstartup.cards");
            List<Class<? extends Card>> classes = reflections.getSubTypesOf(Card.class).stream()
                    .sorted((Comparator.comparing(Class::getSimpleName)))
                    .collect(Collectors.toList());

            for (Class clazz : classes) {

                if (!Modifier.isAbstract(clazz.getModifiers())) {
                    Card card = (Card) clazz.getConstructors()[0].newInstance(id++);
                    cards.add(card);
                    for (int i =0;i<card.howManyExistInPack() - 1;i++){
                        cards.add((Card) clazz.getConstructors()[0].newInstance(id++));
                    }
                }

            }
            System.out.println("Creating cards: " + stopwatch);

            return cards;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
