package pro5;

/**
 *
 * @author VaclavHaramule https://github.com/XerorBattler
 */
public class Solver {
    private Item[] items = null;
    private int backpackSize;
    public Solver(Item[] data, int backpackSize)
    {
        this.items = data;
        this.backpackSize = backpackSize;
        run.print("V obchode jsou: (PORADI-CENA-VAHA)");
        for(int index = 0; index < items.length; index++)
        {
            run.print(index + "-" + items[index].getPrice() + "-" + items[index].getWeight() + " ", true);
        }
        run.print("");
       
    }
    void solveGreedy()
    {
        //toto pole slouzi k ulozeni pomeru mezi cenou a vahou
        //greedy pak vyuziva nejlepsi pomer
        double[] combinedData = new double[items.length];
        for(int index = 0; index < items.length; index++)
        {
            //radeji pretypuji data kvuli presnosti
            combinedData[index] = (double)items[index].getPrice() / (double)items[index].getWeight();
        }
        //nastaveni vychozich hodnot
        int max;
        int weightSum = 0;
        int valueSum = 0;
        String ret = "";
        while(true)
        {
            //pro kazdy cyklus porovnavam s vychozim 0. prvkem
            max = 0;
            for(int index = 0; index < items.length; index++)
            {
                //hledam zda soucasny prvek je lepsi nez zatim nejlepsi nalezeny
                if(combinedData[index] > combinedData[max] && items[index].getWeight() <= backpackSize)
                {
                    max = index;
                }
            }
            //jelikoz pouzivam jako vychozi nulty prvek tak musim po konci cyklu zkontrolovat zda se vejde do kapacity
            //a nebo jestli uz neni vybran
            if(max == 0 && items[max].getWeight() > backpackSize)break;
            if(max == 0 && combinedData[max] < 0)break;
            //pricitam k sumam
            valueSum += items[max].getPrice();
            weightSum += items[max].getWeight();
            //odecitam od velikosti batohu
            backpackSize -= items[max].getWeight();
            //pripravuji si vystupni retezec
            ret += "" + max + ", ";
            //odstranuji z pole jiz pouzity prvek, jelikoz pomery jsou vzdy lepsi nez 0
            combinedData[max] = -1;
        }
        run.print("Greedy reseni vybralo predmety: " + ret + "celkova cena: " + valueSum + ", celkova vaha: " + weightSum);
    }
    void solveDynamic()
    {
        //pripravim si dve pole o delce vstupnich dat + 1 (kvuli "logictejsimu" pristupu
        //kde budu rotovat od 1 do n
        int[] value = new int[items.length+1];
        int[] weight = new int[items.length+1];

        //zpracuju vstupni data do vytvorenych poli
        for (int n = 1; n <= items.length; n++) {
            value[n] = items[n-1].getPrice();
            weight[n] = items[n-1].getWeight();
        }
        //vytvorim se dve dvourozmerne pole (matice), tyto matice mi budou slozit jako reseni a jako optimalizace
        //optimalizacni matice mi urci nejlepsi zisk, ktery se vejde do vahoveho limitu
        //druha matice nam ukaze zda je prvni reseni lepsi nebo horsi nez druhe
        int[][] optimalization = new int[items.length+1][backpackSize+1];
        boolean[][] solution = new boolean[items.length+1][backpackSize+1];

        for (int n = 1; n <= items.length; n++) {
            for (int w = 1; w <= backpackSize; w++) {
                int firstOption = optimalization[n-1][w];
                int secondOption = Integer.MIN_VALUE;
                //overuji zda se predmet vejde do batohu
                if (weight[n] <= w)
                {
                    secondOption = value[n] + optimalization[n-1][w-weight[n]];
                }
                //ulozim zda je prvni reseni lepsi nez druhe
                solution[n][w] = (secondOption > firstOption);
                //vyberu lepsi reseni
                optimalization[n][w] = Math.max(firstOption, secondOption);
            }
        }
        //zjistim ktere predmety si mam vybrat
        boolean[] finalSolution = new boolean[items.length+1];
        for (int n = items.length, w = backpackSize; n > 0; n--)
        {
            if (solution[n][w])
            {
                finalSolution[n] = true;
                //odecitam od celkove velikosti batohu
                w -= weight[n];
            }
            else
            {
                finalSolution[n] = false;
            }
        }

        //zpracuju data pro vystup
        String ret="";
        int weightSum = 0;
        int valueSum = 0;
        for (int n = 1; n <= items.length; n++)
        {
            if(finalSolution[n])
            {
                ret+="" + n + ", ";
                weightSum+=weight[n];
                valueSum+=value[n];
            }
            
        }
        //vypisu vysledek
        run.print("Dynamicke reseni vybralo predmety: " + ret + "celkova cena: " + valueSum + ", celkova vaha: " + weightSum);
    }
}
