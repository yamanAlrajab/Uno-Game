import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the Uno card game and manages the game flow.
 *
 * @version 1.0
 */
public class UnoGame {
    private List<Player> players; // List of players in the game
    private Card topCard;         // The current top card on the deck
    private int currentPlayerIndex; // Index of the current player
    boolean clockwise = true;     // Direction of play (clockwise or counterclockwise)
    GameBoardFrame view;
    private Player currentPlayer;
    private String side;

    /**
     * Constructs an UnoGame with the specified number of players.
     *
     * @param numPlayers The number of players in the game (2 to 4).
     */
    public UnoGame(int numPlayers, int numAi) {
        players = new ArrayList<>();
        initializePlayers(numPlayers, numAi);
        currentPlayerIndex = 0;
        topCard = Card.generate_top_card();
        side = "LIGHT";

    }

    public void addView(GameBoardFrame view) {
        this.view = view;
    }

    /**
     * Initializes the players by collecting their names from the user.
     *
     * @param numPlayers The number of players in the game.
     */
    private void initializePlayers(int numPlayers, int numAi) {


        for (int i = 1; i <= numPlayers - numAi; i++) {
            String playerName = "Player " + i;
            Player player = new Player(playerName);
            players.add(player);
        }
        for (int i = 1; i <= numAi; i++) {
            String playerName = "AI " + i;
            Player player = new Player(playerName);
            players.add(player);
        }
    }

    public boolean checkAiPlayer(Player player) {
        System.out.println("Reached checkAiPlayer");
        currentPlayer = player;
        if (currentPlayer.isAi()) {
            return true;
        }
        return false;
    }

    public void handleAiTurn(Player currentPlayer) {
        Hand playerHand = currentPlayer.getHand();
        for (Card card : playerHand.getCards()) {
            if (isValidUnoPlay(card)) {
                handleValidPlay(currentPlayer, card);
                return;
            }
        }
        handleDrawCard(currentPlayer);
    }

    /**
     * Displays the current player's hand.
     *
     * @param currentPlayer The current player.
     */
    private void displayCurrentPlayerHand(Player currentPlayer) {
        System.out.print("Player " + currentPlayer.getName() + "'s cards: \n");
        List<Card> hand = currentPlayer.getHand().getCards();
        for (int j = 0; j < hand.size(); j++) {
            Card card = hand.get(j);
            System.out.print((j + 1) + "- " + card.stringCard());
            if (j < hand.size() - 1) {
                System.out.print(",\n");
            }
        }
        System.out.println();
    }

    /**
     * Checks if a card is a valid play in the Uno game.
     *
     * @param card The card to check.
     * @return True if the card can be played; otherwise, false.
     */
    public boolean isValidUnoPlay(Card card) {
        System.out.println("is valid reached");
        if (side.equals("LIGHT")) {
            return card.getColor() == topCard.getColor() || card.getValue() == topCard.getValue();
        } else if (side.equals("DARK")) {
            return card.getDarkColor() == topCard.getDarkColor() || card.getDarkValue() == topCard.getDarkValue();
        } else {
            return false;
        }
    }

    /**
     * Calculates the score for the winning player based on opponents' cards.
     *
     * @param winningPlayer The player who won the round.
     */
    private void calculateScoreForWinningPlayer(Player winningPlayer) {
        int finalScore = 0;

        // Iterate through the remaining players' hands
        for (Player opponent : players) {
            if (opponent != winningPlayer) {
                for (Card card : opponent.getHand().getCards()) {
                    Card.Value value = card.getValue();

                    if (value == Card.Value.REVERSE || value == Card.Value.SKIP) {
                        finalScore += 20;
                    } else if (value == Card.Value.WILD) {
                        finalScore += 40;
                    } else if (value == Card.Value.WILD_DRAW_TWO_CARDS) {
                        finalScore += 50;
                    } else {
                        finalScore += value.ordinal();
                    }
                }
            }
        }

        winningPlayer.updateScore(finalScore);
        System.out.println("Player " + winningPlayer.getName() + " scored " + finalScore + " points from opponents' cards.");
    }

