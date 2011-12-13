package blackjack;

import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author Xeror Battler
 */
public class BlackJack
{

    private static Scanner scanner = new Scanner(System.in);
    
    private int minBet=1;
    private int maxBet=10000;
    private int minBuyIn=10;
    private int maxBuyIn=5000;
    private boolean announce=true;
    private boolean shortCmdsAllow=false;
    private double blackJackRatio=1.5;
    
    //staticke promenne
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Packet packet;
    private static int activePlayer = 0;
    private static Dealer dealer;
    
    public BlackJack(int playerCount)
    {
        String name;
        for(int i = 0; i < playerCount; i++)
        {
            msg("Zadejte jmeno hrace " +(i + 1) + ": ", true);
            name = scanner.next();
            this.createPlayer(name);
            name = "";
        }
        scanner.nextLine();
        this.initGame();
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
            else
            {
                msg("Nemas dostatek kreditu! Sazim vse!");
                players.get(i).getBank().removeCash(cash);
                players.get(i).getAccount().addBet(cash);
            }
        }
        scanner.nextLine();
        while(this.getCommand())
        {
        }
    }
    private boolean getCommand()
    {
        msg("*** NYNI JE NA RADE HRAC " + players.get(activePlayer).getName() + " ***");
        if(this.noPlayersLeft())
        {
            msg("Konec hry");
            this.calculateWinner();
            return false;
        }
        if(players.get(activePlayer).getAccount().plays())
        {
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
                gameAccount.doubleBet();
                this.nextPlayer();
            }
            else if(split[0].equalsIgnoreCase("split") || split[0].equalsIgnoreCase("sp"))
            {
                gameAccount.split();
                this.nextPlayer();
            }
            else if(split[0].equalsIgnoreCase("status"))
            {
                this.status();
            }
            else if(split[0].equalsIgnoreCase("q") || split[0].equalsIgnoreCase("quit"))
            {
                return false;
            }
            else if(split[0].equalsIgnoreCase("help"))
            {
                help();
            }
            else
            {
                help();
            }
        }
        return true;
    }
    private void nextPlayer()
    {
        activePlayer =(activePlayer + 1) % players.size();
    }
    public static Player getActivePlayer()
    {
        return players.get(activePlayer);
    }
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
    public static Packet getPacket()
    {
        return packet;
    }
    public void showAll()
    {
        msg(dealer.toString());
        for(Player player : players)
        {
            msg("Hrac " + player.toString());
        }
    }
    private void createPlayer(String name)
    {
        players.add(new Player(name));
    }
    private void help()
    {
        msg("BlackJack napoveda:");
        msg("Pro zapoceti nove hry: \"game\"");
        msg("Pro dalsi kartu: \"hit\"");
        msg("Pro cekani: \"hold\"");
        msg("Pro stav hracskych uctu: \"status\"");
        msg("Pro zobrazeni teto napovedy: \"help\"");
        msg("Nize uvedene prikazy lze pouzit pouze v prvnim kole!");
        msg("Pro zdvojnasobeni sazky: \"double\"");
        msg("Pro rozdeleni stejnych karet: \"split\"");
    }
    public void status()
    {
        String ret = "Stav hracu:";
        for(Player player : players)
        {
           ret += "\nHrac \"" + player.getName() + "\", aktivni hra: " +player.getAccount().plays() + ", konto: "+player.getBank().getCash(); 
        }
        msg(ret);
    }
    private void initGame()
    {
        msg("Pripravuji hru...");
        packet = new Packet();
        dealer = new Dealer();
        for(Player player : players)
        {
            player.getAccount().addCard(packet.takeCard());
            player.getAccount().addCard(packet.takeCard());
        }
        dealer.addCard(packet.takeCard());
    }
    //zpravy vsem
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
    private void calculateWinner()
    {
        msg("Vyhodnoceni: ");
    }
//    public boolean onCommand(String command)
//    {
//        String[] split=command.split(" ");
//        
//        if(split[0].equalsIgnoreCase("hit") || split[0].equalsIgnoreCase("h"))
//        {
//            players.get(activePlayer).getAccount().hit(this.packet);
//        }
//        else if(split[0].equalsIgnoreCase("hold"))
//        {
//            players.get(activePlayer).getAccount().hold();
//        }
//        else if(split[0].equalsIgnoreCase("status"))
//        {
//            status();
//        }
//        else if(split[0].equalsIgnoreCase("help"))
//        {
//            help();
//        }
//        else if(split[0].equalsIgnoreCase("q") || split[0].equalsIgnoreCase("quit"))
//        {
//            return false;
//        }
//        else
//        {
//            help();
//        }
//        return true;
//    }        
//    private void newGame()
//    {
//        if(activeGame)
//        {
//            msg("Jedna hra uz se hraje");
//        }
//        activeGame = true;
//        packet = new Packet();
//        dealer = new Dealer();
//        //michani karet
//        //dealer.shuffle();
//        //TODO
//        for(int i = 0; i < players.size(); i++)
//        {
//            msg(players.get(i).getName() + " zadej sazku");
//            int bet;
//            int cash;
//            try
//            {
//                bet = Integer.valueOf(scanner.next());
//            }
//            catch(Exception ex)
//            {
//                msg("Neplatna hodnota, automaticky sazim minimum");
//                bet = minBet;
//            }
//            cash = players.get(i).getBank().getCash();
//            if(bet <= cash)
//            {
//                players.get(i).getBank().removeCash(bet);
//                players.get(i).getAccount().addBet(bet);
//            }
//            else
//            {
//                msg("Nemas dostatek kreditu! Sazim vse!");
//                players.get(i).getBank().removeCash(cash);
//                players.get(i).getAccount().addBet(cash);
//            }
//        }
//        for(int i = 0; i < 2*players.size(); i++)
//        {
//            Card card = packet.takeCard();
//            players.get(i%players.size()).getAccount().addCard(card);
//        }
//        dealer.addCard(packet.takeCard());
//        showAll();
//        activePlayer = 0;
//        msg("Na rade je hrac " + players.get(activePlayer).getName());
//        msg(players.get(activePlayer).getAccount().toString());
//        msg("prikazy: \"hit\" \"hold\" \"double\"");
//        if(players.get(activePlayer).getAccount().canSplit())
//        {
//            msg("Muzes sve karty rozdelit: \"split\"");
//        }
//    }
//    private void hit(Player player)
//    {
//        nextCard(player);
//    }
//    private void nextCard(Player player)
//    {
//        Card card = this.packet.takeCard();
//        GameAccount account = player.getAccount();
//        account.addCard(card);
//    }
//    private static Player getPlayer(String name)
//    {
//        Player foundPlayer = null;
//        for(int i = 0; i < players.size(); i++)
//        {
//            if(players.get(i).getName().equalsIgnoreCase(name))
//            {
//                foundPlayer = players.get(i);
//            }
//        }
//        return foundPlayer;
//    }

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
