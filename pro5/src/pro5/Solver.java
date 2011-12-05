package pro5;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.Arrays;

/**
 *
 * @author Xeror
 */
public class Solver {
    private int[] valueData = null;
    private int[] weightData = null;
    private int backpackSize;
    public Solver(Item[] data, int backpackSize)
    {
        this.backpackSize = backpackSize;
        prepareData(data);
        //Arrays.sort(valueData);
        run.print("V obchode jsou: (PORADI-CENA-VAHA)");
        for(int index = 0; index < valueData.length; index++)
        {
            System.out.print(index + "-" + valueData[index] + "-"+weightData[index] + " ");
        }
       
    }
    void solveGreedy()
    {
        double[] combinedData = new double[valueData.length];
        for(int index = 0; index < valueData.length; index++)
        {
            combinedData[index] = (double)valueData[index] / (double)weightData[index];
        }
        int max;
        int weightSum=0;
        int valueSum=0;
        String ret="";
        //run.print("Prvky:");
        while(true)
        {
            max = 0;
            for(int index = 0; index < valueData.length; index++)
            {
                if(combinedData[index] > combinedData[max] && weightData[index] <= backpackSize)
                {
                    //System.out.print(combinedData[index]+"-"+weightData[index]+"; ");
                    max = index;
                }
            
            }
            if(max <= 0)break;
            //System.out.print("[" + valueData[max] + ";" + weightData[max] + "]");
            valueSum+=valueData[max];
            weightSum+=weightData[max];
            backpackSize-=weightData[max];
            ret+=""+max+", ";
            combinedData[max] = -1;
        }
        run.print("\nVybrany byly prvky: "+ret+", celkova cena: "+valueSum+", celkova vaha: "+weightSum);
    }
    void solveDynamic()
    {
        throw new NotImplementedException();
    }
    private void prepareData(Item[] data)
    {
        valueData = new int[data.length];
        weightData = new int[data.length];
        for(int index = 0; index < data.length; index++)
        {
            valueData[index] = data[index].getPrice();
            weightData[index] = data[index].getWeight();
        }
        for(int index = 0; index < data.length; index++)
        {
            valueData[index] = data[index].getPrice();
            weightData[index] = data[index].getWeight();
        }
        
    }
}
