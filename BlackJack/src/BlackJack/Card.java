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
    /**
     * Card constructor
     * 
     * @param cardName String card name
     * @param cardValue int card value
     * @param cardColor ChatColor card color
     */
    public Card(String cardName, int cardValue)
    {
        this.cardName=cardName;
        this.cardValue=cardValue;
    }
    /**
     * Card name getter
     * 
     * @return String card name
     */
    public String getName()
    {
        return this.cardName;
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
