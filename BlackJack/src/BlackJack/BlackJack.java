package blackjack;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
/**
 *
 * @author Xeror Battler
 */
public class BlackJack
{
    private PlayerAccount[] accounts;
    //config default values
    private static BlackJack INSTANCE;
    
    private int minBet=1;
    private int maxBet=10000;
    private int minBuyIn=10;
    private int maxBuyIn=5000;
    private boolean announce=true;
    private boolean shortCmdsAllow=false;
    private double blackJackRatio=1.5;
    //iconomy
    private boolean iConomyActive=false;
    private boolean BOSEconomyActive=false;
    //iconome subst.
    public BlackJack()
    {
    }
    public boolean onCommand(Player sender, String command) {
        
        
        String[] split=command.split(" ");
        
        split[0] = command.toLowerCase();

        Player player = (Player)sender;
        
        int pArrayPos=getPlayer(player);
        PlayerAccount playerAcc=INSTANCE.accounts[pArrayPos];
        //debug log
        //log.info("[BlackJack] "+player.getName()+" used command: "+Arrays.toString(split));
        if(pArrayPos>=0)
        {
            //if(args.length<1)
            try
            {
                if(split.length>=0)
                {
                    if(INSTANCE.shortCmdsAllow && split[0].equalsIgnoreCase("hit"))
                    {
                        hit(playerAcc);
                    }
                    else if(INSTANCE.shortCmdsAllow && split[0].equalsIgnoreCase("stay"))
                    {
                        stay(playerAcc);
                    }
                    else if(INSTANCE.shortCmdsAllow && split[0].equalsIgnoreCase("double"))
                    {
                        doubleBet(playerAcc);
                    }
                    else if(INSTANCE.shortCmdsAllow && split[0].equalsIgnoreCase("split"))
                    {
                        splitCards(playerAcc);
                    }
                    else if(split.length>0)
                    {
                        if(split[0].equalsIgnoreCase("status"))
                        {
                            playerAcc.infoMessage("= BlackJack settings status =");
                            playerAcc.infoMessage("Bet: "+INSTANCE.minBet+" to "+INSTANCE.maxBet);
                            playerAcc.infoMessage("Buyin: "+INSTANCE.minBuyIn+" to "+INSTANCE.maxBuyIn);
                            playerAcc.infoMessage("BJack ratio: "+INSTANCE.getBlackJackRatio());
                            playerAcc.infoMessage("Announce: "+((INSTANCE.getAnnounce())?"ON":"OFF"));
                            if(INSTANCE.getShortCmds())
                            {
                                playerAcc.infoMessage("Short commands: ON");
                            }
                        }
                        else if(split[0].equalsIgnoreCase("game") || split[0].equalsIgnoreCase("g"))
                        {
                            game(playerAcc,stringToInt(split,2));
                        }
                        else if(split[0].equalsIgnoreCase("h") || split[0].equalsIgnoreCase("hit"))
                        {
                            hit(playerAcc);
                        }
                        else if(split[0].equalsIgnoreCase("s") || split[0].equalsIgnoreCase("stay"))
                        {
                            stay(playerAcc);
                        }
                        else if(split[0].equalsIgnoreCase("db") || split[0].equalsIgnoreCase("double"))
                        {
                            doubleBet(playerAcc);
                        }
                        if(split[0].equalsIgnoreCase("sp") || split[0].equalsIgnoreCase("split"))
                        {
                            splitCards(playerAcc);
                        }
                        else if(split[0].equalsIgnoreCase("checkin") || split[0].equalsIgnoreCase("ci"))
                        {
                            checkIn(playerAcc,stringToInt(split,2));
                        }
                        else if(split[0].equalsIgnoreCase("checkout") || split[0].equalsIgnoreCase("co"))
                        {
                            checkOut(playerAcc);
                        }
                        else if(split[0].equalsIgnoreCase("balance") || split[0].equalsIgnoreCase("b"))
                        {
                            playerAcc.infoMessage("Balance: "+playerAcc.getCash());
                        }
                        else if(split[0].equalsIgnoreCase("sh") || split[0].equalsIgnoreCase("show"))
                        {
                            showCards(playerAcc);
                        }
                    }
                    else
                    {
                            help(playerAcc);
                    }
                }
            }
            catch(Exception e)
            {
                help(playerAcc);
            }
        }
        return false;
    }
    public boolean playerNotExists(Player player)
    {
        if(INSTANCE.accounts==null)return false;
        for(PlayerAccount playerListed:INSTANCE.accounts)
        {
            if(playerListed.getPlayerName().equalsIgnoreCase(player.getName()))return true;
        }
        return false;
    }
    public void addMoney(Player player, int value)
    {
        INSTANCE.accounts[INSTANCE.getPlayer(player)].addCash(value);
    }
     private void checkOut(PlayerAccount playerAcc)
    {
        throw new NotImplementedException();        
        //else
        //{
//            playerAcc.sendMessage("Sorry, you can't checkout money here!");
//            playerAcc.sendMessage("Try /bjack "+"e"+"xchange"+"i"+"tem command!");
        //}
    }
    private void game(PlayerAccount playerAcc,int cash)
    {
                    if(playerAcc.plays())
                    {
                        playerAcc.errorMessage("You already ingame, finish it before start new one!");
                    }
                    else
                    {
                        if(cash==0)
                        {
                            playerAcc.infoMessage("[TRAINING] Game without bets!");
                            playerAcc.dealCards(0);
                        }
                        else{
                            if(cash>0 && cash>=INSTANCE.minBet && cash<=INSTANCE.maxBet)
                            {
                                playerAcc.dealCards(cash);
                            }
                            else if(cash<INSTANCE.minBet || cash>INSTANCE.maxBet)
                            {
                                playerAcc.errorMessage("You can bet only in interval: "+INSTANCE.minBet+" to "+INSTANCE.maxBet);
                            }
                        }
                    }
    }
    private void hit(PlayerAccount playerAcc)
    {
        if(playerAcc.plays())
        {
            playerAcc.nextCard();
        }
        else
        {
            playerAcc.errorMessage("No game in progress, start new one with: /bjack "+"g"+"ame!");
        }
    }
    private void stay(PlayerAccount playerAcc)
    {
        if(playerAcc.plays())
        {
            playerAcc.hold();
        }
        else
        {
            playerAcc.errorMessage("No game in progress, start new one with: /bjack "+"g"+"ame!");
        }
    }
    private void doubleBet(PlayerAccount playerAcc)
    {
        if(playerAcc.plays())
        {
            playerAcc.doubleBet();
        }
        else
        {
            playerAcc.errorMessage("No game in progress, start new one with: /bjack "+"g"+"ame!");
        }
    }
    private void showCards(PlayerAccount playerAcc)
    {
        if(playerAcc.plays())
        {
            playerAcc.showCards();
        }
        else
        {
            playerAcc.errorMessage("No game in progress, start new one with: /bjack "+"g"+"ame!");
        }
    }
    private void splitCards(PlayerAccount playerAcc)
    {
        if(playerAcc.plays())
        {
            playerAcc.split();
        }
        else
        {
            playerAcc.errorMessage("No game in progress, start new one with: /bjack "+"g"+"ame!");
        }
    }
    private void checkIn(PlayerAccount playerAcc,int cash)
    {
        throw new NotImplementedException();
    }
    private void help(PlayerAccount playerAcc)
    {
        playerAcc.sendMessage("You have to checkin for cash games (/bjack "+"c"+"heck"+"i"+"n [value])");
        playerAcc.sendMessage("You can take all money with /bjack "+"c"+"heck"+"o"+"ut)");
        playerAcc.sendMessage("For game start use /bjack "+"g"+"ame [bet]");
        playerAcc.sendMessage("To show account balance use /bjack "+"b"+"alance");
        playerAcc.sendMessage("You can double bet in first round /bjack "+"d"+"ouble, /double");
        playerAcc.sendMessage("You can split same cards in sec. round /bjack "+"s"+"plit, /split");
    }
    public void setMinBet(int ammount)
    {
        INSTANCE.minBet=ammount;
    }
    public void setMaxBet(int ammount)
    {
        INSTANCE.maxBet=ammount;
    }
    public void setMinBuyIn(int ammount)
    {
        INSTANCE.minBuyIn=ammount;
    }
    public void setMaxBuyIn(int ammount)
    {
        INSTANCE.maxBuyIn=ammount;
    }
    public void setAnnounce(boolean bool)
    {
        INSTANCE.announce=bool;
    }
    public String getAccountList()
    {
        if(INSTANCE.accounts==null)return "";
        String ret="";
        for(PlayerAccount playerListed:INSTANCE.accounts)
        {
            if(playerListed!=null)
            {
                ret+=" "+playerListed.getPlayerName();
            }
        }
        return ret;
    }
    public boolean getAnnounce()
    {
        return INSTANCE.announce;
    }
    public boolean getShortCmds()
    {
        return INSTANCE.shortCmdsAllow;
    }
    public double getBlackJackRatio()
    {
        return INSTANCE.blackJackRatio;
    }
    public int getPlayer(Player player)
    {
        if(INSTANCE.accounts==null)
        {
            INSTANCE.accounts=new PlayerAccount[1];
            INSTANCE.accounts[0]=new PlayerAccount(player,INSTANCE.announce);
            return 0;
        }
        for(int i=0;i<INSTANCE.accounts.length;i++)
        {
            if(INSTANCE.accounts[i]==null)continue;
            if(INSTANCE.accounts[i].getPlayerName().equalsIgnoreCase(player.getName()))
            {
                return i;
            }
        }
        PlayerAccount[] temp=new PlayerAccount[INSTANCE.accounts.length+1];
        System.arraycopy(INSTANCE.accounts, 0, temp, 0, INSTANCE.accounts.length);
        temp[INSTANCE.accounts.length]=new PlayerAccount(player,INSTANCE.announce);
        INSTANCE.accounts=new PlayerAccount[temp.length];
        System.arraycopy(temp, 0, INSTANCE.accounts, 0, temp.length);
        return INSTANCE.accounts.length-1;
    }
    public static BlackJack getInstance()
    {
        return INSTANCE;
    }
    /**
     * Converts string to integer, returns positive number or zero.
     * 
     * @param args convertable splited command
     * @param pos position of number in array
     * @return converted number
     */
    private int stringToInt(String[] args,int pos)
    {
        try{
            if(args.length>=pos)
            {
                String numString=args[pos];
                if(numString.length()>0 && numString.length()<=9 && numString.charAt(0)>=48 && numString.charAt(0)<=57)
                {
                    return Math.abs(Integer.parseInt(numString));
                }
            }
        }
        catch(Exception e)
        {
            return 0;
        }
        return 0;
    }
    private String getFile()
    {
        return BlackJack.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }
   
}
