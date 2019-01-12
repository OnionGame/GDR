package pl.gd.itstartup.swing;

import com.google.common.collect.Maps;
import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.AdditionalCards;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.TransferCard;
import pl.gd.itstartup.core.cards.actioncards.ActionCard;
import pl.gd.itstartup.core.cards.hrcards.Rockstar;
import pl.gd.itstartup.core.cards.knownlagecards.KnowledgeCard;
import pl.gd.itstartup.core.cards.programercards.Bolek;
import pl.gd.itstartup.core.cards.programercards.Darek;
import pl.gd.itstartup.core.cards.programercards.ProgrammerCard;
import pl.gd.itstartup.core.cards.programercards.ResearchEngineer;
import pl.gd.itstartup.rmi.ITStartupRMIServerInterface;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private Game game;
    private Player player;
    private Card selectedCard;
    private JPanel cardsPanel;
    private JPanel descriptionPanel;
    private JPanel selectedPanel;
    private boolean isSelected = false;
    private boolean wasRandom = false;
    private ITStartupRMIServerInterface server;

    public MainFrame(String playerName, ITStartupRMIServerInterface server) throws RemoteException {
        super();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setUIFont();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.server = server;
        this.game = server.getGame();
        this.player = game.addPlayer(playerName);
        setTitle(player.getName());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        createCenterPanel();
        createDescriptionPanel();
        setVisible(true);

    }

    public void refresh() throws RemoteException {
        try {
            game = server.getGame();
            player = game.getPlayerByName(player.getName());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        isSelected = false;
        remove(cardsPanel);
        remove(descriptionPanel);
        createCenterPanel();
        createDescriptionPanel();
        revalidate();
        repaint();
    }


    private void createCenterPanel() {
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        game.getPlayers().stream()
                .map(player -> createCardsPanel(player, false))
                .map(this::toScroll)
                .forEach(cardsPanel::add);
        JPanel myCardPanel = createCardsPanel(player, true);
        myCardPanel.setBackground(Color.magenta);
        cardsPanel.add(toScroll(myCardPanel));
        add(cardsPanel, BorderLayout.CENTER);
    }

    private JScrollPane toScroll(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        return scrollPane;
    }

    private void createDescriptionPanel() {
        descriptionPanel = new JPanel(new BorderLayout());

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton endTour = new JButton("Koniec Tury");
        JButton putButton = new JButton("Połóż na stół");
        JButton randomButton = new JButton("Losuj za 1 zasób");
        JButton frontendDev = new JButton("+1 Zasób dla Nieasertywnego Deva");
        JButton moveKnowlage = new JButton("Przenieś wiedzę");

        boolean isEnabled = game.getMakeMovePlayer().equals(player);
        endTour.setEnabled(isEnabled);
        putButton.setEnabled(isEnabled);
        randomButton.setVisible(isEnabled && player.hasDick() && player.getResources() > 0 && !wasRandom);
        frontendDev.setVisible(isEnabled && player.hasFrontend() && player.getResources() > 0 && !wasRandom);
        boolean hasKnowlage = !player.getNoOutsourcingKnowledgeCardsToMove().isEmpty();
        moveKnowlage.setVisible(isEnabled && hasKnowlage && player.getNoOutsourcingProgrammerCards().size() > 1);

        putButton.addActionListener(e -> onPut());
        endTour.addActionListener(e -> onEnd());
        randomButton.addActionListener(e -> onRandom());
        frontendDev.addActionListener(e -> onAddFrontend());
        moveKnowlage.addActionListener(e -> onMoveKnowlage());

        buttonsPanel.add(new JLabel("<html>Tura numer: <b>" + player.getTourNumber()+"</b></html>"));
        buttonsPanel.add(new JLabel("<html>Ruch wykonuje: <b>" + game.getMakeMovePlayer().getName()+"</b></html>"));
        buttonsPanel.add(putButton);
        buttonsPanel.add(endTour);
        buttonsPanel.add(randomButton);
        buttonsPanel.add(frontendDev);
        buttonsPanel.add(moveKnowlage);


        JPanel resources = new JPanel();
        resources.setBorder(BorderFactory.createTitledBorder("<html><b>Zasoby</b></html>"));
        resources.setLayout(new BoxLayout(resources, BoxLayout.Y_AXIS));
        game.getPlayers().stream()
                .map(this::createPlayerLabelResources)
                .forEach(resources::add);

        JPanel points = new JPanel();
        points.setBorder(BorderFactory.createTitledBorder("<html><b>Punkty</b></html>"));
        points.setLayout(new BoxLayout(points, BoxLayout.Y_AXIS));
        game.getPlayers().stream()
                .map(this::createPlayerLabelPoints)
                .forEach(points::add);

        descriptionPanel.add(buttonsPanel, BorderLayout.CENTER);
        descriptionPanel.add(points, BorderLayout.EAST);
        descriptionPanel.add(resources, BorderLayout.WEST);
        add(descriptionPanel, BorderLayout.NORTH);

    }

    private void onEnd() {
        try {
            Object[] options = {"TAK", "NIE"};
            int option = JOptionPane.showOptionDialog(null, "Na pewno", "",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
            if (JOptionPane.YES_OPTION == option) {
                game.end(player.getName());
            }
            wasRandom = false;
            server.refresh(game);
        } catch (Exception e) {
            error(e);
        }
    }

    private void onPut() {
        try {
            if (player.getResources() < player.calculatePrice(selectedCard)) {
                JOptionPane.showMessageDialog(this, "Nie masz kasy :(", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (selectedCard instanceof KnowledgeCard) {
                handleKnowlagecard();
            } else if (selectedCard instanceof TransferCard) {
                handleTransferCard();
            } else if (selectedCard instanceof Darek) {
                handleDarekCard();
            } else if (selectedCard instanceof Bolek) {
                handleBolekCard();
            } else if (selectedCard instanceof AdditionalCards) {
                handleAdditionalCard();
            } else if (selectedCard instanceof ResearchEngineer) {
                handleResarch();
            } else {
                game.putCard(player.getName(), selectedCard);
            }
            server.refresh(game);
        } catch (Exception e) {
            error(e);
        }
    }

    private void onRandom() {
        try {
            List<Card> ipponents = getOpponentsProgrammers();
            if (!ipponents.isEmpty()) {
                if (new Random().nextBoolean()) {
                    JOptionPane.showMessageDialog(this, "Wygrana:)", "", JOptionPane.INFORMATION_MESSAGE);
                    Card card = chooseCard(ipponents, "Wybierz karte pracownika przeciwnika");
                    game.getPlayerByName(player.getName()).removeResources(1);
                    game.transferCard(player.getName(), card);

                } else {
                    JOptionPane.showMessageDialog(this, "Przegrana:(", "", JOptionPane.INFORMATION_MESSAGE);
                }
                wasRandom = true;
                server.refresh(game);
            } else {
                JOptionPane.showMessageDialog(this, "Ogarnij się", "", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            error(e);
        }
    }

    private void onAddFrontend() {
        try {
            game.getPlayerByName(player.getName()).removeResources(1);
            game.getPlayerByName(player.getName()).getFrontend().addPower();
            server.refresh(game);
        } catch (Exception e) {
            error(e);
        }
    }

    private void onMoveKnowlage() {
        try {
            List<Card> knolwages = player.getNoOutsourcingKnowledgeCardsToMove();
            Card knowlage = chooseCard(knolwages, "Którą kartę wiedzy przeniść");
            List<Card> programmers = player.getNoOutsourcingProgrammerCards();
            Card programer = chooseCard(programmers, "Na kogo przenieść");
            game.moveKnowlage(player.getName(), (KnowledgeCard) knowlage, programer);
            server.refresh(game);
        } catch (Exception e) {
            error(e);
        }
    }

    private void error(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, e, "", JOptionPane.ERROR_MESSAGE);
    }

    private void handleKnowlagecard() {
        List<Card> programmers = player.getNoOutsourcingProgrammerCards();
        if (programmers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ogarnij sie! Nie ma programistów", "", JOptionPane.WARNING_MESSAGE);
        } else {
            Card card = chooseCard(programmers, "Wybierz programiste");
            game.putKnowledgeCard(player.getName(), (KnowledgeCard) selectedCard, card);
        }
    }

    private void handleTransferCard() {
        List<Card> opponents = getOpponentsProgrammers();
        if (opponents.isEmpty()) {
            game.putCard(player.getName(), selectedCard);
        } else {
            Card worker = chooseCard(opponents, "Wybierz karte pracownika przeciwnika");
            game.putCardWithTransfer(player.getName(), selectedCard, worker);
        }
    }

    private void handleDarekCard() {
        List<Card> opponents = getOpponentsProgrammers();
        if (opponents.isEmpty()) {
            game.putCard(player.getName(), selectedCard);
        } else {
            Card worker = chooseCard(opponents, "Wybierz karte pracownika przeciwnika");
            game.putDarekCard(player.getName(), selectedCard, worker);
        }
    }

    private void handleBolekCard() {
        Card worker = null;
        List<Card> workers = player.getNoOutsourcingWorkerCards();
        if (!workers.isEmpty()) {
            Object[] options = {"TAK", "NIE"};
            int option = JOptionPane.showOptionDialog(null, "Chcesz kogoś cofnąć?", "",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
            if (JOptionPane.YES_OPTION == option) {
                worker = chooseCard(workers, "Kogo cofasz?");
            }
        }
        game.putBolekCard(player.getName(), selectedCard, worker);
    }

    private void handleAdditionalCard() {
        AdditionalCards aditional = (AdditionalCards) selectedCard;
        List<Card> cardsFromStack = new ArrayList<>(game.getCardsFromStack(aditional.getAll()));

        List<Card> aditionalCards = new ArrayList<>();
        for (int i = 0; i < aditional.getChoose(); i++) {
            Card card = chooseCard(cardsFromStack, "Wybierz kartę");
            cardsFromStack.remove(card);
            aditionalCards.add(card);
        }
        game.putAditionalCards(player.getName(), selectedCard, cardsFromStack, aditionalCards);

    }

    private void handleResarch() {
        List<Card> cardsFromStack = new ArrayList<>(game.getCardsFromStack(3));
        List<Card> selectedCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Card card = chooseCard(cardsFromStack, "Ustaw kolejność kard. Wybierz karte nr " + (i + 1) + "w kolejce");
            selectedCards.add(card);
            cardsFromStack.remove(card);
        }
        game.putCardsOnStartStack(selectedCards);
    }

    private List<Card> getOpponentsProgrammers() {
        return game.getOpponentsOf(player.getName()).stream()
                .map(Player::getWorkerCards)
                .filter(l -> l.size() > 3 || !(selectedCard instanceof Rockstar))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


    private Card chooseCard(List<Card> cards, String message) {
        Map<String, Card> byName = Maps.uniqueIndex(cards, Object::toString);
        Object[] possibleValues = byName.keySet().toArray();
        Object selectedValue = getSelectedValue(possibleValues, message);
        return byName.get(selectedValue);
    }

    private Object getSelectedValue(Object[] possibleValues, String message) {
        Object selectedValue = null;
        while (selectedValue == null) {
            selectedValue = JOptionPane.showInputDialog(this,
                    message, "",
                    JOptionPane.QUESTION_MESSAGE, null,
                    possibleValues, possibleValues[0]);
        }
        return selectedValue;
    }

    private JPanel createCardsPanel(Player player, boolean areMyCards) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        List<Card> cards;
        if (areMyCards) {
            panel.setBorder(BorderFactory.createTitledBorder("<html><b>Moje karty w ręce:" + player.getCardsOnHands().size() + "</b></html>"));
            cards = player.getCardsOnHands();
        } else {
            panel.setBorder(BorderFactory.createTitledBorder("<html><b>Karty gracza " + player.getName() + "</b></html>"));
            cards = player.getCardsOnTable();
            panel.setBackground(player.getColor());
        }

        List<JPanel> buttons = cards.stream()
                .sorted()
                .map(card -> createPanel(card, areMyCards))
                .collect(Collectors.toList());
        buttons.forEach(panel::add);
        return panel;
    }

    private JPanel createPanel(Card card, boolean areMyCards) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(180, 110));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(card.getType().getPolishName()));

        panel.add(new JLabel("<html><b>" + card.toString() + "</b></html>"));
        panel.add(new JLabel("<html>Cena: <b>" + card.getPrice() + "</b></html>"));
        JLabel burntLabel = new JLabel("<html>Pozostałe punkty wypalenia: <b>" + card.getBurnoutPoints() + "</b></html>");
        panel.add(burntLabel);

        if (card instanceof ProgrammerCard) {
            ProgrammerCard programmerCard = (ProgrammerCard) card;
            panel.add(new JLabel("<html>Siła: <b>" + programmerCard.getPoints() + "</b></html>"));
        }

        if (card instanceof KnowledgeCard) {
            KnowledgeCard knowledgeCard = (KnowledgeCard) card;
            panel.add(new JLabel("<html>Dodatkowa siła: <b>" + knowledgeCard.getPoints() + "</b></html>"));
            String owner = knowledgeCard.getOwner().map(Object::toString).orElse("");
            panel.add(new JLabel("<html>Właściciel: <b>" + owner + "</b></html>"));
            panel.remove(burntLabel);
        }

        if (card instanceof ActionCard) {
            panel.remove(burntLabel);
        }
        if (areMyCards) {
            if (!isSelected) {
                selectedCard = card;
                selectedPanel = panel;
                panel.setBackground(Color.LIGHT_GRAY);
                isSelected = true;
            }

            panel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    panel.setBackground(Color.LIGHT_GRAY);
                    selectedPanel.setBackground(Color.white);
                    selectedCard = card;
                    selectedPanel = panel;
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }
        return panel;
    }

    private JLabel createPlayerLabelPoints(Player player) {
        JLabel label = new JLabel("<html><b>" + player.getName() + ": " + player.getPoints() + "</b></html>");
        label.setForeground(player.getColor());
        return label;
    }

    private JLabel createPlayerLabelResources(Player player) {
        JLabel label = new JLabel("<html><b>" + player.getName() + ": " + player.getResources() + "</b></html>");
        label.setForeground(player.getColor());
        return label;
    }

    private void setUIFont() {
        FontUIResource font = new FontUIResource("Arial", Font.PLAIN, 12);
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, font);
            }
        }
        UIManager.put("Button.font", new FontUIResource("Serif", Font.BOLD, 12));
        UIManager.put("TitledBorder.font", new FontUIResource("Serif", Font.ITALIC, 12));
    }

    public String getPlayerName() {
        return player.getName();
    }

}
