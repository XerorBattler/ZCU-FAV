/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Xeror
 */
public class Bank {
    
    private static Map<String, BankAccount> bankAccounts = new HashMap<String, BankAccount>();
    
    public static BankAccount getAccount(String name)
    {
        return bankAccounts.get(name);
    }
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
}
