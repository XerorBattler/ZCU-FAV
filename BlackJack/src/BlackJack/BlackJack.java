package blackjack;

import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author Xeror Battler
 */
public class BlackJack
{

    private int minBet=1;
    private int maxBet=10000;
    private int minBuyIn=10;
    private int maxBuyIn=5000;
    private boolean announce=true;
    private boolean shortCmdsAllow=false;
    private double blackJackRatio=1.5;
    
    private static ArrayList<Player> players = new ArrayList<Player>();
    
    private Packet packet;
   
    private static int activePlayer = 0;
    
    private boolean activeGame = false;
    
    private Dealer dealer;
    
    private Scanner scanner = new Scanner(System.in);
    
    public BlackJack()
    {
    }
    public void nextPlayer()
    {
        activePlayer++;
        activePlayer %= players.size();
        if(!players.get(activePlayer).getAccount().plays())nextPlayer();
        else
        {
            run.log("Na rade je hrac " + players.get(activePlayer).getName());
            run.log(players.get(activePlayer).getAccount().toString());
            run.log("prikazy: \"hit\" \"hold\"");
        }
    }
    public boolean onCommand(String command)
    {
        String[] split=command.split(" ");
        
        if(split[0].equalsIgnoreCase("game") || split[0].equalsIgnoreCase("g"))
        {
            newGame();
        }
        else if(split[0].equalsIgnoreCase("hit") || split[0].equalsIgnoreCase("h"))
        {
            players.get(activePlayer).getAccount().hit(this.packet);
            nextPlayer();
        }
        else if(split[0].equalsIgnoreCase("hold"))
        {
            players.get(activePlayer).getAccount().hold();
            nextPlayer();
            
        }
        else if(split[0].equalsIgnoreCase("status"))
        {
            status();
        }
        else if(split[0].equalsIgnoreCase("help"))
        {
            help();
        }
        else if(split[0].equalsIgnoreCase("q") || split[0].equalsIgnoreCase("quit"))
        {
            return false;
        }
        return true;
    }        
    private void help()
    {
        run.log("BlackJack napoveda:");
        run.log("Pro zapoceti nove hry: \"game\"");
        run.log("Pro dalsi kartu: \"hit\"");
        run.log("Pro cekani: \"hold\"");
        run.log("Pro stav hracskych uctu: \"status\"");
        run.log("Pro zobrazeni teto napovedy: \"help\"");
    }
    public void status()
    {
        String ret = "Stav hracu:";
        for(Player player : players)
        {
           ret += "\nHrac \"" + player.getName() + "\", aktivni hra: " +player.getAccount().plays() + ", konto: "+player.getBank().getCash(); 
        }
        run.log(ret);
    }
    private void newGame()
    {
        if(activeGame)
        {
            run.log("Jedna hra uz se hraje");
        }
        activeGame = true;
        packet = new Packet();
        dealer = new Dealer();
        //michani karet
        //dealer.shuffle();
        //TODO
        for(int i = 0; i < players.size(); i++)
        {
            run.log(players.get(i).getName() + " zadej sazku");
            int bet;
            int cash;
            try
            {
                bet = Integer.valueOf(scanner.next());
            }
            catch(Exception ex)
            {
                run.log("Neplatna hodnota, automaticky sazim minimum");
                bet = minBet;
            }
            cash = players.get(i).getBank().getCash();
            if(bet <= cash)
            {
                players.get(i).getBank().removeCash(bet);
                players.get(i).getAccount().addBet(bet);
            }
            else
            {
                run.log("Nemas dostatek kreditu! Sazim vse!");
                players.get(i).getBank().removeCash(cash);
                players.get(i).getAccount().addBet(cash);
            }
        }
        for(int i = 0; i < 2*players.size(); i++)
        {
            Card card = packet.takeCard();
            players.get(i%players.size()).getAccount().addCard(card);
        }
        dealer.addCard(packet.takeCard());
        showAll();
        activePlayer = 0;
        run.log("Na rade je hrac " + players.get(activePlayer).getName());
        run.log(players.get(activePlayer).getAccount().toString());
        run.log("prikazy: \"hit\" \"hold\" \"double\"");
        if(players.get(activePlayer).getAccount().canSplit())
        {
            run.log("Muzes sve karty rozdelit: \"split\"");
        }
    }
    private void hit(Player player)
    {
        nextCard(player);
    }
    private void nextCard(Player player)
    {
        Card card = this.packet.takeCard();
        GameAccount account = player.getAccount();
        account.addCard(card);
    }
    public void createPlayer(String name)
    {
        activePlayer = players.size();
        players.add(new Player(name));
        
    }
    private static Player getPlayer(String name)
    {
        Player foundPlayer = null;
        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).getName().equalsIgnoreCase(name))
            {
                foundPlayer = players.get(i);
            }
        }
        return foundPlayer;
    }
    public static Player getActivePlayer()
    {
        return players.get(activePlayer);
    }
    public void showAll()
    {
        run.log(dealer.toString());
        for(Player player : players)
        {
            run.log("Hrac " + player.toString());
        }
    }
        //debug log
        //log.info("[BlackJack] "+player.getName()+" used command: "+Arrays.toString(split));
