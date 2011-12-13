package blackjack;

/**
 * This class represents player Bank account from bank
 * 
 * @author Vaclav Haramule https://github.com/XerorBattler
 * @version 1.1
 */
public class BankAccount {
    private int currentCash;
    /**
     * Constructor which creates account with default cash
     */
    public BankAccount()
    {
        this.currentCash = BlackJack.getBankDefaultCash();
    }
    /**
     * Remove some cash from account
     * 
     * @param value how much to remove
     * @return true if player have enough money
     */
    public boolean removeCash(int value)
    {
        if(this.currentCash >= value && value > 0)
        {
            this.currentCash -= value;
            return true;
        }
        return false;
    }
    /**
     * Add some cash to account
     * 
     * @param value how much to add
     * @return true if are cash sucessfuly added
     */
    public boolean addCash(int value)
    {
        if(value >= 0)
        {
            this.currentCash += value;
            return true;
        }
        return false;
    }
    /**
     * Show how much cash account have
     * 
     * @return cash
     */
    public int getCash()
    {
        return this.currentCash;
    }
    /**
     * Value setter for account
     * 
     * @param value new value
     * @return true if value is more then zero
     */
    public boolean setCash(int value)
    {
        if(value > 0)
        {
            this.currentCash = value;
            return true;
        }
        return false;
    }
}
