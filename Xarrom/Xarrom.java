

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Xarrom
{
    public static void main(String[]args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    new GameXarrom();
                }
            });
    }
}
class Coin
{
    int dx,dy,x,y,nx,ny;
    public Coin(int xx,int yy)
    {
        x=xx;
        y=yy;
        nx=x+dx;
        ny=y+dy;

    }
}
class GameXarrom extends JPanel implements Runnable
{
    JFrame f;
    Thread t;
    int X,Y,r,mx,my,fric=1,ix,iy,clk=0;
    boolean mp[]=new boolean[5];//mouse pressed
    double b,p,h;
    //reference:0-red,1-white,2-black,3-bstriker,4-wstriker
    Coin c[]=new Coin[5];
    public GameXarrom()
    {
        f=new JFrame("XaRRoM");
        f.add(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(900,900);
        f.setVisible(true);
        t=new Thread(this);
        t.start();

        super.addMouseMotionListener(new MouseMotionListener()
            {
                public void mouseDragged(MouseEvent me){}

                public void mouseMoved(MouseEvent me)
                {
                    clk=0;
                }
            });
        super.addMouseListener(new MouseListener()
            {
                public void mouseClicked(MouseEvent me)
                {
                    if(clk++==2)
                        for(int i=0;i<5;i++)
                            setDefault(i);

                }

                public void mouseEntered(MouseEvent me){}

                public void mouseExited(MouseEvent me){}

                public void mousePressed(MouseEvent me)
                {
                    mx=ix=me.getX();
                    my=iy=me.getY();

                    if(mx>c[3].x&&mx<c[3].x+r&&my>c[3].y&&my<c[3].y+r)
                        mp[3]=true;
                    if(mx>c[4].x&&mx<c[4].x+r&&my>c[4].y&&my<c[4].y+r)
                        mp[4]=true;
                }

                public void mouseReleased(MouseEvent me)
                {
                    for(int i=0;i<5;i++)
                    {
                        if(mp[i]==true)
                        {
                            c[i].dx=me.getX()-ix;
                            c[i].dy=me.getY()-iy;

                            mp[i]=false;
                        }
                    }
                }
            });
        X=getWidth();
        Y=getHeight();
        r=60;
        c[0]=new Coin(X/2-r/2,Y/2-r/2);
        c[1]=new Coin(X/2-r/2,Y/2-r/2*3);
        c[2]=new Coin(X/2-r/2,Y/2+r/2);
        c[3]=new Coin(X/10,Y/2-r/2);
        c[4]=new Coin(X-X/10-r,Y/2-r/2);
    }

    public void run()
    {
        while(true)
        {

            try
            {
                Thread.sleep(20);
            }
            catch(Exception e){}
            //mutual collisions
            for(int i=0;i<4;i++)
            {
                for(int j=i+1;j<5;j++)
                {
                    b=c[i].x-c[j].x;
                    p=c[i].y-c[j].y;
                    h=Math.sqrt(b*b+p*p);
                    if(h<r)
                    {
                        if(c[i].x<c[j].x&&c[i].y<c[j].y)
                        {
                            c[j].x=c[j].x+10;
                            c[j].y=c[j].y+10;
                        }
                        if(c[i].x<c[j].x&&c[i].y>c[j].y)
                        {
                            c[j].x=c[j].x+10;
                            c[j].y=c[j].y-10;
                        }
                        if(c[i].x>c[j].x&&c[i].y<c[j].y)
                        {
                            c[j].x=c[j].x-10;
                            c[j].y=c[j].y+10;
                        }
                        if(c[i].x>c[j].x&&c[i].y>c[j].y)
                        {
                            c[j].x=c[j].x-10;
                            c[j].y=c[j].y-10;
                        }
                        collide(c[i].x,c[j].x,c[i].y,c[j].y,c[i].dx,c[i].dy,c[j].dx,c[j].dy,i,j);

                    }
                    if(c[j].x==c[i].x&&c[j].y==c[i].y)
                    {
                        c[j].x=c[j].x+r;
                        c[j].y=c[j].y-r;
                    }
                }
            }
            for(int i=0;i<5;i++)
            {
                //speed control
                if(c[i].dx>30)
                    c[i].dx=30;
                if(c[i].dy>30)
                    c[i].dy=30;

                if(c[i].dx<-30)
                    c[i].dx=-30;
                if(c[i].dy<-30)
                    c[i].dy=-30;

                //Motion
                c[i].x+=c[i].dx;

                c[i].y+=c[i].dy;

                //Friction
                if(c[i].dx>0)
                    c[i].dx-=fric;
                if(c[i].dx<0)
                    c[i].dx+=fric;
                if(c[i].dy>0)
                    c[i].dy-=fric;
                if(c[i].dy<0)
                    c[i].dy+=fric;

                //Winning condition
                if(c[1].x+r/2<0&&c[1].y>Y/3&&c[1].y<Y/3*2)
                {
                    JOptionPane.showMessageDialog(f,"WHITE  WON!!");
                    setDefault(1);
                }
                if(c[2].x+r/2>X&&c[2].y>Y/3&&c[2].y<Y/3*2)
                {
                    JOptionPane.showMessageDialog(f,"BLACK  WON!!");
                    setDefault(2);
                }
                
                if((c[3].x+r/2<0&&c[3].y>Y/3&&c[3].y<Y/3*2)||(c[3].x+r/2>X&&c[3].y>Y/3&&c[3].y<Y/3*2))
                {
                setDefault(3);
                setDefault(2);
                }
                if((c[4].x+r/2<0&&c[4].y>Y/3&&c[4].y<Y/3*2)||(c[4].x+r/2>X&&c[4].y>Y/3&&c[4].y<Y/3*2))
                {
                setDefault(4);
                setDefault(1);
                }
                 
                //boundary conditions to bounce
                if((c[i].x)<0&&(c[i].y>Y/3*2&&c[i].y<Y||c[i].y>0&&c[i].y<Y/3))
                {
                    c[i].dx*=(-1);
                    c[i].x=0;
                }
                if((c[i].x+r)>X&&(c[i].y>Y/3*2&&c[i].y<Y||c[i].y>0&&c[i].y<Y/3))
                {
                    c[i].dx*=(-1);
                    c[i].x=X-(r);
                }
                if(((c[i].y+r)>Y)&&c[i].x>X/3&&c[i].x<X/3*2)
                {
                    c[i].dy*=-1;
                    c[i].y=Y-r;
                }
                if((c[i].y)<0&&c[i].x>X/3&&c[i].x<X/3*2)
                {
                    c[i].dy*=-1;
                    c[i].y=0;
                }

                //reset conditons
                //for white on top
                if((c[1].y+r/2<0&&(c[1].x>0&&c[1].x<X/3||c[1].x>X/3*2&&c[1].x<X))||(c[1].x-r/2>Y&&c[1].y>Y/3&&c[1].y<Y/3*2))
                    setDefault(1);
                //for black on top
                if(c[2].y+r/2<0&&(c[2].x>0&&c[2].x<X/3||c[2].x>X/3*2&&c[2].x<X)||(c[2].x+r/2<0&&c[2].y>Y/3&&c[2].y<Y/3*2))
                    setDefault(2);
                //for white at bottom
                if(c[1].y+r/2>Y&&(c[1].x>0&&c[1].x<X/3||c[1].x>X/3*2&&c[1].x<X))
                    setDefault(1);
                //for black at bottom
                if(c[2].y+r/2>Y&&(c[2].x>0&&c[2].x<X/3||c[2].x>X/3*2&&c[2].x<X))
                    setDefault(2);
                //for black striker on top
                if(c[3].y+r/2<0&&(c[3].x>0&&c[3].x<X/3||c[3].x>X/3*2&&c[3].x<X))
                {
                    setDefault(3);
                    setDefault(2);
                }
                //for white striker on top
                if(c[4].y+r/2<0&&(c[4].x>0&&c[4].x<X/3||c[4].x>X/3*2&&c[4].x<X))
                {
                    setDefault(4);
                    setDefault(1);
                }
                //for black striker at bottom
                if(c[3].y+r/2>Y&&(c[3].x>0&&c[3].x<X/3||c[3].x>X/3*2&&c[3].x<X))
                {
                    setDefault(3);
                    setDefault(2);
                }
                //for white striker at bottom
                if(c[4].y+r/2>Y&&(c[4].x>0&&c[4].x<X/3||c[4].x>X/3*2&&c[4].x<X))
                {
                    setDefault(4);
                    setDefault(1);
                }

            }
            repaint();
        }
    }

    public void paintComponent(Graphics g)
    {
        X=getWidth();
        Y=getHeight();
        super.paintComponent(g);
        setBackground(new Color(150,200,50));

        //Borders

        g.setColor(Color.BLACK);
        g.drawLine(X/2,0,X/2,Y);
        g.drawRect(-1,Y/3,X/10,Y/3);
        g.drawRect(X-X/10,Y/3,X/10,Y/3);

        g.setColor(new Color(50,200,50));
        g.fillArc(X/3,Y/3,X/3,Y/3,0,360);
        g.setColor(Color.BLACK);
        g.drawArc(X/3,Y/3,X/3,Y/3,0,360);

        //Xarrom Central
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,30));
        g.drawString("XARROM",X/2-55,Y/2);
        //coins
        g.setColor(Color.RED);
        g.fillArc(c[0].x,c[0].y,r,r,0,360);
        g.setColor(Color.BLACK);
        g.fillArc(c[0].x+10,c[0].y+10,r-20,r-20,0,360);
        g.setColor(Color.WHITE);
        g.fillArc(c[1].x,c[1].y,r,r,0,360);
        g.setColor(Color.BLACK);
        g.drawArc(c[1].x,c[1].y,r,r,0,360);
        g.fillArc(c[2].x,c[2].y,r,r,0,360);
        g.setColor(Color.WHITE);
        g.drawArc(c[2].x,c[2].y,r,r,0,360);
        g.setColor(Color.BLACK);
        g.fillArc(c[3].x,c[3].y,r,r,0,360);
        g.setColor(Color.WHITE);
        g.drawArc(c[3].x,c[3].y,r,r,0,360);
        g.setColor(Color.GRAY);
        g.fillArc(c[3].x+10,c[3].y+10,r-20,r-20,0,360);
        g.setColor(Color.WHITE);
        g.fillArc(c[4].x,c[4].y,r,r,0,360);
        g.setColor(Color.BLACK);
        g.fillArc(c[4].x+10,c[4].y+10,r-20,r-20,0,360);
        //safe Circle
        for(int i=0;i<5;i++)
        {
            if(mp[i]==true)
            {
                g.setColor(Color.RED);
                g.drawArc(c[i].x-15,c[i].y-15,r+30,r+30,0,360);
            }
        }
        //safe Zone
        g.setColor(Color.WHITE);
        g.fillRect(X/3,0,X/3,6);
        g.fillRect(X/3,Y-2,X/3,6);
        g.fillRect(0,0,6,Y/3);
        g.fillRect(0,Y/3*2,6,Y/3);
        g.fillRect(X-4,0,6,Y/3);
        g.fillRect(X-4,Y/3*2,6,Y/3);
        g.setColor(Color.BLACK);
        g.fillRect(0,Y/3,4,Y/3);
        g.fillRect(X-4,Y/3,4,Y/3);
        g.fillRect(0,0,X/3,4);
        g.fillRect(X*2/3,0,X/3,4);
        g.fillRect(0,Y-4,X/3,4);
        g.fillRect(X/3*2,Y-4,X/3,4);

    }

    public void collide(int x1,int x2,int y1,int y2,int ddx1,int ddy1,int ddx2,int ddy2,int i,int j)
    {
        double bb,pp,h2;
        double ndx1,ndx2,ndy1,ndy2;
        bb=Math.abs(x1-x2);
        pp=Math.abs(y1-y2);
        h2=bb*bb+pp*pp;
        ndx1=ddx2+((ddy2*2*bb*pp)/h2);
        ndy1=ddy2+((ddx2*2*bb*pp)/h2);
        ndx2=ddx1+((ddy1*2*bb*pp)/h2);
        ndy2=ddy1+((ddx1*2*bb*pp)/h2);

        c[i].dx=(int)(ndx1);

        c[j].dx=(int)(ndx2);

        c[i].dy=(int)(ndy1);

        c[j].dy=(int)(ndy2);
    }

    public void setDefault(int i)
    {
        switch(i)
        {
            case 0:c[0]=new Coin(X/2-r/2,Y/2-r/2);
            break;
            case 1:c[1]=new Coin(X/2-r/2,Y/2-r/2*3);
            break;
            case 2:c[2]=new Coin(X/2-r/2,Y/2+r/2);
            break;
            case 3:c[3]=new Coin(X/10,Y/2-r/2);
            break;
            case 4:c[4]=new Coin(X-X/10-r,Y/2-r/2);
            break;
        }
    }
}