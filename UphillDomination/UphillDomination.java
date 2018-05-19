
import java.io.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Graphics;
import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class GameFrame extends JFrame
{
   Board p=new Board();
   Thread t;
   JLabel l;
   final GameFrame f=this;
    public GameFrame()
    {
        super("Uphill Domination_v1.2.0");
        JMenuBar mb=new JMenuBar();
        JButton  nb=new JButton("NEW GAME");
        
        nb.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {t=new Thread(new MyGame(p,f));
                t.start();
                }
                catch(Exception ee)
                {
                    l.setText("  ERROR  ");
                }
            }
        });
        JMenu pm= new JMenu(" PLAYERS ");
        JButton  db=new JButton("       DICE       ");
       db.addActionListener(new ActionListener()
       {
           public void actionPerformed(ActionEvent e){
               try{t.resume();
                }
                catch(Exception ee){l.setText(" ERROR ");}
            }
        });
       JButton  eb=new JButton("EXIT");
       eb.addActionListener(new ActionListener()
       {
           public void actionPerformed(ActionEvent e){
               System.exit(0);
            }
        });
        JCheckBoxMenuItem cbox1=new JCheckBoxMenuItem("Player 1");
        JCheckBoxMenuItem cbox2=new JCheckBoxMenuItem("Player 2");
        JCheckBoxMenuItem cbox3=new JCheckBoxMenuItem("Player 3");
        JCheckBoxMenuItem cbox4=new JCheckBoxMenuItem("Player 4");
        pm.add(cbox1);
        pm.add(cbox2);
        pm.add(cbox3);
        pm.add(cbox4);
        mb.add(nb);
        mb.add(pm);
        mb.add(db);
        mb.add(eb);
       l=new JLabel("       PRESS NEW GAME To START GAME!!");
        super.add("North",mb);
       super.add("Center",p);
        super.add("South",l);
        super.setSize(800,800);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
        
    }
    void send(String s)
    {
        l.setText(s);
        return;
    }
}
class Board extends JPanel 
{
    int WW=getWidth(),HH=getHeight();
    int W=WW*95/100;
    int H=HH;
    int dn=0;
    int px[]=new int [2];
    int py[]=new int [2];
    int v=H/10,h=W/10;
    String caves[][]={{"4","7","12","16","20","25","43","44","47","55","59","81","82","83","93","95"},{"12","20","4","43","7","4","4","7","12","7","43","12","20","47","44","25"},{"25","44","47","83","82","44","16","25","59","16","47","55","95","16","82","59"},{"43","55","81","55","82","93","59","95","83","81","93","95","20","93","81","83"}};
    public Board()
    {
    for(int i=0;i<2;i++)//initialising position of players.
    {
        px[i]=50;
        py[i]=780;
    }
    }
    public void paintComponent(Graphics g)
    {
         WW=getWidth();HH=getHeight();
         W=WW*95/100;
         H=HH;
         v=H/10;h=W/10;
        super.paintComponent(g);
        int c=0;
        for(int i=0;i<H;i+=v)  //Coloring the levels
        {
            if(c++%2==0)
            g.setColor(new Color(150,100,100));
            else
            g.setColor(new Color(50,200,50));
            g.fillRect(0,i,W,v);
        }
        g.setColor(Color.WHITE);
        int hh=h/2,n=100,j;
        c=0;
       for(int i=v/2;i<H;i+=v) //Draws the numbers on board
        {
            c++;
            for(j=hh;j>=0&&j<=W;)
            {
                for(int k=0;k<16;k++)//Draws Caves
                {
                    if(Integer.parseInt((caves[0][k]))==n)
                    {   g.setColor(Color.BLACK);
                        g.fillRect(j-h/2,i-v/2,h,v);
                        g.setColor(Color.WHITE);
                    }
                }
                g.drawString(String.valueOf(n--),j,i);
                if(c%2==0)
                j=j-h;
                else
                j=j+h;
            }
            if(c%2==0)
                hh=h/2;
                else
                hh=W-h/2-W%10;
         }
         g.setColor(Color.WHITE);
        for(int i=0;i<H;i+=v)     //drawing the horizontal lines
        g.drawLine(0,i,W,i);
       for(j=0;j<W-h;j+=h)    //drawing the vertical lines
        g.drawLine(j,0,j,H);

        g.setColor(Color.BLACK);
        g.fillRect(W,0,WW-W,H);
        // Linear DICE
        int Hd=H/6;
        g.setColor(Color.RED);
        for(int i=0;i<dn;i++)
        {
            g.fillArc(W+(WW-W)/4,Hd*i+Hd/4,(WW-W)/2,(WW-W)/2,0,360);
        }
        g.setColor(Color.WHITE);
        for(int i=dn;i<6;i++)
        {
            g.fillArc(W+(WW-W)/4,Hd*i+Hd/4,(WW-W)/2,(WW-W)/2,0,360);
        }
        //Coins
        g.setColor(Color.RED);
        g.fillArc(px[0],py[0],Math.max(h/2,v/2),Math.max(h/2,v/2),0,360);
        g.setColor(Color.BLUE);
        g.fillArc(px[1],py[1],Math.max(h/2,v/2),Math.max(h/2,v/2),0,360);
        
    }
    public void receive(int pi,int pxx,int pyy)
    {
        px[pi]=pxx*h+h/2-20;
        py[pi]=pyy*v+v/2-30;
        repaint();
    }
    public void setDiceNo(int Dn)
    {
        dn=Dn;
        repaint();
    }
}

