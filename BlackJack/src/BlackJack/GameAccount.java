package blackjack;

/**
 *
 * @author Xeror Battler
 */
public class GameAccount {
    private int totalPlayed;
    private int wins;
    private int loses;
    private int cash;
    private int bet;
    private int splitBet;
    //old
    
    
    private Hand hand;
    private Hand splitedHand;
    
    private boolean hold = false;
    
    private int round;
    private int dealerRound;
    
    private boolean display=false;
    private double blackJackRatio=1.5;
    public GameAccount()
    {
        this.totalPlayed = 0;
        this.wins = 0;
        this.loses = 0;
        this.hand = new Hand();
        this.splitedHand = new Hand();
    }
    public boolean plays()
    {
        if(this.round>0)return true;
        return hold;
    }
    public void addCard(Card card)
    {
        this.hand.addCard(card);
    }
    public void addBet(int bet)
    {
        this.bet = bet;
    }
    public void hit(Packet packet)
    {
        Card card = packet.takeCard();
        this.hand.addCard(card);
        run.log("Dostal jsi " + card.getName() + ", soucet " + hand.getCardSum());
    }
    public void hold()
    {
        this.hold = true;
        run.log("Soucet karet " + hand.getCardSum());
//        int playerCards=(round>=50)?(round-50):round;
//        int betCash=0;
//        player.sendMessage("Your points: "+((round>=50)?splitedHand.getCardSum():hand.getCardSum())+", cards: "+((round>50)?splitedHand.showCards():hand.showCards()));
//        int playerScore=(this.round>=50)?splitedHand.getCardSum():hand.getCardSum();
//        int dealerScore=0;
//        if(playerScore<=21)
//        {
//            dealerScore=dealer(player, this.packet,playerScore,playerCards);
//        }
//        else
//        {
//            player.sendMessage("Sorry, you are over 21!");
//        }
//        String betString=(this.bet>0)?String.valueOf(this.bet):"";
//        //debug
//        //this.server.broadcastMessage("BlackJack DEBUG ~ BET: "+((this.bet!=0)?this.bet:"N/A")+", PS:"+((playerScore!=0)?playerScore:"N/A")+", PC:"+((playerCards!=0)?playerCards:"N/A")+", DS:"+((dealerScore!=0)?dealerScore:"N/A")+", DC:"+((this.dealerRound!=0)?this.dealerRound:"N/A"));
//        if((playerScore<=21 && (playerScore>dealerScore ||(dealerScore>21&&playerScore<=21)))||(playerScore<=21&&playerScore==dealerScore&&playerCards<this.dealerRound))
//        {
//            betCash=(this.bet>0)?Integer.parseInt(betString):0;
//            int wonCash=betCash;
//            if(wonCash>0)
//            {
//                if(splitedHand!=null && this.round<50)
//                {
//                    wonCash/=2;
//                    betCash/=2;
//                }
//                if(hand!=null && hand.isBlackJack())
//                {
//                    wonCash*=this.blackJackRatio;
//                }
//                else if(splitedHand!=null && splitedHand.isBlackJack())
//                {
//                    wonCash*=this.blackJackRatio;
//                }
//            }
//            player.sendMessage("You won "+wonCash+"!");
//            if(this.bet>0)
//            {
//                this.cash+=betCash+wonCash;
//            }
//            if(display)
//            {
//             //   System.out.println(this.player.getDisplayName()+" has won "+wonCash+" in BlackJack");
//            }
//            if(this.round>50 && this.splitedHand!=null)
//            {
//                this.splitedHand=null;
//            }
//            else if(this.round<50 && this.splitedHand!=null)
//            {
//                this.hand=null;
//            }
//        }
//        else if(playerScore <= 21 && playerScore==dealerScore && playerCards==this.dealerRound)
//        {
//            player.sendMessage("It's a tie!");
//            if(this.bet>0)
//            {
//                this.cash+=(splitedHand!=null && this.round<50)?Integer.parseInt(betString):Integer.parseInt(betString)/2.0;
//            }
//            if(this.round>50 && this.splitedHand!=null)
//            {
//                this.splitedHand=null;
//            }
//            else if(this.round<50 && this.splitedHand!=null)
//            {
//                this.hand=null;
//            }
//        }
//        else
//        {
//            betCash=(this.bet>0)?Integer.parseInt(betString):0;
//            if(betCash>0)
//            {
//                if(splitedHand!=null && this.round<50)
//                {
//                    betCash/=2;
//                }
//            }
//            player.sendMessage("You lose "+betCash+"!");
//           // if(this.bet>0 && BlackJack.getInstance().getAnnounce())
//            //{
//            //    System.out.println(this.player.getDisplayName()+" has lost "+betCash+" in BlackJack");
//           // }
//            if(this.round>50 && this.splitedHand!=null)
//            {
//                this.splitedHand=null;
//            }
//            else if(this.round<50 && this.splitedHand!=null)
//            {
//                this.hand=null;
//            }
//        }
//        this.round=(this.splitedHand!=null)?51:0;
//        this.bet=(this.round>=50)?this.bet/2:0;
//        if(this.round==51)
//        {
//            player.sendMessage("One more hand left ("+splitedHand.showCards()+")");
//        }
//        else{
//            this.round=0;
//        }
    }
    public boolean canSplit()
    {
        return this.hand.topCardsSame();
    }
//    public void nextCard()
//    {
//        
//        Hand temp=(this.splitedHand!=null&&this.round>=50)?this.splitedHand:this.hand;
//        if(this.round == 1 && temp!=null && temp.topCardsSame() && this.splitedHand==null)
//        {
//            player.sendMessage("Muzes sve karty rozdelit: \"split\"");
//        }
//        temp.addCard(this.packet.takeCard());
//        this.round++;
//        player.sendMessage("Kolo "+((this.round>=50)?(this.round-50):this.round));
//        player.sendMessage("v ruce: "+temp.showCards());
//        if (temp.getCardSum()>21)
//        {
//            hold(player);
//        }
//    }
   
