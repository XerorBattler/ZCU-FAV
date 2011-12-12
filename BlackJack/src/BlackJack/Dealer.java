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
    @Override
    public String toString() {
        return "Dealer(" + hand.getCardSum() + "): " + hand.showCards();
    }
    
}