class MyGame implements Runnable
{
    String pno[]={"90","90"}; //Players' Position
    String board[][]=new String[10][10]; //helpful in calculation of move
    Board b;
    GameFrame f;
     String caves[][]={{"4","7","12","16","20","25","43","44","47","55","59","81","82","83","93","95"},{"12","20","4","43","7","4","4","7","12","7","43","12","20","47","44","25"},{"25","44","47","83","82","44","16","25","59","16","47","55","95","16","82","59"},{"43","55","81","55","82","93","59","95","83","81","93","95","20","93","81","83"}};
    public MyGame(Board bb,GameFrame ff)
    {
        f=ff;
        b=bb;
        int j,k=0,c=0,n=100;
        for(int i=0;i<10;i++)
        {
            c++; //Counter for left and right
            for(j=k;j>=0&&j<10;) //Stores the numbers present on the board
            {
                board[i][j]=String.valueOf(n--);
                if(c%2==0)
                j--;
                else
                j++;
            }
            if(c%2==0)
            k=0;
            else
            k=9;
        }
        
    }
    public void run()
    {
       int pp,i=0;
        hhere: while(true) //Game Loop
        {
            for(i=0;i<2;i++) //Player i's Loop
            {
                f.send("       Player "+(i+1)+"'s Turn!!!! Throw DICE--->");
                int mov=0,j;
                while(true) //While dice==6
                {
                    try{Thread.currentThread().suspend();}
                    catch(Exception ee){}
                    int val=new Dice().value(6);
                    b.setDiceNo(val);
                    if(val==6)
                    {
                        f.send("       Player "+(i+1)+" Threw 6...  Re-throw DICE--->");
                        if(mov==18)
                        mov=0;
                        else
                        {   mov+=val;
                            continue;
                        }
                    }
                    else
                    {  mov+=val;
                        break;
                    }
                }
            f.send("       Player "+(i+1)+" threw "+mov);
            //move coin
            for(j=1;j<=mov;j++)
            {
                try{Thread.sleep(200);}
                catch(Exception e){}
                pp=Integer.parseInt(board[pno[i].charAt(0)-'0'][pno[i].charAt(1)-'0']);
                pp+=1;
                int k=0,l=0;
                here:for(;k<10;k++)
                for(l=0;l<10;l++)
                if(board[k][l].equals(String.valueOf(pp)))
                break here;
                pno[i]=""+k+""+l;
                b.receive(i,pno[i].charAt(1)-'0',pno[i].charAt(0)-'0');
            }
            for(int k=0;k<16;k++)
            {
                if(board[pno[i].charAt(0)-'0'][pno[i].charAt(1)-'0'].equals(caves[0][k]))
                {
                    f.send("       YOU ENTERED A CAVE");
                    try{Thread.sleep(1500);}
                catch(Exception e){}
                    int val=new Dice().value(3);
                    for(int u=0;u<10;u++)
                    for(int uu=0;uu<10;uu++)
                    {
                        if(board[u][uu].equals(caves[val][k]))
                        pno[i]=""+u+""+uu;
                    }
                    f.send("       YOU REACHED "+board[pno[i].charAt(0)-'0'][pno[i].charAt(1)-'0']);
                    try{Thread.sleep(1500);}
                catch(Exception e){}
                    b.receive(i,pno[i].charAt(1)-'0',pno[i].charAt(0)-'0');
                    break;
                }
            }
                            if((pno[0].equals("00")||pno[1].equals("00")))
                break hhere;
        }
        }
        JOptionPane.showMessageDialog(f,"Player "+(i+1)+" Wins");
    }
}
class Dice
{
    int value(int v)
    {
        double nn=Math.random();
        nn=nn*Math.pow(10,8);
       int n=(int)nn;
       n=n%v+1;
       return n;
    }
}
public class UphillDomination
{
    public static void main(String args[])throws IOException
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
            GameFrame g=new GameFrame();
        }
        });
        
    }
}