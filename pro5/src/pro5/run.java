package pro5;

import java.util.Arrays;
import java.io.FileReader;
import java.util.Scanner;
import java.io.File;

/**
 *
 * @author Xeror
 */
public class run {
    private static FileReader fileReader = null;
    private static Scanner scanner = null;
    private static Item[] items = null;
    private static boolean method = false;
    private static final int DEFAULTMETHOD = 2;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        print("Metoda reseni? Pro dynamicky pristup zadejte 1, pro greedy pristup 2");
        int number;
        try
        {
            number = Integer.parseInt(scanner.next());
        }
        catch (Exception ex)
        {
            number = DEFAULTMETHOD;
            print("Naplatny vstup! Pouzivam defaultni hodnotu.");
            
        }
        if(number == 1)
        {
            print("Resim pomoci dynamickeho pristupu");
            method = true;
        }
        else
        {
            print("Resim pomoci greedy pristupu");
        }
        try
        {
            fileReader = new FileReader("data.txt");
            Scanner fileScanner = new Scanner(fileReader);
            fileScanner.nextLine();
        }
        catch(Exception ex)
        {
            print("Vstupni data nenalezena, chcete vygenerovat data nahodna? (ano/ne)");
            String choice = scanner.next();
            if(choice.equalsIgnoreCase("ano"))
            {
                print("Kolik dat mam vygenerovat?");
                number = 0;
                while(number <= 0)
                {
                    try
                    {
                        number = scanner.nextInt();
                    }
                    catch(Exception exc)
                    {
                        number = 0;
                        print("Chybny vstup! Zadejte prosim nezapornou nenulovou hodnotu!");
                    }
                }
                items = Generator.generate(number);
                print("Vygenerovano " + items.length + " polozek...");
            }
            print("Zadejte velikost batohu");
            number = 0;
            while(number <= 0)
            {
                try
                {
                    number = scanner.nextInt();
                }
                catch(Exception exc)
                {
                    number = 0;
                    print("Chybny vstup! Zadejte prosim nezapornou nenulovou hodnotu!");
                }
            }
        }
        if(items != null && items.length > 0)
        {
            Solver solver = new Solver(items, number);
            if(method)
            {
                solver.solveDynamic();
            }
            else
            {
                solver.solveGreedy();
            }
        }
        else
        {
            print("Nebyly definovany zadna data, program bude ukoncen");
        }
    }
    static void print(String text)
    {
        System.out.println(text);
    }
}

        