    public void split(Player player)
    {
        if(this.cash>=(this.bet*2) && this.round==2 &&this.splitedHand==null && this.hand!=null && this.hand.topCardsSame())
        {
            this.cash-=bet;
            this.bet*=2;
            this.splitedHand=new Hand();
            this.splitedHand.addCard(this.hand.split());
            this.round--;
            player.sendMessage("You splited the cards!");
        }
        else if(this.splitedHand!=null)
        {
            player.sendMessage("You can split only once!");
        }
        else if(this.hand.topCardsSame())
        {
            player.sendMessage("You need two same cards for split");
        }
        else if(this.round!=2)
        {
            player.sendMessage("You can split only in second round!");
        }
        else if(this.cash<(this.bet*2))
        {
            player.sendMessage("You don't have enough credit!");
        }
        else
        {
            player.sendMessage("You can't split now!");
        }
    }
    private int dealer(Player player, Packet packet, int playerScore, int playerCards)
    {
        Hand dealerHand=new Hand();
        int sum=0;
        this.dealerRound=0;
        while(sum < 21 && (sum < playerScore || sum <=17))
        {
            this.dealerRound++;
            dealerHand.addCard(packet.takeCard());
            sum=dealerHand.getCardSum();
            if(playerScore>21 && sum>=17 || playerScore<sum && sum>=17)break;
            if(playerScore==21&&playerCards<=this.dealerRound)break;
        }
        player.sendMessage("Dealer's points: "+dealerHand.getCardSum()+" ( "+dealerHand.showCards()+")");
        return sum;
    }
    public void doubleBet(Player player)
    {
//        if(this.round==1){
//            if(this.cash>=this.bet)
//            {
//                this.cash-=this.bet;
//                this.bet*=2;
//                player.sendMessage("You doubled your bet");
//                //nextCard(player);
//                hold(player);
//            }
//            else
//            {
//                player.sendMessage("You don't have enough credit!");
//            }
//        }
//        else
//        {
//            player.sendMessage("You can't double the bet!");
//        }
    }

    @Override
    public String toString()
    {
        if(this.hand.getCountCards() > 0 && this.splitedHand.getCountCards() > 0)
        {
            return "\nRuka 1 (sazka: " + this.bet + "), soucet: " + this.hand.getCardSum() + ", karty: " + this.hand.showCards()
            + "\nRuka 2 (sazka: " + this.splitBet + "), soucet: " + this.splitedHand.getCardSum() + ", karty: " + this.splitedHand.showCards();
        }
        else if(this.splitedHand.getCountCards() == 0)
        {
            return "(sazka: " + this.bet +"), soucet: " + this.hand.getCardSum() + ", karty: " + this.hand.showCards();
        }
        return "nema karty";
    }
}