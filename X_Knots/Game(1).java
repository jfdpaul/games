import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Game extends JPanel implements MouseListener
{
    int H;
    int L;
    int l;
    int turn;
    int hGap;
    int vGap;
    Grid array;
    int pi;
    int pj;
    int N;
    int P;
    int WN;
    boolean won;
    public Game()
    {
        super();
        addMouseListener(this);
        
        won=false;
        N=3;
        P=2;
        P=Integer.parseInt(JOptionPane.showInputDialog(new JFrame(),"Enter Number of Players"));
        N=Integer.parseInt(JOptionPane.showInputDialog(new JFrame(),"Enter Grid Size (Eg.5 for 5x5)"));
        WN=Integer.parseInt(JOptionPane.showInputDialog(new JFrame(),"Enter Winning Stretch "));
        
        hGap=100;
        vGap=40;
        
        H=Toolkit.getDefaultToolkit().getScreenSize().height;
        L=H-110;
        l=(int)L/N;
        JFrame f=new JFrame("X&Knots");
        f.add(this);
        f.setSize(H,H+hGap);
        f.setVisible(true);
        
        turn=0;
        
       
        array=new Grid(N,P);
        pi=-1;
        pj=-1;
    }
    public void mouseEntered(MouseEvent me){}
    public void mouseExited(MouseEvent me){}
    public void mousePressed(MouseEvent me){}
    public void mouseReleased(MouseEvent me){}
    public void mouseClicked(MouseEvent me)
    {
        int x=me.getX();
        int y=me.getY();
        setCood(x,y);
        if(won)
        JOptionPane.showMessageDialog(new JFrame(),"GAME OVER");
        else if(array.setMove((turn%P)+1,pi,pj))
        {
            
            if(array.check_win((turn%P)+1,WN))
            {
                if(((turn%P)+1)==1)
                {
                    JOptionPane.showMessageDialog(new JFrame(),"RED WON");
                    won=true;
                }
                if(((turn%P)+1)==2)
                {
                    JOptionPane.showMessageDialog(new JFrame(),"BLUE WON");
                    won=true;
                }
                if(((turn%P)+1)==3)
                {
                    JOptionPane.showMessageDialog(new JFrame(),"GREEN WON");
                    won=true;
                }
                if(((turn%P)+1)==4)
                {
                    JOptionPane.showMessageDialog(new JFrame(),"YELLOW WON");
                    won=true;
                }
                if(((turn%P)+1)==5)
                {
                    JOptionPane.showMessageDialog(new JFrame(),"ORANGE WON");
                    won=true;
                }
                if(((turn%P)+1)==6)
                {
                    JOptionPane.showMessageDialog(new JFrame(),"PINK WON");
                    won=true;
                }
                if(((turn%P)+1)==7)
                {
                    JOptionPane.showMessageDialog(new JFrame(),"WHITE WON");
                    won=true;
                }
                if(((turn%P)+1)==8)
                {
                    JOptionPane.showMessageDialog(new JFrame(),"CYAN WON");
                    won=true;
                }
                
            }
            turn++;
            repaint();
        }
    }
    public void setCood(int x,int y)
    {
        boolean ptFound=false;
        int i=0;
        int j=0;;
        here:for(i=0;i<N;i++)
        {
            for(j=0;j<N;j++)
            {
                if(x>(hGap+j*l)&&x<(hGap+(j+1)*l)&&y>(vGap+i*l)&&y<(vGap+(i+1)*l))
                {
                    ptFound=true;
                    break here;
                }
            }
        }
        if (ptFound==true)
        {
            pi=i;
            pj=j;
        }
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                g.setColor(Color.gray);
                if((i+j)%2==0)
                    g.setColor(Color.black);
                g.fillRect(hGap+j*l,vGap+i*l,l,l);
            }
        }
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                if(array.g[i][j]==1)
                {
                    g.setColor(Color.red);
                    g.fillArc(hGap+j*l,vGap+i*l,l,l,0,360);
                }
                if(array.g[i][j]==2)
                {
                    g.setColor(Color.blue);
                    g.fillArc(hGap+j*l,vGap+i*l,l,l,0,360);
                }
                if(array.g[i][j]==3)
                {
                    g.setColor(Color.green);
                    g.fillArc(hGap+j*l,vGap+i*l,l,l,0,360);
                }
                if(array.g[i][j]==4)
                {
                    g.setColor(Color.yellow);
                    g.fillArc(hGap+j*l,vGap+i*l,l,l,0,360);
                }
                if(array.g[i][j]==5)
                {
                    g.setColor(Color.orange);
                    g.fillArc(hGap+j*l,vGap+i*l,l,l,0,360);
                }
                if(array.g[i][j]==6)
                {
                    g.setColor(Color.pink);
                    g.fillArc(hGap+j*l,vGap+i*l,l,l,0,360);
                }
                if(array.g[i][j]==7)
                {
                    g.setColor(Color.white);
                    g.fillArc(hGap+j*l,vGap+i*l,l,l,0,360);
                }
                if(array.g[i][j]==8)
                {
                    g.setColor(Color.cyan);
                    g.fillArc(hGap+j*l,vGap+i*l,l,l,0,360);
                }
            }
        }
                if(((turn%P)+1)==1)
                {
                    g.setColor(Color.red);
                }
                if(((turn%P)+1)==2)
                {
                    g.setColor(Color.blue);
                }
                if(((turn%P)+1)==3)
                {
                    g.setColor(Color.green);
                }
                if(((turn%P)+1)==4)
                {
                    g.setColor(Color.yellow);
                }
                if(((turn%P)+1)==5)
                {
                    g.setColor(Color.orange);
                }
                if(((turn%P)+1)==6)
                {
                    g.setColor(Color.pink);
                }
                if(((turn%P)+1)==7)
                {
                    g.setColor(Color.white);
                }
                if(((turn%P)+1)==8)
                {
                    g.setColor(Color.cyan);
                }
                
                    g.fillArc(1,L/2,hGap,hGap,0,360);
    }
    public static void main(String[]args)
    {
        new Game();
    }
}