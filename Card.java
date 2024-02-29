import java.util.Random;

/**
 * The `Card` class represents Uno cards with a specific color and value.
 */
public class Card {
    /**
     * Enum for the possible values of light Uno cards, including numbers, special cards, and wild cards.
     */
    public enum Value {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, REVERSE, FLIP, SKIP, WILD, WILD_DRAW_TWO_CARDS}

    /**
     * Enum for the possible colors of Uno cards.
     */
    public enum Color {RED, GREEN, BLUE, YELLOW}

    /**
     * Enum for the possible values of dark Uno cards, including numbers, special cards, and wild cards.
     */
    public enum DarkValue {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, REVERSE, FLIP, SKIP_EVERYONE, WILD, DRAW_FIVE}

    /**
     * Enum for the possible dark colors of Uno cards.
     */
    public enum DarkColor {ORANGE, PINK, PURPLE, TEAL}

    public enum CardSide {
        LIGHT, DARK
    }

    private Value value;  // The value of the card.
    private Color color;  // The color of the card.
    private DarkValue darkValue;
    private DarkColor darkColor;
    private String currentSide;

    /**
     * Constructs a random Uno card with a random color and value.
     */
    public Card(String side) {
        this.currentSide = side;
        Random random = new Random();
        if (currentSide.equals("LIGHT")) {
            this.color = Color.values()[random.nextInt(Color.values().length)];
            this.value = Value.values()[random.nextInt(Value.values().length)];
            this.darkColor = getOppositeColorToDark();
            this.darkValue = getOppositeValueToDark();
            System.out.println(color + " " + value);
        } else if (currentSide.equals("DARK")) {
            this.darkColor = DarkColor.values()[random.nextInt(DarkColor.values().length)];
            this.darkValue = DarkValue.values()[random.nextInt(DarkValue.values().length)];
            this.color = getOppositeColorToLight();
            this.value = getOppositeValueToLight();
        }
    }

    private DarkColor getOppositeColorToDark() {
        switch (color) {
            case YELLOW:
                return DarkColor.PURPLE;
            case BLUE:
                return DarkColor.PINK;
            case RED:
                return DarkColor.ORANGE;
            case GREEN:
                return DarkColor.TEAL;
        }
        return null;
    }

    private Color getOppositeColorToLight() {
        switch (darkColor) {
            case PURPLE:
                return Color.YELLOW;
            case PINK:
                return Color.BLUE;
            case ORANGE:
                return Color.RED;
            case TEAL:
                return Color.GREEN;
        }
        return null;
    }

    private DarkValue getOppositeValueToDark() {
        switch (value) {
            case FLIP:
                return DarkValue.FLIP;
            case REVERSE:
                return DarkValue.REVERSE;
            case WILD:
                return DarkValue.WILD;
            case SKIP:
                return DarkValue.SKIP_EVERYONE;
            case WILD_DRAW_TWO_CARDS:
                return DarkValue.DRAW_FIVE;
            case ONE:
                return DarkValue.ONE;
            case TWO:
                return DarkValue.TWO;
            case THREE:
                return DarkValue.THREE;
            case FOUR:
                return DarkValue.FOUR;
            case FIVE:
                return DarkValue.FIVE;
            case SIX:
                return DarkValue.SIX;
            case SEVEN:
                return DarkValue.SEVEN;
            case EIGHT:
                return DarkValue.EIGHT;
            case NINE:
                return DarkValue.NINE;
        }
        return null;
    }

    private Value getOppositeValueToLight() {
        switch (darkValue) {
            case FLIP:
                return Value.FLIP;
            case REVERSE:
                return Value.REVERSE;
            case WILD:
                return Value.WILD;
            case SKIP_EVERYONE:
                return Value.SKIP;
            case DRAW_FIVE:
                return Value.WILD_DRAW_TWO_CARDS;
            case ONE:
                return Value.ONE;
            case TWO:
                return Value.TWO;
            case THREE:
                return Value.THREE;
            case FOUR:
                return Value.FOUR;
            case FIVE:
                return Value.FIVE;
            case SIX:
                return Value.SIX;
            case SEVEN:
                return Value.SEVEN;
            case EIGHT:
                return Value.EIGHT;
            case NINE:
                return Value.NINE;
        }
        return null;
    }

    /**
     * Constructs a Uno card with a specified color and value.
     *
     * @param color The color of the card.
     * @param value The value of the card.
     */
    public Card(Color color, Value value, String side) {
        if (side.equals("LIGHT")) {
            this.color = color;
            this.value = value;
        }
    }


    /**
     * Generates a random Uno card to be used as the top card, excluding special cards.
     *
     * @return A random Uno card.
     */
    public static Card generate_top_card() {
        Card card;
        Random random = new Random();
        do {
            card = new Card("LIGHT");
        } while (card.isSpecialCard());
        return card;
    }

    /**
     * Checks if the card is a special card (wild, wild draw two, reverse, or skip).
     *
     * @return `true` if the card is a special card, otherwise `false`.
     */
    public boolean isSpecialCard() {
        return value == Value.WILD ||
                value == Value.WILD_DRAW_TWO_CARDS ||
                value == Value.REVERSE ||
                value == Value.SKIP ||
                value == Value.FLIP;
    }

    /**
     * Gets the color of the Uno card.
     *
     * @return The color of the card.
     */
    public Color getColor() {
        return color;
    }

    public DarkColor getDarkColor() {
        return darkColor;
    }

    /**
     * Gets the value of the Uno card.
     *
     * @return The value of the card.
     */
    public Value getValue() {
        return value;
    }

    public DarkValue getDarkValue() {
        return darkValue;
    }

    public String getCurrentside() {
        return this.currentSide;
    }

    public void setCurrentside(String side) {
        this.currentSide = side;
    }

    /**
     * Returns a string representation of the Uno card.
     * If the card's value is a wild card, it returns only the value; otherwise, it returns both the color and value.
     *
     * @return A string representation of the Uno card.
     */
    public String stringCard() {
        if (value == Value.WILD || value == Value.WILD_DRAW_TWO_CARDS) {
            return value.toString();
        } else {
            return color.toString() + " " + value.toString();
        }
    }

    public String stringDarkCard() {
        if (darkValue == DarkValue.WILD) {
            return darkValue.toString();
        } else {
            return darkColor.toString() + " " + darkValue.toString();
        }
    }

}
