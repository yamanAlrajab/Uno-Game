import javax.swing.*;
import java.awt.*;


public class GameBoardFrame extends JFrame {
    private JButton drawButton;
    private JButton nextPlayerButton;
    private JPanel playerHandPanel;
    private JLabel topCardLabel;
    private JLabel currentPlayerLabel;
    private UnoGame gameModel;
    private Player currentPlayer;
    private JPanel messagesPanel;
    private JTextArea messagesTextArea;
    private JLabel imageLabel;
    private boolean dark;

    public GameBoardFrame(UnoGame game) {
        gameModel = game;
        setTitle("Uno Game");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        // Create a split pane for the upper and lower halves
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.5); // Equal resizing for upper and lower halves

        // Upper half panel
        JPanel upperHalfPanel = new JPanel(new BorderLayout());

        // Current player label (upper half, left)
        JPanel currentPlayerPanel = new JPanel();
        currentPlayerLabel = new JLabel();
        currentPlayerPanel.add(currentPlayerLabel);
        upperHalfPanel.add(currentPlayerPanel, BorderLayout.WEST);

        // Top card display (upper half, center)
        JPanel topCardPanel = new JPanel();
        topCardLabel = new JLabel();
        topCardPanel.add(topCardLabel);
        upperHalfPanel.add(topCardPanel, BorderLayout.CENTER);

        // Add the upper half panel to the split pane
        splitPane.setTopComponent(upperHalfPanel);

        JPanel lowerHalfPanel = new JPanel(new BorderLayout());

        // Player hand panel
        playerHandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JScrollPane scrollPane = new JScrollPane(playerHandPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        lowerHalfPanel.add(scrollPane, BorderLayout.CENTER);

        // Messages panel (lower half, left)
        messagesPanel = new JPanel(new BorderLayout());
        messagesTextArea = new JTextArea(10, 20); // You can customize the size
        JScrollPane messagesScrollPane = new JScrollPane(messagesTextArea);
        messagesPanel.add(messagesScrollPane, BorderLayout.CENTER);
        lowerHalfPanel.add(messagesPanel, BorderLayout.WEST);
        imageLabel = null;

        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout());
        drawButton = new JButton("Draw Card");
        drawButton.addActionListener(new UnoGameController(gameModel, this));
        actionPanel.add(drawButton);


        nextPlayerButton = new JButton("Next Player");
        nextPlayerButton.setEnabled(false);
        nextPlayerButton.addActionListener(new UnoGameController(gameModel, this));
        actionPanel.add(nextPlayerButton);

        lowerHalfPanel.add(actionPanel, BorderLayout.SOUTH);

        // Add the lower half panel to the split pane
        splitPane.setBottomComponent(lowerHalfPanel);

        // Add the main panel to the frame
        add(splitPane);
        // Add this view to the uno game model
        gameModel.addView(this);

