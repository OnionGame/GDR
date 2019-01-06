package pl.gd.itstartup.swing;

import com.google.common.collect.Maps;
import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.core.cards.Card;
import pl.gd.itstartup.core.cards.actioncards.ActionCard;
import pl.gd.itstartup.core.cards.knownlagecards.KnowledgeCard;
import pl.gd.itstartup.core.cards.programercards.ProgrammerCard;
import pl.gd.itstartup.rmi.ITStartupRMIServerInterface;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private Game game;
    private Player player;
    private Card selectedCard;
    private JPanel cardsPanel;
    private JPanel descriptionPanel;
    private JPanel selectedPanel;
    private boolean isSelected = false;
    private ITStartupRMIServerInterface server;

    public MainFrame(String playerName, ITStartupRMIServerInterface server) throws RemoteException {
        super();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setUIFont();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.server = server;
        this.game = server.getGame();     //   game = new Game();
        this.player = game.addPlayer(playerName);
        setTitle(player.getName());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        createCenterPanel();
        createDescriptionPanel();
        setVisible(true);

    }

    public void refresh() {
        try {
            game = server.getGame();
            player = game.getPlayerByName(player.getName());
        } catch (RemoteException e) {
            e.printStackTrace();
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
        endTour.setEnabled(game.getMakeMovePlayer().equals(player));
        putButton.setEnabled(game.getMakeMovePlayer().equals(player));
        putButton.addActionListener(e -> onPut());
        endTour.addActionListener(e -> onEnd());

        buttonsPanel.add(new JLabel("Tura numer: " + player.getTourNumber()));
        buttonsPanel.add(new JLabel("Ruch wykonuje: " + game.getMakeMovePlayer().getName()));
        buttonsPanel.add(putButton);
        buttonsPanel.add(endTour);


        JPanel statistics = new JPanel();
        statistics.setBorder(BorderFactory.createTitledBorder("<html><b>Punkty</b></html>"));
        statistics.setLayout(new BoxLayout(statistics, BoxLayout.Y_AXIS));
        game.getPlayers().stream()
                .map(this::createPlayerLabel)
                .forEach(statistics::add);

        descriptionPanel.add(buttonsPanel, BorderLayout.CENTER);
        descriptionPanel.add(statistics, BorderLayout.EAST);
        descriptionPanel.add(new JLabel("<html>Ilość zasobów:<b>" + player.getResources() + "</b></html>"), BorderLayout.WEST);
        add(descriptionPanel, BorderLayout.NORTH);
    }

    private void onEnd() {
        Object[] options = {"TAK", "NIE"};
        int option = JOptionPane.showOptionDialog(null, "Na pewno", "",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        if (JOptionPane.YES_OPTION == option) {
            game.end(player.getName());
        }
        try {
            server.refresh(game);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onPut() {
        if (selectedCard instanceof KnowledgeCard) {
            Map<Object, ProgrammerCard> byName = Maps.uniqueIndex(player.getProgrammersOnTable(), Object::toString);
            if (byName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ogarnij się", "", JOptionPane.ERROR_MESSAGE);
            } else {
                Object[] possibleValues = byName.keySet().toArray();
                Object selectedValue = JOptionPane.showInputDialog(this,
                        "Do kogo przypisać?", "",
                        JOptionPane.QUESTION_MESSAGE, null,
                        possibleValues, possibleValues[0]);
                ProgrammerCard programmerCard = byName.get(selectedValue);
                game.putKnowledgeCard(player.getName(), (KnowledgeCard) selectedCard, programmerCard);
            }
        } else {
            game.putCard(player.getName(), selectedCard);
        }
        try {
            server.refresh(game);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private JPanel createCardsPanel(Player player, boolean areMyCards) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        List<Card> cards;
        if (areMyCards) {
            panel.setBorder(BorderFactory.createTitledBorder("<html><b>Moje karty w ręce</b></html>"));
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
        panel.setPreferredSize(new Dimension(170, 100));
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

    private JLabel createPlayerLabel(Player player) {
        JLabel label = new JLabel("<html><b>" + player.getName() + ": " + player.getPoints() + "</b></html>");
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
