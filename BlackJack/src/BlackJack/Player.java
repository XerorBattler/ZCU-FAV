package blackjack;

/**
 * This class represents player in game
 * 
 * @author Vaclav Haramule https://github.com/XerorBattler
 * @version 1.2
 */
public class Player {
    private String name;
    private GameAccount account;
    private BankAccount bank;
    
    /**
     * Constructor for new player
     * 
     * @param name name of player
     */
    public Player(String name)
    {
        this.name = name;
        this.account = new GameAccount();
        this.bank = new BankAccount();
    }
    /**
     * Player name getter
     * 
     * @return player name
     */
    public String getName()
    {
        return this.name;
    }
    /**
     * Game account getter
     * 
     * @return player game account
     */
    public GameAccount getAccount()
    {
        if(account == null)this.account = new GameAccount();
        return this.account;
    }
    /**
     * Bank account getter
     * 
     * @return player bank account
     */
    public BankAccount getBank()
    {
        if(bank == null)this.bank = Bank.getAccount(name);
        return this.bank;
    }
    /**
     * This method allows "private" message to player
     * 
     * @param text message
     */
    public void sendMessage(String text)
    {
        BlackJack.msg(text);
    }
}
