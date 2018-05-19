import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Game extends JPanel implements MouseListener
{
    int user;
    Grid Small[][];
    Grid Big;
    boolean big_win;
    int turn;
    
    int W;
    int H;
    int L;
    int l;
    int vGap;
    int hGap;
    
    int si,sj;
    int bi,bj;
    
    
    public Game()
    {
        super();
        addMouseListener(this);
        W=Toolkit.getDefaultToolkit().getScreenSize().width;
        H=Toolkit.getDefaultToolkit().getScreenSize().height;
        L=H-110;
        l=L/3;
        vGap=15;
        hGap=100;
        
        JFrame f=new JFrame("MEGA-CROSS&KNOTS");
        f.add(this);
        f.setSize(H,H-20);
        f.setVisible(true);
        
        turn=0;
        user=-1;
        si=-1;
        sj=-1;
        bi=-1;
        bj=-1;
        
        Big=new Grid("big",null);
        Small=new Grid[3][3];
        
        for (int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                Small[i][j]=new Grid("small",null);
                Small[i][j].setCode(""+i+j);
                Small[i][j].setType("small");
            }
        }
        big_win=false;
        turn=81;
    }
    public void mouseEntered(MouseEvent me){}
    public void mouseExited(MouseEvent me){}
    public void mousePressed(MouseEvent me){}
    public void mouseReleased(MouseEvent me){}
    public void mouseClicked(MouseEvent me)
    {
        int x=me.getX();
        int y=me.getY();
        //check if location is valid
        //if valid then update else
        //do nothing
        if(big_win==true)
        {
            JOptionPane.showMessageDialog(new JFrame(),"THE GAME IS OVER!! PLAY AGAIN");
        }
        else if (turn==81)
        {
            setSmall_i_j(x,y);
            setBig_i_j(x,y);
            Small[bi][bj].setMove(user,si,sj);
            user=-1*user;
            turn--;
            repaint();
        }
        else
        {
            boolean tr=false;
            tr=check_validity(x,y);
            if(tr)
            {
                setSmall_i_j(x,y);
                setBig_i_j(x,y);
                if(!Small[bi][bj].isPresent(si,sj))
                    Small[bi][bj].setMove(user,si,sj);
                
                if(Small[bi][bj].checkWin(user))
                {
                    //user has won a Small grid. Update the Big Grid
                    if(!Big.isPresent(bi,bj))
                    Big.setMove(user,bi,bj);
                    big_win=Big.checkWin(user);
                }
                if(big_win==true)
                {
                    //Display user WON
                    if(user==-1)
                        JOptionPane.showMessageDialog(new JFrame(),"Red wins");
                    else
                        JOptionPane.showMessageDialog(new JFrame(),"Blue wins");
                }
                user=-1*user;
                turn--;
                //System.out.println(turn);
                repaint();
            }
        }
    }
    boolean check_validity(int x,int y)
    {
        int i=0;
        int j=0;
        boolean ptFound=false;
        for( i=0;i<3;i++)
        {
            for(j=0;j<3;j++)
            {
                if(x>(hGap+j*l)&&x<(hGap+(j+1)*l)&&y>(vGap+i*l)&&y<(vGap+(i+1)*l))
                {
                    ptFound=true;
                    break;
                }
            }
            if (ptFound)
                break;
        }
        if(i==si&&j==sj)
            return true;
        else
            return false;
    }
    void setSmall_i_j(int x,int y)
    {   
        boolean ptFound=false;
        int i=0;
        int j=0;
        
        for( i=0;i<9;i++)
        {
            for(j=0;j<9;j++)
            {
                if(x>(hGap+j*(int)(l/3))&&x<(hGap+(j+1)*(int)(l/3))&&y>(vGap+i*(int)(l/3))&&y<(vGap+(i+1)*(int)(l/3)))
                {
                    ptFound=true;
                    break;
                }
            }
            if (ptFound)
                break;
        }
        
        si=i%3;
        sj=j%3;
        //System.out.println(si+" "+sj);
    }
    void setBig_i_j(int x,int y)
    {
        boolean ptFound=false;
        int i=0;
        int j=0;
        for( i=0;i<3;i++)
        {
            for(j=0;j<3;j++)
            {
                if(x>(hGap+j*l)&&x<(hGap+(j+1)*l)&&y>(vGap+i*l)&&y<(vGap+(i+1)*l))
                {
                    ptFound=true;
                    break;
                }
            }
            if (ptFound)
                break;
        }
        bi=i;
        bj=j;
        //System.out.println(bi+" "+bj);
        
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        //Big Squares
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if((i+j)%2==0)
                {
                    g.setColor(Color.black);
                    g.fillRect(hGap+j*l,vGap+i*l,l,l);
                }
                else
                {
                    g.setColor(Color.gray);
                    g.fillRect(hGap+j*l,vGap+i*l,l,l);
                }
                for(int ii=0;ii<3;ii++)
                {
                        g.setColor(Color.black);
                        if((i+j)%2==0)
                            g.setColor(Color.gray);
                        g.drawLine(hGap+j*l+ii*(int)(l/3),vGap+i*l,hGap+j*l+ii*(int)(l/3),vGap+i*l+l);
                        g.drawLine(hGap+j*l,vGap+i*l+ii*(int)(l/3),hGap+j*l+l,vGap+i*l+ii*(int)(l/3));
                }
                
            }
        }
        g.setColor(Color.white);
        g.fillRect(hGap+sj*l,vGap+si*l,l,l);
        
        //Coloring on selection winning
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                for(int k=0;k<3;k++)
                {
                    for(int ll=0;ll<3;ll++)
                    {
                        if(Small[i][j].g[k][ll]==-1)
                        {
                            g.setColor(new Color(180,50,50));
                            g.fillArc(hGap+j*l+ll*(int)(l/3),vGap+i*l+k*(int)(l/3),(int)(l/3),(int)(l/3),0,360);
                        }
                        if(Small[i][j].g[k][ll]==1)
                        {
                            g.setColor(new Color(50,50,180));
                            g.fillRect(hGap+j*l+ll*(int)(l/3),vGap+i*l+k*(int)(l/3),(int)(l/3),(int)(l/3));
                        }
                    }
                }
                if(Big.g[i][j]==-1)
                {
                    g.setColor(Color.red);
                    g.drawArc(hGap+j*l,vGap+i*l,l,l,0,360);    
                }
                if(Big.g[i][j]==1)
                {
                    g.setColor(Color.blue);
                    g.drawRect(hGap+j*l+10,vGap+i*l+10,l-20,l-20);
                }
            }
        }
        //for lines
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                for(int ii=0;ii<3;ii++)
                {
                        g.setColor(new Color(10,10,10));
                        if((i+j)%2==0)
                            g.setColor(new Color(20,20,20));
                        g.drawLine(hGap+j*l+ii*(int)(l/3),vGap+i*l,hGap+j*l+ii*(int)(l/3),vGap+i*l+l);
                        g.drawLine(hGap+j*l,vGap+i*l+ii*(int)(l/3),hGap+j*l+l,vGap+i*l+ii*(int)(l/3));
                }
                
            }
        }
        
        // legend next turn
        if(user==-1)
        {
            g.setColor(new Color(180,50,50));
            g.fillArc(0,(int)(H/2)-(int)(l/3),(int)(l/3),(int)(l/3),0,360);
        }
        else
        {
            
            g.setColor(new Color(50,50,180));
            g.fillRect(0,(int)(H/2)-(int)(l/3),(int)(l/3),(int)(l/3));
        }
    }
    public static void main(String[]args)
    {
        new Game();
    }
}