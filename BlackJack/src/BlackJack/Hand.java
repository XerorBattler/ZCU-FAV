package blackjack;

import java.util.LinkedList;

/**
 * Class which represent player hand
 *  
 * @author Vaclav Haramule https://github.com/XerorBattler
 * @version 1.3
 */
public class Hand {
    private LinkedList<Card> hand;
    /**
     * Hand constructor
     * 
     * @param owner player
     */
    public Hand()
    {
        this.hand = new LinkedList<Card>();
    }
    /**
     * Adds card to player hand
     * 
     * @param card added card
     */
    public void addCard(Card card)
    {
        this.hand.add(card);
    }
    /**
     * Counts card value in hand, getter
     * 
     * @return int card sum
     */
    public int getCardSum()
    {
        int sum=0;
        int ace=0;
        if(hand.size() == 0)return 0;
        for(Card card : hand)
        {
            if(card.getValue()==11)
            {
                ace++;
            }
            sum+=card.getValue();
        }
        if(ace==2 && sum==22)return 21;
        while(sum>21 && ace>0)
        {
            sum-=10;
            ace--;
        }
        return sum;
    }
    public Card takeCard()
    {
        return this.hand.pollLast();
    }
    /**
     * Method which puts all cards names into String
     * 
     * @return String cards in String
     */
    public String showCards()
    {
        String ret = "";
        if(hand.size() == 0)return null;
        for(Card card : hand)
        {
            ret += card.getName()+" ";
        }
        return ret;
    }
    /**
     * Verify if the cards are the same, if so returns true
     * 
     * @return boolean true if are cards same
     */
    public boolean topCardsSame()
    {
        if(hand.size() == 2)
        {
            if(hand.get(0).getValue() == hand.get(1).getValue())
            {
                return true;
            }
        }
        return false;
    }
    /**
     * Counts cards, getter
     * 
     * @return int card count
     */
    public int getCountCards()
    {
        return this.hand.size();
    }
    /**
     * Creates String with card in hand list
     * 
     * @return String cards
     */
    @Override
    public String toString()
    {
        String ret="";
        for(Card card:hand)
        {   
            ret+=" | "+card.getName()+" "+card.getValue();
        }
        return ret;
    }
    
}