        update();
    }

    public void nextPlayerButton(Boolean bool) {
        this.nextPlayerButton.setEnabled(bool);
    }

    public void drawCardButton(Boolean bool) {
        this.drawButton.setEnabled(bool);
    }

    public void cardButtons(Boolean bool) {
        Component[] components = playerHandPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {

                JButton button = (JButton) component;
                button.setEnabled(bool);
            }
        }
    }

    // Update the view components as necessary
    public void update() {
        // update the panel with player cards
        updatePlayerHandPanel();
        // update the panel with top card
        updateTopCardDisplay();
        // Update the current player label
        updateCurrentPlayerDisplay();


    }


    public void updatePlayerHandPanel() {
        playerHandPanel.removeAll(); // Clear the existing cards (buttons)

        if (gameModel.getSide().equals("LIGHT")) {
            dark = false;
        } else {
            dark = true;
        }
        // Get the current player's hand
        Hand currentHand = gameModel.getCurrentPlayer().getHand();

        for (Card card : currentHand.getCards()) {
            // For each card, create a button and set the text to the card's string representation
            JButton cardButton = new JButton();
            ImageIcon cardImage;
            JLabel cardLabel = new JLabel(card.stringCard(), SwingConstants.CENTER);
            if (dark) {
                //Get dark Image Path for each card's button
                cardButton.setText(card.stringDarkCard());
                cardImage = loadDarkImagePath(card);
                cardLabel = new JLabel(card.stringDarkCard(), SwingConstants.CENTER);
            } else {
                //Get Image Path for each card's button
                cardButton.setText(card.stringCard());
                cardImage = loadImagePath(card);
            }
            cardImage.setImage(cardImage.getImage().getScaledInstance(80, 160, Image.SCALE_SMOOTH));
            cardButton.setIcon(cardImage);

            cardButton.setLayout(new BoxLayout(cardButton, BoxLayout.Y_AXIS));
            cardButton.add(cardLabel);

            cardButton.setPreferredSize(new Dimension(120, 150));
            cardButton.addActionListener(new UnoGameController(gameModel, this));

            // Add the button to the player hand panel
            playerHandPanel.add(cardButton);
        }

        // Refresh the panel to show the updated hand
        playerHandPanel.revalidate();
        playerHandPanel.repaint();
    }

    private void updateTopCardDisplay() {
        Card topCard = gameModel.getTopCard();
        ImageIcon topCardImage;
        String cardText = topCard.stringCard();
        if (dark) {
            //Get dark Image Path for each card's button
            topCardImage = loadDarkImagePath(topCard);
            cardText = topCard.stringDarkCard();
        } else {
            //Get Image Path for each card's button
            topCardImage = loadImagePath(topCard);
        }

        // Check if the image was successfully loaded
        if (topCardImage.getImageLoadStatus() == MediaTracker.COMPLETE) {
            topCardLabel.setIcon(topCardImage);
        } else {
            // Handle image loading failure
            topCardLabel.setIcon(null);
            topCardLabel.setText("Image not found");
        }
        topCardLabel.setText(cardText);

    }

    public void updateMessages(String message) {
        messagesTextArea.setText("");
        messagesTextArea.append(message + "\n");
        if (imageLabel != null) {
            messagesPanel.remove(imageLabel);
            messagesPanel.revalidate();
            messagesPanel.repaint();
        }
    }

    public void updateDrawCardMessagePanel(String message, Card drawnCard) {
        messagesTextArea.setText("");
        messagesTextArea.append(message + "\n");

        if (drawnCard != null) {
            // Assuming you have a JLabel to display the image
            ImageIcon cardImage;
            if (dark) {
                //Get dark Image Path for each card's button
                cardImage = loadDarkImagePath(drawnCard);
            } else {
                //Get Image Path for each card's button
                cardImage = loadImagePath(drawnCard);
            }
            imageLabel = new JLabel(cardImage);

            // Add the image to the messages panel
            messagesPanel.add(imageLabel, BorderLayout.NORTH);
            messagesPanel.revalidate();
            messagesPanel.repaint();
        }
    }

    private void updateCurrentPlayerDisplay() {

        currentPlayer = gameModel.getCurrentPlayer();
        currentPlayerLabel.setText("Current Player: " + currentPlayer.getName());
    }

    protected ImageIcon loadImagePath(Card card) {
        String imagePath;

        if (card.getValue() == Card.Value.WILD || card.getValue() == Card.Value.WILD_DRAW_TWO_CARDS) {
            imagePath = "unoCards/" + card.getValue().toString().toLowerCase() + "/" + card.getValue().toString() + ".png";
        } else {
            imagePath = "unoCards/" + card.getValue().toString().toLowerCase() + "/" + card.getColor().toString().toLowerCase() + ".png";
        }

        ImageIcon CardImage = new ImageIcon(imagePath);

        return CardImage;
    }

    protected ImageIcon loadDarkImagePath(Card card) {
        String imagePath;
        if (card.getValue() == Card.Value.WILD) {
            imagePath = "dark/" + card.getDarkValue().toString().toLowerCase() + ".png";
        } else {
            imagePath = "dark/" + card.getDarkColor().toString().toLowerCase() + "_" + card.getDarkValue().toString().toLowerCase() + ".png";
        }
        ImageIcon CardImage = new ImageIcon(imagePath);

        return CardImage;
    }

    // Main method to start the game GUI
    public static void main(String[] args) {
        // Show input dialog to get the number of players
        String numPlayersStr = JOptionPane.showInputDialog(null,
                "How many players? (2-4)",
                "Number of Players",
                JOptionPane.QUESTION_MESSAGE);
        int numPlayers = 0;

        // Validate and parse the input
        try {
            numPlayers = Integer.parseInt(numPlayersStr);
            if (numPlayers < 2 || numPlayers > 4) {
                JOptionPane.showMessageDialog(null,
                        "Please choose a number between 2 and 4.",
                        "Invalid Number",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0); // Exit or repeat the process
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid input. Please enter a number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0); // Exit or repeat the process
        }

        String numAistr = JOptionPane.showInputDialog(null,
                "How many AI? (0-2)",
                "Number of AI",
                JOptionPane.QUESTION_MESSAGE);
        int numAi = 0;

        // Validate and parse the input
        try {
            numAi = Integer.parseInt(numAistr);
            if (numAi < 0 || numAi > 2) {
                JOptionPane.showMessageDialog(null,
                        "Please choose a number between 0 and 2.",
                        "Invalid Number",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0); // Exit or repeat the process
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid input. Please enter a number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0); // Exit or repeat the process
        }

        // Start the game with the specified number of players
        UnoGame unoGame = new UnoGame(numPlayers, numAi);
        // Ideally, pass unoGame to the view
        GameBoardFrame view = new GameBoardFrame(unoGame);
        view.setVisible(true);
        view.dark = false;

        // Start the game logic if needed
        unoGame.play();
    }

    public void endGame() {
        JOptionPane.showMessageDialog(this, "This player won");
    }
}
