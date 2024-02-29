import javax.swing.JButton;
import java.awt.*;

public class CardButton extends JButton {
    private final Card card;

    public CardButton(Card card) {
        this.card = card;
        // Set the button text to the card's string representation
        this.setText(card.stringCard());
        // Optionally set the button color based on the card color
        this.setBackground(getColorForCard(card));
        // Set action command to identify the card when an action is performed
        this.setActionCommand(card.stringCard());
    }

    private Color getColorForCard(Card card) {
        switch (card.getColor()) {
            case RED:
                return Color.RED;
            case GREEN:
                return Color.GREEN;
            case BLUE:
                return Color.BLUE;
            case YELLOW:
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
    }

    public Card getCard() {
        return card;
    }
}

