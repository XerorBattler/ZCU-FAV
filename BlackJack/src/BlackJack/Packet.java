package blackjack;

import java.util.LinkedList;

/**
 * Card packet class
 * 
 * @author Xeror Battler
 * @version 1.1
 */
public class Packet {
    private LinkedList<Card> packet = new LinkedList<Card>();
    /**
     * Creates shuffled packet
     */
    public Packet()
    {
        createNewPacket();
    }
    /**
     * Method which takes random card from packet
     * 
     * @return Card card
     */
    public Card takeCard()
    {
        Card card = packet.pollLast();
        return card;
    }
    /**
     * Method which count card value
     * 
     * @return int packet value
     */
    public int packetValue()
    {
        int sum=0;
        for(Card card:packet)
        {   
            if(card==null)continue;
            sum+=card.getValue();
        }
        return sum;
    }
    /**
     * Getter for card count
     * 
     * @return int card count 
     */
    public int getCardsLeft()
    {
        return this.packet.size();
    }
    /**
     * Creates String with card list
     * 
     * @return String cards
     */
    @Override
    public String toString()
    {
        String ret="";
        for (Card card : packet)
        {
            if(card==null)continue;
            ret+=" "+card.getName();
        }
        return ret;
    }
    private void createNewPacket()
    {
        int value=0;
        String text="";
        for(int i=0;i<4;i++)
        {
            String color;
            if(i < 2)
            {
                color = "B";
            }
            else
            {
                color = "R";
            }
            for(int j=0;j<13;j++)
            {
                if(j<9)
                {
                    text=String.valueOf(j+2);
                    value=j+2;
                }
                else
                {
                    switch(j){
                        case 9:
                            text="J";
                            value=10;
                        break;
                        case 10:
                            text="Q";
                            value=10;
                        break;
                        case 11:
                            text="K";
                            value=10;
                        break;
                        case 12:
                            text="A";
                            value=11;
                        break;
                    }
                }
                this.packet.add(new Card(text, value, color));
            }
        }
        //Collections.shuffle(Arrays.asList(this.packet));
        //Collections.shuffle(Arrays.asList(this.packet));
    }
    
    
}
