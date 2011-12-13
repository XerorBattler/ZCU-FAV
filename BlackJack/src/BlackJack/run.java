package blackjack;

import java.util.Scanner;

/**
 *
 * @author Xeror
 */
public class run {
    public static void main(String[] args)
    {
        System.out.println("Vitejte ve hre BlackJack");
        System.out.print("Zadejte pocet hracu: ");
        Scanner scanner = new Scanner(System.in);
        int playerCount = 0;
        try
        {
            playerCount = Integer.valueOf(scanner.next());
            if(playerCount <= 0)
            {
                throw new Exception("Invalid value!");
            }
        }
        catch(Exception e)
        {
            System.out.println("Vstupem muze byt jen kladne cele cislo! Zacinam hru pro jednoho hrace.");
        }
        new BlackJack(playerCount);
        System.out.println("Diky za hru ;)");
    }
}
