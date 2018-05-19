import java.io.*;
import javax.swing.*;
import java.awt.*;
class Generate
{
    public static void main(String[]args)throws IOException
    {
        int n,l;
        char ch[];
        String name=JOptionPane.showInputDialog("Enter name of file for output:");
        FileWriter fw=new FileWriter(name);
        BufferedWriter bw=new BufferedWriter(fw);
        
        l=Integer.parseInt(JOptionPane.showInputDialog("Enter length of string"));
        String ss=JOptionPane.showInputDialog("Enter the different characters in set\nin a comma separated list\nfor eg: a,b,c or 1,2,3 ");
        ch=new char[(ss.length()+1)/2]; 
        n=0;
        for(int i=0;i<ss.length();i++)
        {
            if(ss.charAt(i)!=',')
            ch[n++]=ss.charAt(i);
        }
        int s[]=new int[l];
        boolean tr=true;
    while(tr)
    {
        
        s[0]=s[0]+1;
        for(int i=0;i<l;i++)
        {
          
            if(s[i]>n-1)
            {
                if(s[l-1]==n)
                    tr=false;
                s[i]=0;
                if(i<l-1)
                s[i+1]=s[i+1]+1;
            }
            
            bw.write(""+ch[s[i]]);
        }
        bw.write(""+"\n");
        
    }
    bw.close();
    }
}