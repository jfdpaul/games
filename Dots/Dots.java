import java.io.*;
import javax.swing.*;
import java.awt.*;
class Dots
{
    public static void main(String[]args)throws IOException
    {
        try
        {BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int n=10;
        while(n>6)
        {
         n= Integer.parseInt(JOptionPane.showInputDialog(new JFrame(),"Enter number of players"));
        if(n>6)
        JOptionPane.showMessageDialog(new JFrame(),"Enter number of players < 7");
    }
    int nn=21;
    while(nn>20)
    nn=Integer.parseInt(JOptionPane.showInputDialog(new JFrame(),"Enter Dimension of matrix(<21):"));
        String player[]=new String [n];
        
        for(int i=0;i<n;i++)
        {
            player[i]=JOptionPane.showInputDialog(new JFrame(),"Enter Name of Player"+(i+1));
        }
        
        new Game(player,nn);
    }
    
    catch(Exception e){}
}
}