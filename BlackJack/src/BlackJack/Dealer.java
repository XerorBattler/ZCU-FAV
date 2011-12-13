/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

/**
 *
 * @author Xeror
 */
public class Dealer {
    private Hand hand;
    public Dealer()
    {
        hand = new Hand();
    }
    public void addCard(Card card)
    {
        this.hand.addCard(card);
    }
    public int[] getScore()
    {
        while(hand.getCardSum() < 17)
        {
            hand.addCard(BlackJack.getPacket().takeCard());
        }
        int[] score = new int[2];
        score[0] = hand.getCardSum(); 
        score[1] = hand.getCountCards();
        return score;
    }
    @Override
    public String toString() {
        return "Dealer(" + hand.getCardSum() + "): " + hand.showCards();
    }
    
}
