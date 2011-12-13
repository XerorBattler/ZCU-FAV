package blackjack;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class handles almost all blackjack events, this class is remake of original bukkit plugin BlackJack
 * 
 * @author Vaclav Haramule https://github.com/XerorBattler
 * @version 2.0
 */
public class BlackJack
{
    //default vars
    private int minBet = 1;
    private int maxBet = 1000;
    private double blackJackRatio = 1.5;
    
    //static vars
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Bank bank;
    private static Packet packet;
    private static int activePlayer = 0;
    private static Dealer dealer;
    
    /**
     * Contructor which handles blackjack game
     * 
     * @param playerCount number of player in game
     */
    public BlackJack(int playerCount)
    {
        String name;
        bank = new Bank();
        for(int i = 0; i < playerCount; i++)
        {
            msg("Zadejte jmeno hrace " +(i + 1) + ": ", true);
            name = scanner.next();
            this.createPlayer(name);
            name = "";
        }
        scanner.nextLine();
        this.initGame();
        while(true)
        {
            if(this.getCommand() == false)break;
        }
        msg("Konec hry");
    }
    /**
     * This method is used for handling all player commands
     * 
     * @return false if player wants to quit
     */
    private boolean getCommand()
    {
        if(packet.getCardsLeft() == 0)
        {
            msg("Nemel bych, ale micham novy balicek!");
            packet = new Packet();
        }
        if(players.isEmpty())
        {
            msg("Zadni hraci nezustali ve hre!");
            return false;
        }
        if(this.noPlayersLeft())
        {
            this.prepareWinnerData();
            this.initGame();
            activePlayer = 0;
            return true;
        }
        if(players.get(activePlayer).getAccount().plays())
        {
            if(players.size() != 1)
            {
                msg("*** NYNI JE NA RADE HRAC " + players.get(activePlayer).getName() + " ***");
            }
            else
            {
                msg("****************");
            }
            if(players.get(activePlayer).getAccount().playerHasTooMuch())
            {
                msg("Sorry, presahl jsi 21!");
                players.get(activePlayer).getAccount().hold();
                return true;
            }
            if(players.get(activePlayer).getAccount().playerHasBlackJack())
            {
                msg("Mas kliku! BlackJack!");
                players.get(activePlayer).getAccount().hold();
                return true;
            }
            msg(players.get(activePlayer).getAccount().toString());
            msg("cmd: ", true);
            String command = scanner.nextLine();

            String[] split = command.split(" ");

            Player player = players.get(activePlayer);
            GameAccount gameAccount = player.getAccount();
           
            if(split[0].equalsIgnoreCase("hit") || split[0].equalsIgnoreCase("h"))
            {
                gameAccount.hit();
                this.nextPlayer();
            }
            else if(split[0].equalsIgnoreCase("hold") || split[0].equalsIgnoreCase("hd"))
            {
                gameAccount.hold();
                this.nextPlayer();
            }
            else if(split[0].equalsIgnoreCase("double") || split[0].equalsIgnoreCase("db"))
            {
                if(gameAccount.doubleBet())
                {
                    gameAccount.hold();
                    this.nextPlayer();
                }
            }
            else if(split[0].equalsIgnoreCase("split") || split[0].equalsIgnoreCase("sp"))
            {
                if(gameAccount.split())this.nextPlayer();
            }
            else if(split[0].equalsIgnoreCase("status"))
            {
                this.status();
            }
            else if(split[0].equalsIgnoreCase("q") || split[0].equalsIgnoreCase("quit"))
            {
                return false;
            }
            else if(split[0].equalsIgnoreCase("stav"))
            {
                this.stats();
            }
            else if(split[0].equalsIgnoreCase("help"))
            {
                this.help();
            }
            else
            {
                this.help();
            }
        }
        else
        {
            nextPlayer();
        }
        return true;
    }
    /**
     * Method which prepare every new round
     */
    private void initGame()
    {
        msg("Pripravuji hru...");
        packet = new Packet();
        dealer = new Dealer();
        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).getBank().getCash() < this.minBet)
            {
                msg("**** Hrac " + players.get(i).getName() + " byl vyrazen! ****");
                players.remove(i);
            }
        }
        for(Player player : players)
        {
            player.getAccount().addCard(packet.takeCard());
            player.getAccount().addCard(packet.takeCard());
        }
        dealer.addCard(packet.takeCard());
        for(int i = 0; i < players.size(); i++)
        {
            msg(players.get(i).getName() + " zadej sazku: ", true);
            int bet;
            int cash;
            try
            {
                bet = Integer.valueOf(scanner.next());
            }
            catch(Exception ex)
            {
                msg("Neplatna hodnota, automaticky sazim minimum");
                bet = minBet;
            }
            cash = players.get(i).getBank().getCash();
            if(bet <= cash)
            {
                players.get(i).getBank().removeCash(bet);
                players.get(i).getAccount().addBet(bet);
            }
            else if(bet < this.minBet)
            {
                msg("Neni mozne vsadit tak malo, sazim minimum!");
                players.get(i).getBank().removeCash(this.minBet);
                players.get(i).getAccount().addBet(this.minBet);
            }
            else if(bet > this.maxBet)
            {
                msg("Neni mozne vsadit tolik, sazim maximum!");
                players.get(i).getBank().removeCash(this.maxBet);
                players.get(i).getAccount().addBet(this.maxBet);
            }
            else
            {
                msg("Nemas dostatek kreditu! Sazim vse!");
                players.get(i).getBank().removeCash(cash);
                players.get(i).getAccount().addBet(cash);
            }
        }
        scanner.nextLine();
    }
    /**
     * Switches game to next player
     */
    private void nextPlayer()
    {
        activePlayer =(activePlayer + 1) % players.size();
    }
    /**
     * Getter for current active player
     * 
     * @return active player
     */
    public static Player getActivePlayer()
    {
        return players.get(activePlayer);
    }
    /**
     * This method checks if all player holds
     * 
     * @return true if all player holds
     */
    private boolean noPlayersLeft()
    {
        boolean bool = true;
        for(Player player : players)
        {
            if(player.getAccount().plays())
            {
                bool = false;
            }
        }
        return bool;
    }
    /**
     * This method return used card packet
     * 
     * @return card packet
     */
    public static Packet getPacket()
    {
        return packet;
    }
    /**
     * This method add new player to game
     * 
     * @param name player name
     */
    private void createPlayer(String name)
    {
        players.add(new Player(name));
    }
    /**
     * Method that shows help
     */
    private void help()
    {
        msg("BlackJack napoveda:");
        msg("Pro zapoceti nove hry: \"game\"");
        msg("Pro dalsi kartu: \"hit\"");
        msg("Pro cekani: \"hold\"");
        msg("Pro stav tveho uctu: \"stav\"");
        msg("Pro status hry: \"status\"");
        msg("Pro zobrazeni teto napovedy: \"help\"");
        msg("Nize uvedene prikazy lze pouzit pouze v prvnim kole!");
        msg("Pro zdvojnasobeni sazky: \"double\"");
        msg("Pro rozdeleni stejnych karet: \"split\"");
    }
    /**
     * Method which show all ingame players status
     */
    public void status()
    {
        String ret = "Stav hracu:";
        for(Player player : players)
        {
           ret += "\nHrac \"" + player.getName() + "\", aktivni hra: " +player.getAccount().plays() + ", konto: "+player.getBank().getCash(); 
        }
        msg(ret);
    }
    /**
     * Method which show stats of current player
     */
    public void stats()
    {
        msg(players.get(activePlayer).getAccount().stats());
    }
    /*
     * This method is first phase of preparing round results
     */
    private void prepareWinnerData()
    {
        msg("Vyhodnoceni: ");
        int[] score = dealer.getScore();
        int[] dealerScore = dealer.getScore();
        int dealerPoints = score[0];
        int dealerCards = score[1];
        GameAccount account;
        int playerPoints = 0;
        int playerCards = 0;
        msg(dealer.toString());
        for(Player player : players)
        {
            account = player.getAccount();
            boolean splitedHand = account.haveSplitted();
            score = account.getScore(false);
            this.solve(player, true, calculateWinner(dealerScore[0], dealerScore[1], score[0], score[1]));
            if(splitedHand)
            {
                score = account.getScore(true);
                this.solve(player, false, calculateWinner(dealerScore[0], dealerScore[1], score[0], score[1]));
            }
        }
    }
    /**
     * This method is second phase of preparing round results, in this method are ALL USED BLACKJACK RULES
     */    

    private int calculateWinner(int dealerPoints, int dealerCards, int playerPoints, int playerCards)
    {
        if(playerPoints == 21 && playerCards == 2 && dealerPoints!=21)return 3;
        if(playerPoints > 21)return 0;
        if(dealerPoints > 21)return 1;
        if(playerPoints == dealerPoints && playerCards < dealerCards)
        {
            return 1;
        }
        if(playerPoints == dealerPoints && playerCards == dealerCards)
        {
            return 2;
        }
        if(dealerPoints == 21 && playerPoints < 21)return 0;
        if(playerPoints > dealerPoints)
        {
            return 1;
        }
        return 0;
    }
    /**
     * This method is third phase of preparing round results
     */
    private void solve(Player player,boolean type, int status)
    {
        int modifyCash = 0;
        if(status == 0)
        {
            player.sendMessage("Bohuzel prohral jsi " + player.getAccount().getBet(type) + ", hraci " + player.getName());
            player.getAccount().lose();
        }
        else if(status == 1)
        {
            player.sendMessage("Gratuluji, vyhral jsi " + player.getAccount().getBet(type) + ", hraci " + player.getName());
            modifyCash = 2 * player.getAccount().getBet(type);
            player.getAccount().won();
        }
        else if(status == 3)
        {
            player.sendMessage("Blackjack! Gratuluji, vyhral jsi " + (int)(this.blackJackRatio * player.getAccount().getBet(type)) + ", hraci " + player.getName());
            modifyCash = player.getAccount().getBet(type) + (int)(player.getAccount().getBet(type) * this.blackJackRatio);
            player.getAccount().won();
        }
        else
        {
            player.sendMessage("Remiza, hraci " + player.getName());
            modifyCash = player.getAccount().getBet(type);
            player.getAccount().tie();
        }
        player.getBank().setCash(player.getBank().getCash() + modifyCash);
    }
    //below are all console prints in special form
    public static void msg(int numberInText)
    {
        msg(numberInText, false);
    }
    public static void msg(int numberInText, boolean noNewLine)
    {
        msg(String.valueOf(numberInText), noNewLine);
    }
    public static void msg(String text)
    {
        msg(text, false);
    }
    public static void msg(String text, boolean noNewLine)
    {
        if(noNewLine)
        {
            System.out.print(text);
        }
        else
        {
            System.out.println(text);
        }
    }
}
