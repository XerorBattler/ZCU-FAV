package pro5;

/**
 *
 * @author VaclavHaramule https://github.com/XerorBattler
 */
public class Generator {
    public static Item[] generate(int itemNumber)
    {
        Item[] items = new Item[itemNumber];
        int weight;
        int price;
        for(int index = 0; index < itemNumber; index++)
        {
            weight = (int)(Math.random()*1000);
            price = (int)(Math.random()*1000);
            if(weight == 0)weight = 1;
            if(price == 0)price = 1;
            items[index] = new Item(price, weight);
            //run.print(items[index].getPrice() + " - " + items[index].getWeight());
        }
        return items;
    }
}
