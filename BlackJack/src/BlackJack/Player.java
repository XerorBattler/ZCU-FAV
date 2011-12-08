/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

/**
 *
 * @author Xeror
 */
public class Player {
    private Packet packet;
    private String name;
    public String getName()
    {
        return this.name;
    }
    public void sendMessage(String text)
    {
        System.out.println(text);
    }
    public String getDisplayName()
    {
        return getName();
    }
}
