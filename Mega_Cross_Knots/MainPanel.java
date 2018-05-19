import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class MainPanel extends JPanel implements MouseListener
{
    int big[][],small[][];
    int W=Toolkit.getDefaultToolkit().getScreenSize().width;
    int H=Toolkit.getDefaultToolkit().getScreenSize().height;
    int L=Math.min(W,H)-30;
    int l=L/3;
    int turn=0;
    int ai=-1;
    int aj=-1;
    
    public MainPanel()
    {
        super();
        big=new int [9][9];
        small=new int[3][3];
        for(int i=0;i<9;i++)
        for(int j=0;j<9;j++)
        big[i][j]=-1;
        for(int i=0;i<3;i++)
        for(int j=0;j<3;j++)
        small[i][j]=-1;
        
        JFrame f=new JFrame("MEGA-CROSS&KNOTS");
        f.add(this);
        f.setSize(W,H);
        f.setVisible(true);
        
    }
    public void mouseEntered(MouseEvent me){}
    public void mouseExited(MouseEvent me){}
    public void mousePressed(MouseEvent me){}
    public void mouseReleased(MouseEvent me){}
    public void mouseClicked(MouseEvent me)
    {
        int x=me.getX();
        int y=me.getY();
        getLocation(x,y);
            if(checkValidity()==true)
            {
                big[ai][aj]=turn++%2;
                check();
                ai=-1;
                aj=-1;
                repaint();
            }
           checkGame();
    }
    private void getLocation(int x,int y)
    {
    }
    private boolean checkValidity()
    {
        return true;
    }
    private void check()//function to mark a subgrid
    {
        boolean otr,ztr;
        otr=ztr=true;
        if(small[(int)Math.floor(ai/3)][(int)Math.floor(aj/3)]==-1)
        {
            for(int i=ai-ai%3;i<ai-ai%3+3;i++)
            {
                otr=ztr=true;
                for(int j=aj-aj%3;j<aj-aj%3+3;j++)
                {
                    if(small[i][j]!=1)
                        otr=false;
                    if(small[i][j]!=0)
                        ztr=false;    
                }
            }
            for(int j=aj-aj%3;j<aj-aj%3+3;j++)
            {
                otr=ztr=true;
                for(int i=ai-ai%3;i<ai-ai%3+3;i++)
                {
                    if(small[i][j]!=1)
                        otr=false;
                    if(small[i][j]!=0)
                        ztr=false;    
                }
            }
            if(otr)
            small[(int)ai/3][(int)aj/3]=1;
            if(ztr)
            small[(int)ai/3][(int)aj/3]=0;
            checkGame();
        }
    }
    private void checkGame()//function to end game
    {
        boolean ztr,otr;
        for(int i=0;i<3;i++)
        {
            ztr=otr=true;
            for(int j=0;j<3;j++)
            {
                if(small[i][j]!=0)
                    ztr=false;
                if(small[i][j]!=1)
                    otr=false;
            }
            
            if(ztr==true)
            JOptionPane.showMessageDialog(new JFrame(),"0 wins");
            if(otr==true)
            JOptionPane.showMessageDialog(new JFrame(),"[] wins");
            
            for(int j=0;j<3;j++)
            {
                if(small[j][i]!=0)
                    ztr=false;
                if(small[j][i]!=1)
                    otr=false;
            }
            if(ztr==true)
            JOptionPane.showMessageDialog(new JFrame(),"0 wins");
            if(otr==true)
            JOptionPane.showMessageDialog(new JFrame(),"[] wins");
            
        }
        ztr=otr=true;
        
        for(int i=0;i<3;i++)
        {
            if(small[i][i]!=0)
            ztr=false;
            if(small[i][i]!=1)
            otr=false;
        }
        if(ztr==true)
            JOptionPane.showMessageDialog(new JFrame(),"0 wins");
        if(otr==true)
            JOptionPane.showMessageDialog(new JFrame(),"[] wins");
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int c=0;
        int gap=(W-H)/2;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                g.setColor(Color.white);
                if(c++%2==0)
                    g.setColor(Color.gray);
                g.fillRect(gap+i*l,j*l,l,l);
                for(int ii=0;ii<3;ii++)
                {
                        g.setColor(Color.white);
                        if(c%2==0)
                            g.setColor(Color.gray);
                        g.drawLine(gap+i*l+ii*(int)(l/3),j*l,gap+i*l+ii*(int)l/3,j*l+l);
                        g.drawLine(gap+i*l,j*l+ii*(int)l/3,gap+i*l+l,j*l+ii*(int)l/3);
                    
                }
            }
        }
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                if(big[i][j]==0)
                {
                    g.setColor(Color.red);
                    g.fillArc(gap+i*l/3+10,j*l/3+10,l-20,l-20,0,360);
                }
                if(big[i][j]==1)
                {
                    g.setColor(Color.blue);
                    g.fillRect(gap+i*l/3+10,j*l/3+10,l-20,l-20);
                }
            }
        }
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(small[i][j]==0)
                {
                    g.setColor(Color.red);
                    g.fillArc(gap+i*l+10,j*l+10,l-20,l-20,0,360);
                }
                if(small[i][j]==1)
                {
                    g.setColor(Color.blue);
                    g.fillRect(gap+i*l+10,j*l+10,l-20,l-20);
                }
            }
        }
    }
}