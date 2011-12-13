package blackjack;

/**
 * This class stores statistic and game data for players
 * 
 * @author Vaclav Haramule https://github.com/XerorBattler
 * version 1.2
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

    /**
     * Constructor which defines default values of stats
     */
    public GameAccount()
    {
        this.totalPlayed = 0;
        this.wins = 0;
        this.loses = 0;
        this.round = 0;
        this.hand = new Hand();
        this.splitedHand = new Hand();
    }
    /**
     * Returns if player plays
     * 
     * @return true if player dont hold
     */
    public boolean plays()
    {
        return !hold;
    }
    /**
     * Adds a card to player packet
     * 
     * @param card added card
     */
    public void addCard(Card card)
    {
        this.hand.addCard(card);
    }
    /**
     * Adds a bet to player account
     * 
     * @param bet bet ammount
     */
    public void addBet(int bet)
    {
        this.bet += bet;
    }
    /**
     * Defines player hit
     */
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
    /**
     * Defines player hold
     */
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
    /**
     * Defines player double
     * 
     * @return true if are bet doubled
     */
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
    /**
     * Defines player split
     * 
     * @return true if is hand splited
     */
    public boolean split()
    {
        Player player = BlackJack.getActivePlayer();
        if(this.splitedStatus == 0 && this.round == 0)
        {
            if(this.hand.topCardsSame() && player.getBank().getCash() >= this.bet)
            {
                Packet packet = BlackJack.getPacket();
                
                this.splitedHand.addCard(this.hand.takeCard());
                
                player.getBank().removeCash(this.bet);
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
    /**
     * Return bool value that show if player already splited
     * 
     * @return true if player already splited
     */
    public boolean haveSplitted()
    {
        if(this.hand.getCountCards()>0 && this.splitedHand.getCountCards()>0)return true;
        return false;
    }
    /**
     * Lose handler
     */
    public void lose()
    {
        this.loses++;
        this.clear();
    }
    /**
     * Win handler
     */
    public void won()
    {
        this.wins++;
        this.clear();
    }
    /**
     * Tie handler
     */
    public void tie()
    {
        this.clear();
    }
    /**
     * This method is for preparation for the next round
     */
    public void clear()
    {
        this.totalPlayed++;
        this.hand = new Hand();
        this.splitedHand = new Hand();
        this.hold = false;
        this.round = 0;
        this.splitedStatus = 0;
        this.bet = 0;
        this.splitBet = 0;
    }
    /**
     * Gets player combined score
     * 
     * @return array with length 2, first positions saves points second card count
     */
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
    /**
     * This method show how much player bet
     * 
     * @param type this defines if you want a splited bet or normal bet
     * @return bet value
     */
    public int getBet(boolean type)
    {
        if(type)return this.bet;
        return this.splitBet;
    }
    /**
     * This method check if players are above 21
     * 
     * @return true if player is above 21
     */
    public boolean playerHasTooMuch()
    {
        if(this.splitedStatus < 2 && this.hand.getCardSum() > 21)return true;
        else if(this.splitedHand.getCardSum() > 21)return true;
        return false;
    }
    /**
     * This method check if player have 21 with two cards
     * 
     * @return true if player have 2 cards and he have 21 card value
     */
    public boolean playerHasBlackJack()
    {
        if(this.splitedStatus < 2 && this.hand.getCardSum() == 21 && this.hand.getCountCards() == 2)return true;
        else if(this.splitedHand.getCardSum() == 21 && this.hand.getCountCards() == 2)return true;
        return false;
    }
    /**
     * This method show stats
     * 
     * @return text with stats
     */
    public String stats()
    {
        return "Pocet her: " + this.totalPlayed + ", vyher: " + this.wins + ", proher: " + this.loses + ", pomer(V/P): " + this.wins/this.loses;
    }
    /**
     * toString method used for player status
     * 
     * @return text with info
     */
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
