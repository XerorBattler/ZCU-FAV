package pro5;

import java.util.Arrays;

/**
 *
 * @author Xeror
 */
public class Item
{
    private int weight;
    private int price;
    public Item(int weight, int price)
    {
        this.weight = weight;
        this.price = price;
    }
    
    public int getWeight()
    {
        return this.weight;
    }
    public int getPrice()
    {
        return this.price;
    }
    @Override
    public String toString() {
        return "[vaha: " + this.weight + ", cena:" + this.price + "]";
    }
}
