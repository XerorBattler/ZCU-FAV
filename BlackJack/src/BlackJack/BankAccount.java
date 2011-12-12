/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

/**
 *
 * @author Xeror
 */
public class BankAccount {
    private int currentCash;
    public BankAccount(int value)
    {
        this.currentCash = value;
    }
    public boolean removeCash(int value)
    {
        if(this.currentCash >= value)
        {
            this.currentCash -= value;
            return true;
        }
        return false;
    }
    public boolean addCash(int value)
    {
        if(value >= 0)
        {
            this.currentCash += value;
            return true;
        }
        return false;
    }
    public int getCash()
    {
        return this.currentCash;
    }
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
