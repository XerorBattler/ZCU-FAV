package blackjack;

/**
 *
 * @author Xeror
 */
public class Player {
    private String name;
    private GameAccount account;
    private BankAccount bank;
    
    public Player(String name)
    {
        this.name = name;
        this.account = new GameAccount();
        this.bank = new BankAccount();
    }
    public String getName()
    {
        return this.name;
    }
    public GameAccount getAccount()
    {
        if(account == null)this.account = new GameAccount();
        return this.account;
    }
    public BankAccount getBank()
    {
        if(bank == null)this.bank = Bank.getAccount(name);
        return this.bank;
    }
    public void sendMessage(String text)
    {
        BlackJack.msg(text);
    }
    @Override
    public String toString() {
        return this.name + this.account.toString();
    }
    
}
