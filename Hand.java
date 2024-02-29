import java.util.ArrayList;

/**
 * The `Hand` class represents a player's hand in the Uno card game, which holds a collection of Uno cards.
 */
public class Hand {
    private int start_cards = 7;  // The initial number of cards in the hand.
    private ArrayList<Card> cards;  // The list of cards in the hand.
    private int num_cards;  // The number of cards currently in the hand.

    /**
     * Constructs a player's hand by randomly selecting an initial set of cards (7 cards by default).
     */
    public Hand() {
        cards = new ArrayList<Card>();
        for (int i = 0; i < start_cards; i++) {
            Card card = new Card("LIGHT");
            cards.add(card);
            num_cards += 1;
        }
    }

    /**
     * Adds a new card to the hand.
     *
     * @return The card added to the hand.
     */
    public Card addCard(String side) {
        cards.add(new Card(side));
        num_cards += 1;
        return cards.get(num_cards - 1);
    }

    /**
     * Removes a specified card from the hand.
     *
     * @param card The card to be removed from the hand.
     */
    public void removeCard(Card card) {
        cards.remove(card);
        num_cards -= 1;
    }

    /**
     * Prints all the cards in the hand, displaying their colors and values.
     */
    public void printAll() {
        for (Card card : cards) {
            System.out.print(card.getColor().toString() + " " + card.getValue().toString() + ", ");
        }
    }

    /**
     * Gets the list of cards in the hand.
     *
     * @return The ArrayList containing the cards in the hand.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Gets the number of cards currently in the hand.
     *
     * @return The number of cards in the hand.
     */
    public int getNumCards() {
        return num_cards;
    }
}



