/**
 * Represents a player in the Uno card game.
 *
 * @version 1.0
 */
public class Player {
    private String name;            // The player's name
    private Hand hand;              // A player's hand made up of cards
    private int score;              // Player's score
    private boolean unoCalled;      // Indicates whether Uno has been called by the player
    private boolean remindedUno;    // Indicates whether Uno reminder has been displayed

    /**
     * Constructs a new player with an empty hand, a score of 0, and Uno not called.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.hand = new Hand();   // Initialize the player's hand
        this.score = 0;          // Initialize the player's score
        this.unoCalled = false;  // Uno is initially not called
        this.remindedUno = false; // Reminder is initially not displayed
    }

    /**
     * Gets the player's name.
     *
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    public Boolean isAi() {
        if (name.contains("AI")) {
            return true;
        }
        return false;
    }

    /**
     * Gets the player's current score.
     *
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the player's hand.
     *
     * @return The player's hand.
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Sets the player's score.
     *
     * @param score The new score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Checks if Uno has been called by the player.
     *
     * @return True if Uno has been called; otherwise, false.
     */
    public boolean getUnoCalled() {
        return unoCalled;
    }

    /**
     * Sets the Uno call status for the player.
     *
     * @param unoCalled True to indicate Uno has been called; otherwise, false.
     */
    public void setUnoCalled(boolean unoCalled) {
        this.unoCalled = unoCalled;
    }


    /**
     * Plays a card from the player's hand.
     *
     * @param card The card to play.
     */
    public void playCard(Card card) {
        hand.removeCard(card);
    }

    /**
     * Displays all cards in the player's hand.
     */
    public void viewHand() {
        hand.printAll();
    }

    /**
     * Checks if the player has Uno (one card left in hand).
     *
     * @return True if the player has Uno; otherwise, false.
     */
    public boolean hasUno() {
        return hand.getNumCards() == 1;
    }

    /**
     * Calls Uno, indicating that the player has one card left in hand.
     */
    public void callUno() {
        unoCalled = true;
    }

    /**
     * Updates the player's score based on whether they have won the round.
     *
     * @param newScore The new score calculated from the opponents' cards.
     */
    public void updateScore(int newScore) {
        score = newScore; // Increase the score if the player has won.
    }

    /**
     * Checks if Uno has been reminded by the player.
     *
     * @return True if Uno has been reminded; otherwise, false.
     */
    public boolean hasRemindedUno() {
        return remindedUno;
    }

    /**
     * Sets the Uno reminder status for the player.
     *
     * @param remindedUno True to indicate Uno has been reminded; otherwise, false.
     */
    public void setRemindedUno(boolean remindedUno) {
        this.remindedUno = remindedUno;
    }

    /**
     * Display "UNO!" if the player has one card left and attempts to call Uno.
     */
    public void sayUno() {
        if (hasUno()) {
            System.out.println(getName() + " says UNO!");
            callUno();
        } else {
            System.out.println(getName() + " tried to say UNO, but they have more than one card left.");
        }
    }


}
