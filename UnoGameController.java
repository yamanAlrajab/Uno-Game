import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class UnoGameController implements ActionListener {
    UnoGame gameModel;
    GameBoardFrame gameView;
    Player currentPlayer;


    public UnoGameController(UnoGame game, GameBoardFrame view) {
        super();
        this.gameModel = game;
        this.gameView = view;


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentPlayer = gameModel.getCurrentPlayer();
        String clickedButton = e.getActionCommand();
        ArrayList<Card> handCards = currentPlayer.getHand().getCards();

        if (clickedButton == "Draw Card") {
            gameModel.handleDrawCard(currentPlayer);
        }
        if (clickedButton == "Next Player") {
            currentPlayer = gameModel.getNextCurrentPlayer();
            System.out.println(currentPlayer.getName());
            if (gameModel.checkAiPlayer(currentPlayer)) {
                gameModel.handleAiTurn(currentPlayer);
                gameView.nextPlayerButton(true);
                gameView.drawCardButton(false);
                gameView.cardButtons(false);
                gameView.updateMessages("Reached inside checking ai player controller");
                System.out.println("Reached checking ai controller");
            } else {
                gameView.nextPlayerButton(false);
                gameView.drawCardButton(true);
                gameView.updateMessages("Choose a card or draw a card");
            }
        }


        for (Card c : handCards) {
            if (c.getCurrentside().equals("LIGHT")) {
                if (clickedButton.equals(c.stringCard())) {
                    if (c.getValue() == Card.Value.WILD) {
                        gameModel.handleWildCard(chooseColor(), currentPlayer, c);
                        break;
                    }
                    if (c.getValue() == Card.Value.WILD_DRAW_TWO_CARDS) {
                        gameModel.handleWildDrawTwoCards(chooseColor(), currentPlayer, c, gameModel.isClockwise());
                        break;
                    }
                    if (c.getValue() == Card.Value.SKIP && c.getColor() == gameModel.getTopCard().getColor()) {
                        gameModel.handleSkipCard(currentPlayer, c, gameModel.isClockwise());
                        break;
                    }
                    if (c.getValue() == Card.Value.FLIP && c.getColor() == gameModel.getTopCard().getColor()) {
                        gameModel.handleFlipCard(currentPlayer, c);
                        break;
                    }
                    if (c.getValue() == Card.Value.REVERSE && c.getColor() == gameModel.getTopCard().getColor()) {
                        gameModel.handleReverseCard(currentPlayer, c, gameModel.isClockwise());
                        break;
                    }
                    if (gameModel.isValidUnoPlay(c)) {
                        gameModel.handleValidPlay(currentPlayer, c);
                        break;
                    } else {
                        gameView.updateMessages("Invalid play");
                        gameView.drawCardButton(true);
                    }
                }
            } else if (c.getCurrentside().equals("DARK")) {
                if (clickedButton.equals(c.stringDarkCard())) {
                    if (c.getDarkValue() == Card.DarkValue.WILD) {
                        gameModel.handleDarkWildCard(chooseDarkColor(), currentPlayer, c);
                        break;
                    }
                    if (c.getDarkValue() == Card.DarkValue.DRAW_FIVE) {
                        gameModel.handleDarkDrawFive(currentPlayer, c, gameModel.isClockwise());
                        break;
                    }
                    if (c.getDarkValue() == Card.DarkValue.SKIP_EVERYONE && c.getDarkColor() == gameModel.getTopCard().getDarkColor()) {
                        gameModel.handleDarkSkipCard(currentPlayer, c, gameModel.isClockwise());
                        break;
                    }
                    if (c.getDarkValue() == Card.DarkValue.FLIP && c.getDarkColor() == gameModel.getTopCard().getDarkColor()) {
                        gameModel.handleFlipCard(currentPlayer, c);
                        break;
                    }
                    if (c.getDarkValue() == Card.DarkValue.REVERSE && c.getDarkColor() == gameModel.getTopCard().getDarkColor()) {
                        gameModel.handleReverseCard(currentPlayer, c, gameModel.isClockwise());
                        break;
                    }
                    if (gameModel.isValidUnoPlay(c)) {
                        gameModel.handleValidPlay(currentPlayer, c);
                        break;
                    } else {
                        gameView.updateMessages("Invalid play");
                        gameView.drawCardButton(true);
                    }
                }

            }
        }
    }

    private static Card.Color chooseColor() {
        Card.Color[] possibleColors = Card.Color.values();
        String[] colorNames = new String[possibleColors.length];

        for (int i = 0; i < possibleColors.length; i++) {
            colorNames[i] = possibleColors[i].name();
        }

        String chosenColorName = null;

        while (true) {
            chosenColorName = (String) JOptionPane.showInputDialog(
                    null,
                    "Choose a color:",
                    "Color Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    colorNames,
                    colorNames[0]);

            // Check if a valid color was chosen
            if (chosenColorName != null) {
                try {
                    return Card.Color.valueOf(chosenColorName);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Invalid selection. Please choose a color.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "You must choose a color.");
            }
        }
    }

    private static Card.DarkColor chooseDarkColor() {
        Card.DarkColor[] possibleColors = Card.DarkColor.values();
        String[] colorNames = new String[possibleColors.length];

        for (int i = 0; i < possibleColors.length; i++) {
            colorNames[i] = possibleColors[i].name();
        }

        String chosenColorName = null;

        while (true) {
            chosenColorName = (String) JOptionPane.showInputDialog(
                    null,
                    "Choose a color:",
                    "Color Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    colorNames,
                    colorNames[0]);

            // Check if a valid color was chosen
            if (chosenColorName != null) {
                try {
                    return Card.DarkColor.valueOf(chosenColorName);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Invalid selection. Please choose a color.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "You must choose a color.");
            }
        }
    }


}
