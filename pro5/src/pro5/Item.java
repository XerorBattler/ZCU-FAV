package pro5;

/**
 *
 * @author VaclavHaramule https://github.com/XerorBattler
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
