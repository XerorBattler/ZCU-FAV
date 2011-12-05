package pro4;

/**
 *
 * @author VaclavHaramule https://github.com/XerorBattler
 */
public class Generator {
    public static int[] generate(int itemNumber)
    {
        int[] data = new int[itemNumber];
        for(int index = 0; index < itemNumber; index++)
        {
            data[index] = (int)(Math.random() * 100 + 1.0) - (int)(Math.random() * 100 + 1.0);
        }
        return data;
    }
}
