package pro4;

/**
 *
 * @author VaclavHaramule https://github.com/XerorBattler
 */
public class Solver {
    private int[] values;
    public Solver(int[] values)
    {
        this.values = values;
        run.print(java.util.Arrays.toString(values));
    }
    void solveCubic()
    {
        //nadefinuji si promenne pro nejlepsi soucet a pro zacatek a konec nejlepsiho reseni
        int bestSum = 0;
        int solutionStart = 0;
        int solutionEnd = 0;
        //prochazim postupne cele pole
        for(int index = 0; index < values.length; index++)
        {
            //vytvorim si promennou pro prubezny soucet
            int currentSum = 0;
            //prochazim vsechny polozky do indexu nadrazeneho for cyklu
            for(int subIndex = index; subIndex < values.length; subIndex++)
            {
                //scitam prubezne polozky
                currentSum += values[subIndex];
                //pokud je soucet lepsi nez dosavadni nejlepsi prepisi ho a ulozim pocatecni a konecny index
                if(currentSum > bestSum)
                {
                    bestSum = currentSum;
                    solutionStart = index;
                    solutionEnd   = subIndex;
                }
            }
        }
        run.print("Nejlepsi reseni v O(n^2) je suma " + bestSum + ", zacina na indexu " + solutionStart + " a konci na " + solutionEnd);
    }
    void solveLinear()
    {
        //nadefinuji promenne
        int bestSum = 0;
        int currentSum = 0;
        int solutionStart = 0;
        int solutionEnd = 0;
        
        for(int index = 0, subIndex = 0; subIndex < values.length; subIndex++)
        {
            //prochazim cele sub-pole a scitam polozky
            currentSum += values[subIndex];
            //pokud zjistim ze prubezny vysledek je lepsi nez soucasny nejlepsi, tak ho ulozim
            if(currentSum > bestSum)
            {
                bestSum = currentSum;
                solutionStart = index;
                solutionEnd = subIndex;
            }
            //v pripade ze je reseni horsi zvysuji hlavni index
            else if(currentSum < 0)
            {
                index = subIndex + 1;
                currentSum = 0;
            }
        }
        run.print("Nejlepsi reseni v O(n) je suma " + bestSum + ", zacina na indexu " + solutionStart + " a konci na " + solutionEnd);
    }
}
