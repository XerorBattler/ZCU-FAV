package pro4;

import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author VaclavHaramule https://github.com/XerorBattler
 */
public class run {
    private static FileReader fileReader = null;
    private static Scanner scanner = null;
    private static int[] data = null;
    private static int method = 0;
    private static final int DEFAULTMETHOD = 0;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        print("Metoda reseni? Reseni v linearnim case \"1\", ve kvadratickem case \"2\", vsechna reseni \"0\"");
        int number;
        try
        {
            number = Integer.parseInt(scanner.next());
        }
        catch (Exception ex)
        {
            number = DEFAULTMETHOD;
            print("Naplatny vstup! Vybiram defaultni nastaveni.");
            
        }
        method = number;
        if(number == 1)
        {
            print("Resim v linearnim case.");
        }
        else if(number == 2)
        {
            print("Resim v kvadratickem case.");
        }
        else
        {
            print("Resim pomoci vsech pristupu.");
        }
        print("Zadejte jmeno souboru z daty, nebo napiste \"ne\"");
        String fileName = scanner.next();
        try
        {
            fileReader = new FileReader(fileName);
            Scanner fileScanner = new Scanner(fileReader);
            int numberOfItems = Integer.parseInt(fileScanner.nextLine());
            data = new int[numberOfItems];
            String[] lineCells = fileScanner.nextLine().split(";");
            for(int index = 0; index < numberOfItems; index++)
            {
                data[index] = Integer.parseInt(lineCells[index]);
            }
        }
        catch(Exception ex)
        {
            data = null;
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
                data = Generator.generate(number);
                print("Vygenerovano " + data.length + " polozek...");
            }
        }
        if(data != null && data.length > 0)
        {
            Solver solver = new Solver(data);
            if(method == 1)
            {
                solver.solveLinear();
            }
            else if(method == 2)
            {
                solver.solveCubic();
            }
            else
            {
                solver.solveLinear();
                solver.solveCubic();
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

        
