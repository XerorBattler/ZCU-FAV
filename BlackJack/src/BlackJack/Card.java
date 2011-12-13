package blackjack;

/**
 * Card class
 * 
 * @author Xeror Battler
 * @version 1.0
 */
public class Card {
    private String cardName;
    private int cardValue;
    private String color;
    /**
     * Card constructor
     * 
     * @param cardName String card name
     * @param cardValue int card value
     */
    public Card(String name, int value, String color)
    {
        this.cardName = name;
        this.cardValue = value;
        this.color = color; 
    }
    /**
     * Card name getter
     * 
     * @return String card name
     */
    public String getName()
    {
        return this.color + this.cardName;
    }
    /**
     * Card value getter
     * 
     * @return int card value
     */
    public int getValue()
    {
        return this.cardValue;
    }
}
