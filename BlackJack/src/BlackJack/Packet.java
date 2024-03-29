package blackjack;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

/**
 * Card packet class
 * 
 * @author Vaclav Haramule https://github.com/XerorBattler
 * @version 1.2
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
        this.shuffle();
    }
    private void shuffle()
    {
        Collections.shuffle(this.packet, new Random(System.currentTimeMillis()/1000));
        Collections.shuffle(this.packet, new Random(System.currentTimeMillis()/1000));
    }
    
}