    /**
     * Starts the Uno game and manages the game flow.
     */
    public void play() {
        boolean gameRunning = true;
        boolean go_next = true;
        Scanner scanner = new Scanner(System.in);

        while (gameRunning) {
            Player currentPlayer = players.get(currentPlayerIndex);
            displayGameStatus(currentPlayer);

            int cardIndex = getPlayerInput(scanner, currentPlayer);

            if (cardIndex == 0) {
                handleDrawCard(currentPlayer);
                go_next = true;
            }

            handleUnoStatus(scanner, currentPlayer);

            if (currentPlayer.getHand().getNumCards() == 0) {
                handleWinOrPenalty(currentPlayer);
                gameRunning = currentPlayer.getHand().getNumCards() != 0;
                System.out.println("num cards = 0");
                go_next = false;

            }

            if (go_next) {
                currentPlayerIndex = (currentPlayerIndex + (clockwise ? 1 : -1) + players.size()) % players.size();
            }
        }
        view.endGame();
    }

    /**
     * Displays the game status, including the top card and the current player's hand.
     *
     * @param currentPlayer The current player.
     */
    private void displayGameStatus(Player currentPlayer) {
        System.out.println("Top Card: " + topCard.stringCard());
        System.out.println("Player " + currentPlayer.getName() + "'s turn");
        displayCurrentPlayerHand(currentPlayer);
    }

