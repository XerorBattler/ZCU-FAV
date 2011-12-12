/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.Scanner;
/**
 *
 * @author Xeror
 */
public class run {
    private static Scanner scanner = new Scanner(System.in);
    private static BlackJack blackjack;
    private static int counter = 0;
    public static void main(String[] args)
    {
        log("Vitejte ve hre BlackJack");
        initGame();
        while(getCommand())
        {
            
        }
        log("Diky za hru ;)");
    }
    private static boolean getCommand()
    {
        String command;
        //log("Prikaz: ", true);
        command = scanner.nextLine();
        if(command == null)return true;
        return(blackjack.onCommand(command));
    }
    private static void initGame()
    {
        //players = new HashMap<Integer, Player>();
        blackjack = new BlackJack();
        log("Zadejte pocet hracu: ", true);
        int playerCount;
        try
        {
            playerCount = Integer.valueOf(scanner.next());
        }
        catch(Exception e)
        {
            log("Vstupem muze byt jen cislo!");
            playerCount = 1;
        }
        String name;
        
        for(int i = 0; i < playerCount; i++)
        {
            log("Zadejte jmeno hrace " +(i + 1) + ": ", true);
            name = scanner.next();
            blackjack.createPlayer(name);
            name = "";
        }
        scanner.nextLine();
        log("Napoveda: \"help\"");
    }
    public static void log(int numberInText)
    {
        log(numberInText, false);
    }
    public static void log(int numberInText, boolean noNewLine)
    {
        log(String.valueOf(numberInText), noNewLine);
    }
    public static void log(String text)
    {
        log(text, false);
    }
    public static void log(String text, boolean noNewLine)
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
