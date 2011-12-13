package blackjack;

/**
 *
 * @author Xeror Battler
 */
public class GameAccount {
    private int totalPlayed;
    private int wins;
    private int loses;
    private int bet;
    private int splitBet;
    
    
    private Hand hand;
    private Hand splitedHand;
    
    private int round;
    
    private int splitedStatus = 0;

    private boolean hold = false;
    public GameAccount()
    {
        this.totalPlayed = 0;
        this.wins = 0;
        this.loses = 0;
        this.round = 0;
        this.hand = new Hand();
        this.splitedHand = new Hand();
    }
    public boolean plays()
    {
        return !hold;
    }
    public void addCard(Card card)
    {
        this.hand.addCard(card);
    }
    public void addBet(int bet)
    {
        this.bet += bet;
    }
    public void hit()
    {
        Card card = BlackJack.getPacket().takeCard();
        Player player = BlackJack.getActivePlayer();
        BlackJack.msg("Dostal jsi " + card.getName() + ", soucet ", true);
        if(this.splitedStatus == 2)
        {
            this.splitedHand.addCard(card);
            player.sendMessage(this.splitedHand.getCardSum() + ", karty: " + this.splitedHand.showCards());
        }
        else
        {
            this.hand.addCard(card);
            player.sendMessage(this.hand.getCardSum() + ", karty: " + this.hand.showCards());
        }
        this.round++;
        
    }
    public void hold()
    {
        Player player = BlackJack.getActivePlayer();
        this.round = 0;
        if(this.splitedStatus == 2)
        {
            player.sendMessage("Druha ruka uzavrena s hodnotou " + splitedHand.getCardSum() + ", karty: " + splitedHand.showCards());
            this.hold = true;
        }
        else if(this.splitedStatus == 1)
        {
            this.splitedStatus++;
            player.sendMessage("Prvni ruka uzavrena s hodnotou " + hand.getCardSum() + ", karty: " + hand.showCards());
            player.sendMessage("Mas jeste jedny karty!");
        }
        else
        {
            player.sendMessage("Uzavreno s hodnotou " + hand.getCardSum() + ", karty: " + hand.showCards());
            this.hold = true;
        }
    }
    public boolean doubleBet()
    {
        Player player = BlackJack.getActivePlayer();
        if(this.round > 0)
        {
            player.sendMessage("Zdvojnasobeni sazky je mozne jen v prvnim kole");
            return false;
        }
        if(player.getBank().getCash() >= this.bet)
        {
            player.getBank().removeCash(this.bet);
            this.bet *= 2;
            this.hit();
            return true;
        }
        else
        {
            player.sendMessage("Neni mozne zdvojnasobit sazku! Nemas dostatek kreditu!");
            return false;
        }
    }
    public boolean split()
    {
        Player player = BlackJack.getActivePlayer();
        if(this.splitedStatus == 0 && this.round == 0)
        {
            if(this.hand.topCardsSame() && player.getBank().getCash() >= this.bet)
            {
                Packet packet = BlackJack.getPacket();
                
                this.splitedHand.addCard(this.hand.takeCard());
                
                this.splitBet = this.bet;
                
                Card first = packet.takeCard();
                Card second = packet.takeCard();
                
                this.hand.addCard(first);
                this.splitedHand.addCard(second);
                
                
                player.sendMessage("Dostal si " + first.getName() + " (soucet = " + hand.getCardSum() + ") a pak " + second.getName() + " (soucet = " + splitedHand.getCardSum() + ")");
                this.splitedStatus++;
                return true;
            }
            else
            {
                player.sendMessage("Rozdeleni je mozne jen v pripade stejnych karet.");
            }
        }
        else if(this.round > 0)
        {
            player.sendMessage("Rozdeleni je mozne pouze v prvnim kole.");
        }
        else
        {
            player.sendMessage("Rozdelit karty muzete jen jednou.");
        }
        return false;
    }
    public boolean haveSplitted()
    {
        if(splitedStatus > 0)return true;
        return false;
    }
    public void lose()
    {
        this.totalPlayed++;
        this.loses++;
        this.clear();
    }
    public void won()
    {
        this.totalPlayed++;
        this.wins++;
        this.clear();
    }
    public void tie()
    {
        this.totalPlayed++;
        this.clear();
    }
    public void clear()
    {
        this.hand = new Hand();
        this.splitedHand = new Hand();
        this.hold = false;
        this.round = 0;
        this.splitedStatus = 0;
        this.bet = 0;
        this.splitBet = 0;
    }
    public int[] getScore(boolean type)
    {
        int[] score = new int[2];
        if(this.splitedStatus > 0 && type)
        {
            score[0] = this.splitedHand.getCardSum();
            score[1] = this.splitedHand.getCountCards();
        }
        else
        {
            score[0] = this.hand.getCardSum();
            score[1] = this.hand.getCountCards();
        }
        return score;
    }
    public int getBet(boolean type)
    {
        if(type)return this.bet;
        return this.splitBet;
    }
    public boolean playerHasTooMuch()
    {
        if(this.splitedStatus < 2 && this.hand.getCardSum() > 21)return true;
        else if(this.splitedHand.getCardSum() > 21)return true;
        return false;
    }
    @Override
    public String toString()
    {
        if(this.hand.getCountCards() > 0 && this.splitedHand.getCountCards() > 0)
        {
            return "Tvuj stav"
                + "\nRuka 1 (sazka: " + this.bet + "), soucet: " + this.hand.getCardSum() + ", karty: " + this.hand.showCards()
                + "\nRuka 2 (sazka: " + this.splitBet + "), soucet: " + this.splitedHand.getCardSum() + ", karty: " + this.splitedHand.showCards();
        }
        else if(this.splitedHand.getCountCards() == 0)
        {
            return "Tva sazka: " + this.bet +", soucet: " + this.hand.getCardSum() + ", karty: " + this.hand.showCards();
        }
        return "nema karty";
    }
}