    /**
     * Gets the player's input for selecting a card to play or drawing a card.
     *
     * @param scanner       The input scanner.
     * @param currentPlayer The current player.
     * @return The index of the selected card or 0 to draw a card.
     */
    private int getPlayerInput(Scanner scanner, Player currentPlayer) {
        int cardIndex;
        while (true) {
            System.out.println("Enter card index to play (1 to " + currentPlayer.getHand().getNumCards() + ") or 0 to draw a card:");
            if (scanner.hasNextInt()) {
                cardIndex = scanner.nextInt();
                if (cardIndex >= 0 && cardIndex <= currentPlayer.getHand().getNumCards()) {
                    return cardIndex;
                } else {
                    System.out.println("Invalid Index for Hand, Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Handles drawing a card from the deck.
     *
     * @param currentPlayer The current player who is drawing a card.
     */
    protected void handleDrawCard(Player currentPlayer) {
        Card drawnCard = currentPlayer.getHand().addCard(side);
        view.updateDrawCardMessagePanel("Player " + currentPlayer.getName() + " drew a card: ", drawnCard);
        view.nextPlayerButton(true);
        view.drawCardButton(false);
        view.update();
        view.cardButtons(false);
    }

    /**
     * Handles Uno status and reminds the player to say Uno if applicable.
     *
     * @param scanner       The input scanner.
     * @param currentPlayer The current player.
     */
    private void handleUnoStatus(Scanner scanner, Player currentPlayer) {
        if (currentPlayer.hasUno() && !currentPlayer.hasRemindedUno()) {
            System.out.println("Player " + currentPlayer.getName() + ", you have Uno! Don't forget to say it.");
            currentPlayer.setRemindedUno(true);
        }
        if (currentPlayer.getHand().getNumCards() == 1) {
            System.out.println("Type 'UNO' to say it: ");
            String unoInput = scanner.next().trim().toUpperCase();
            if (unoInput.equals("UNO")) {
                currentPlayer.sayUno();
            }
        }
    }

    /**
     * Handles the outcome when a player wins or fails to say Uno.
     *
     * @param currentPlayer The current player.
     */
    private void handleWinOrPenalty(Player currentPlayer) {
        if (currentPlayer.getUnoCalled()) {
            System.out.println("Player " + currentPlayer.getName() + " wins!");
            calculateScoreForWinningPlayer(currentPlayer);
        } else {
            System.out.println("Player " + currentPlayer.getName() + " did not say Uno and draws 2 cards.");
            currentPlayer.getHand().addCard(side);
            currentPlayer.getHand().addCard(side);
            currentPlayer.setRemindedUno(false);
        }
    }

    /**
     * Handles playing a Wild card and allows the player to choose the color.
     *
     * @param colour        The input for the colour chosen.
     * @param currentPlayer The current player.
     * @param selectedCard  The Wild card to play.
     * @return True if the play was successful; otherwise, false.
     */
    public void handleWildCard(Card.Color colour, Player currentPlayer, Card selectedCard) {
        currentPlayer.playCard(selectedCard);
        topCard = new Card(colour, Card.Value.WILD, side);
        System.out.println("handleWildCard is reached");
        view.nextPlayerButton(true);
        view.drawCardButton(false);
        view.updateMessages(colour + " has been chosen. Player " + currentPlayerIndex + " has to draw place a " + colour + "colour card");
        view.update();
        view.cardButtons(false);
    }

    public void handleDarkWildCard(Card.DarkColor colour, Player currentPlayer, Card selectedCard) {
        currentPlayer.playCard(selectedCard);
        System.out.println("handleWildCard is reached");
        view.nextPlayerButton(true);
        view.drawCardButton(false);
        view.updateMessages(colour + " has been chosen. Player " + currentPlayerIndex + " has to draw place a " + colour + "colour card");
        view.update();
        view.cardButtons(false);
    }

    public void handleFlipCard(Player currentPlayer, Card c) {
        side = (side.equals("LIGHT")) ? "DARK" : "LIGHT";

        currentPlayer.playCard(c);
        topCard = c;
        System.out.println("Reached handleFlipCard");
        view.nextPlayerButton(true);
        view.drawCardButton(false);
        view.updateMessages("CARDS HAVE BEEN FLIPPED");
        view.update();
        view.cardButtons(false);

        for (Player player : players) {
            for (Card card : player.getHand().getCards()) {
                card.setCurrentside(side);
            }
        }
    }

    /**
     * Handles playing a Skip card. If the card matches the color of the top card,
     * it skips the next player's turn.
     *
     * @param currentPlayer The current player.
     * @param selectedCard  The Skip card to play.
     * @param clockwise     The direction of play (clockwise or counterclockwise).
     * @return True if the play was successful; otherwise, false.
     */
    public void handleSkipCard(Player currentPlayer, Card selectedCard, boolean clockwise) {
        if (selectedCard.getColor() == topCard.getColor() || topCard.getValue() == Card.Value.SKIP) {
            currentPlayer.playCard(selectedCard);
            topCard = selectedCard;
            currentPlayerIndex = (currentPlayerIndex + (clockwise ? 1 : -1) + players.size()) % players.size();
            view.nextPlayerButton(true);
            view.drawCardButton(false);
            view.update();
            view.cardButtons(false);
        } else {
            view.updateMessages("Invalid play. The card must match the color of the top card.");
        }
    }

    public void handleDarkSkipCard(Player currentPlayer, Card selectedCard, boolean clockwise) {
        if (selectedCard.getDarkColor() == topCard.getDarkColor() || topCard.getDarkValue() == Card.DarkValue.SKIP_EVERYONE) {
            currentPlayer.playCard(selectedCard);
            topCard = selectedCard;
            for (int i = 0; i < players.size() - 1; i++) {
                currentPlayerIndex = (currentPlayerIndex + (clockwise ? 1 : -1) + players.size()) % players.size();
                Player skippedPlayer = players.get(currentPlayerIndex);
                System.out.println("Player " + skippedPlayer.getName() + " is skipped!");
            }
            view.nextPlayerButton(true);
            view.drawCardButton(false);
            view.update();
            view.cardButtons(false);
        } else {
            view.updateMessages("Invalid play. The card must match the color of the top card.");
        }
    }

    /**
     * Handles playing a Reverse card. If the card matches the color of the top card,
     * it reverses the direction of play.
     *
     * @param currentPlayer The current player.
     * @param selectedCard  The Reverse card to play.
     * @param clockwise     The direction of play (clockwise or counterclockwise).
     * @return True if the play was successful; otherwise, false.
     */
    public void handleReverseCard(Player currentPlayer, Card selectedCard, boolean clockwise) {
        if (selectedCard.getColor() == topCard.getColor() || topCard.getValue() == Card.Value.REVERSE) {
            currentPlayer.playCard(selectedCard);
            topCard = selectedCard;
            this.clockwise = !clockwise;
            view.nextPlayerButton(true);
            view.drawCardButton(false);
            view.update();
            view.cardButtons(false);
        } else {
            view.updateMessages("Invalid play. The card must match the color of the top card.");

        }
    }

    /**
     * Handles playing a Wild Draw Two Cards. If the card matches the color of the top card,
     * it forces the next player to draw two cards and skips their turn.
     *
     * @param colour        The input for the colour chosen.
     * @param currentPlayer The current player.
     * @param selectedCard  The Wild Draw Two Cards to play.
     * @param clockwise     The direction of play (clockwise or counterclockwise).
     * @return True if the play was successful; otherwise, false.
     */
    public void handleWildDrawTwoCards(Card.Color colour, Player currentPlayer, Card selectedCard, boolean clockwise) {
        currentPlayer.playCard(selectedCard);
        topCard = new Card(colour, Card.Value.WILD_DRAW_TWO_CARDS, side);
        currentPlayerIndex = (currentPlayerIndex + (clockwise ? 1 : -1) + players.size()) % players.size();
        Player nextPlayer = players.get(currentPlayerIndex);
        nextPlayer.getHand().addCard(side);
        nextPlayer.getHand().addCard(side);
        System.out.println("handleWildDrawTwoCards is reached");
        view.nextPlayerButton(true);
        view.drawCardButton(false);
        view.updateMessages(colour + " has been chosen. Player " + currentPlayerIndex + " has to draw 2 cards. due to wild draw two");
        view.update();
        view.cardButtons(false);
    }

    public void handleDarkDrawFive(Player currentPlayer, Card c, boolean clockwise) {
        currentPlayer.playCard(c);
        topCard = c;
        currentPlayerIndex = (currentPlayerIndex + (clockwise ? 1 : -1) + players.size()) % players.size();
        Player nextPlayer = players.get(currentPlayerIndex);
        nextPlayer.getHand().addCard(side);
        nextPlayer.getHand().addCard(side);
        nextPlayer.getHand().addCard(side);
        nextPlayer.getHand().addCard(side);
        nextPlayer.getHand().addCard(side);
        view.nextPlayerButton(true);
        view.drawCardButton(false);
        view.update();
        view.cardButtons(false);
    }

    /**
     * Handles a valid card play that matches the color or value of the top card.
     *
     * @param currentPlayer The current player.
     * @param selectedCard  The card to play.
     * @return True if the play was successful; otherwise, false.
     */
    public void handleValidPlay(Player currentPlayer, Card selectedCard) {
        System.out.println("Player " + currentPlayer.getName() + " plays: " + selectedCard.stringCard());
        currentPlayer.playCard(selectedCard);
        topCard = selectedCard;
        view.nextPlayerButton(true);
        view.drawCardButton(false);
        view.update();
        view.cardButtons(false);
        check_end_game(currentPlayer);
    }

    public void check_end_game(Player currentPlayer) {
        if (currentPlayer.getHand().getNumCards() == 0) {
            view.nextPlayerButton(false);
            view.drawCardButton(false);
            view.cardButtons(false);
            view.updateMessages(currentPlayer.getName() + " has won the game");
            view.endGame();
        }
    }

    public Player getCurrentPlayer() {
        Player currentPlayer = players.get(currentPlayerIndex);
        return currentPlayer;
    }

    public Player getNextCurrentPlayer() {
        currentPlayerIndex = (currentPlayerIndex + (clockwise ? 1 : -1) + players.size()) % players.size();
        view.update();
        return players.get(currentPlayerIndex);
    }

    public Card getTopCard() {
        return topCard;
    }

    public boolean isClockwise() {
        return clockwise;
    }

    public String getSide() {
        return this.side;
    }

}