//                    if(INSTANCE.shortCmdsAllow && split[0].equalsIgnoreCase("hit"))
//                    {
//                        hit(playerAcc);
//                    }
//                    else if(INSTANCE.shortCmdsAllow && split[0].equalsIgnoreCase("stay"))
//                    {
//                        stay(playerAcc);
//                    }
//                    else if(INSTANCE.shortCmdsAllow && split[0].equalsIgnoreCase("double"))
//                    {
//                        doubleBet(playerAcc);
//                    }
//                    else if(INSTANCE.shortCmdsAllow && split[0].equalsIgnoreCase("split"))
//                    {
//                        splitCards(playerAcc);
//                    }
//                    else if(split.length>0)
//                    {
//                        if(split[0].equalsIgnoreCase("status"))
//                        {
//                            playerAcc.infoMessage("= BlackJack settings status =");
//                            playerAcc.infoMessage("Bet: "+INSTANCE.minBet+" to "+INSTANCE.maxBet);
//                            playerAcc.infoMessage("Buyin: "+INSTANCE.minBuyIn+" to "+INSTANCE.maxBuyIn);
//                            playerAcc.infoMessage("BJack ratio: "+INSTANCE.getBlackJackRatio());
//                            playerAcc.infoMessage("Announce: "+((INSTANCE.getAnnounce())?"ON":"OFF"));
//                            if(INSTANCE.getShortCmds())
//                            {
//                                playerAcc.infoMessage("Short commands: ON");
//                            }
//                        }
//                        else if(split[0].equalsIgnoreCase("game") || split[0].equalsIgnoreCase("g"))
//                        {
//                            game(playerAcc,stringToInt(split,2));
//                        }
//                        else if(split[0].equalsIgnoreCase("h") || split[0].equalsIgnoreCase("hit"))
//                        {
//                            hit(playerAcc);
//                        }
//                        else if(split[0].equalsIgnoreCase("s") || split[0].equalsIgnoreCase("stay"))
//                        {
//                            stay(playerAcc);
//                        }
//                        else if(split[0].equalsIgnoreCase("db") || split[0].equalsIgnoreCase("double"))
//                        {
//                            doubleBet(playerAcc);
//                        }
//                        if(split[0].equalsIgnoreCase("sp") || split[0].equalsIgnoreCase("split"))
//                        {
//                            splitCards(playerAcc);
//                        }
//                        else if(split[0].equalsIgnoreCase("checkin") || split[0].equalsIgnoreCase("ci"))
//                        {
//                            checkIn(playerAcc,stringToInt(split,2));
//                        }
//                        else if(split[0].equalsIgnoreCase("checkout") || split[0].equalsIgnoreCase("co"))
//                        {
//                            checkOut(playerAcc);
//                        }
//                        else if(split[0].equalsIgnoreCase("balance") || split[0].equalsIgnoreCase("b"))
//                        {
//                            playerAcc.infoMessage("Balance: "+playerAcc.getCash());
//                        }
//                        else if(split[0].equalsIgnoreCase("sh") || split[0].equalsIgnoreCase("show"))
//                        {
//                            showCards(playerAcc);
//                        }
//                    }
//                    else
//                    {
//                            help(playerAcc);
//                    }
//                }
//            }
//            catch(Exception e)
//            {
//                help(playerAcc);
//            }
//        }
//        return false;
//     private void checkOut(PlayerAccount playerAcc)
//    {
//        throw new NotImplementedException();        
//        //else
//        //{
////            playerAcc.sendMessage("Sorry, you can't checkout money here!");
////            playerAcc.sendMessage("Try /bjack "+"e"+"xchange"+"i"+"tem command!");
//        //}
//    }
// 
//    private void stay(Player player)
//    {
//        if(player.getAccount().plays())
//        {
//            player.getAccount().hold(player);
//        }
//        else
//        {
//            player.sendMessage("No game in progress, start new one with: /bjack "+"g"+"ame!");
//        }
//    }
//    private void doubleBet(Player player)
//    {
//        if(player.getAccount().plays())
//        {
//            player.getAccount().doubleBet(player);
//        }
//        else
//        {
//            player.sendMessage("No game in progress, start new one with: /bjack "+"g"+"ame!");
//        }
//    }
//    private void showCards(Player player)
//    {
//        if(player.getAccount().plays())
//        {
//            player.getAccount().showCards(player);
//        }
//        else
//        {
//            player.sendMessage("No game in progress, start new one with: /bjack "+"g"+"ame!");
//        }
//    }
//    private void splitCards(Player player)
//    {
//        if(player.getAccount().plays())
//        {
//            player.getAccount().split(player);
//        }
//        else
//        {
//            player.sendMessage("No game in progress, start new one with: /bjack "+"g"+"ame!");
//        }
//    }
//    private void checkIn(PlayerAccount playerAcc,int cash)
//    {
//        throw new NotImplementedException();
//    }
}
