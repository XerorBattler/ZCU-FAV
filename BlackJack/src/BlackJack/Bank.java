package blackjack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents bank system
 * 
 * @author Vaclav Haramule https://github.com/XerorBattler
 * @version 1.1
 */
public class Bank {
    
    private static Map<String, BankAccount> bankAccounts = new HashMap<String, BankAccount>();
    
    //default cash
    private static int defaultCash;
    
    /**
     * Constructor which defines default value
     */
    public Bank()
    {
         defaultCash = 1000;
    }
    /**
     * This method return bank account by name
     * 
     * @param name player name
     * @return bank account
     */
    public static BankAccount getAccount(String name)
    {
        return bankAccounts.get(name);
    }
    /**
     * This method returns money of all accounts
     * 
     * @return total cash
     */
    public static int getTotalCash()
    {
        int sum = 0;
        if(bankAccounts == null)return sum;
        Collection<BankAccount> list = bankAccounts.values();
        for(BankAccount account : list)
        {
            if(account.getCash()>0)
            {
                sum+=account.getCash();
            }
        }
        return sum;
    }
    /**
     * Getter for default cash
     * 
     * @return default cash
     */
    public static int getDefaultCash()
    {
        return defaultCash;
    }
}
