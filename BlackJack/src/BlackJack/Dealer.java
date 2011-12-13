package blackjack;

/**
 * This class represents computer controlled dealer
 * 
 * @author Vaclav Haramule https://github.com/XerorBattler
 * @version 1.2
 */
public class Dealer {
    private Hand hand;
    public Dealer()
    {
        hand = new Hand();
    }
    /**
     * Add card to dealers packet
     * 
     * @param card added card
     */
    public void addCard(Card card)
    {
        this.hand.addCard(card);
    }
    /**
     * Gets dealer combined score
     * 
     * @return array with length 2, first positions saves points second card count
     */
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
    /**
     * Method which return String with dealers info
     * 
     * @return dealer info
     */
    @Override
    public String toString() {
        return "Dealer(" + hand.getCardSum() + "): " + hand.showCards();
    }
    
}
