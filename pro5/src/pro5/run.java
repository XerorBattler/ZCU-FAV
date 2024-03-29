package pro5;

import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author VaclavHaramule https://github.com/XerorBattler
 */
public class run {
    private static FileReader fileReader = null;
    private static Scanner scanner = null;
    private static Item[] items = null;
    private static int method = 0;
    private static final int DEFAULTMETHOD = 0;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        print("Metoda reseni? Pro dynamicky pristup zadejte \"1\", pro greedy pristup \"2\", pro porovnani pristupu \"0\"");
        int number;
        int backPackLimit = 0;
        try
        {
            number = Integer.parseInt(scanner.next());
        }
        catch (Exception ex)
        {
            number = DEFAULTMETHOD;
            print("Naplatny vstup! Zobrazuji porovnani!");
            
        }
        method = number;
        if(number == 1)
        {
            print("Resim pomoci dynamickeho pristupu.");
        }
        else if(number == 2)
        {
            print("Resim pomoci greedy pristupu.");
        }
        else
        {
            print("Resim pomoci obou pristupu.");
        }
        print("Zadejte jmeno souboru z daty, nebo napiste \"ne\"");
        String fileName = scanner.next();
        try
        {
            fileReader = new FileReader(fileName);
            Scanner fileScanner = new Scanner(fileReader);
            int numberOfItems = Integer.parseInt(fileScanner.nextLine());
            backPackLimit = Integer.parseInt(fileScanner.nextLine());
            items = new Item[numberOfItems];
            String line;
            String[] lineCells = new String[3];
            for(int index = 0; index < numberOfItems; index++)
            {
                line = fileScanner.nextLine();
                lineCells = line.split(";",3);
                items[index] = new Item(Integer.parseInt(lineCells[0]),Integer.parseInt(lineCells[1]));
            }
        }
        catch(Exception ex)
        {
            items = null;
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
        }
        if(items != null && items.length > 0)
        {
            if(backPackLimit == 0)
            {
                print("Zadejte velikost batohu");
                while(backPackLimit <= 0)
                {
                    try
                    {
                        backPackLimit = scanner.nextInt();
                    }
                    catch(Exception exc)
                    {
                        backPackLimit = 0;
                        print("Chybny vstup! Zadejte prosim nezapornou nenulovou hodnotu!");
                    }
                }
            }
            Solver solver = new Solver(items, backPackLimit);
            if(method == 1)
            {
                solver.solveDynamic();
            }
            else if(method == 2)
            {
                solver.solveGreedy();
            }
            else
            {
                solver.solveDynamic();
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
    static void print(String text, boolean type)
    {
        if(type)System.out.print(text);
        else print(text);
    }
}

        
