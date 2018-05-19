
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import java.io.*;
class Ludo
{
    public static void main(String[]args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
            {
                public void run(){new gameLudo();}
            });
    }
}

class gameLudo extends JPanel implements Runnable
{
    JFrame f;
    Thread t;
    Dice d=new Dice();
    int c=4;
    int X, Y,dn=0;    
    int mx, my;  // the most recently recorded mouse coordinates
    int xr[]=new int[c];
    int yr[]=new int [c];
    int xb[]=new int[c];
    int yb[]=new int [c];
    int xg[]=new int[c];
    int yg[]=new int [c];
    int xy[]=new int[c];
    int yy[]=new int [c];
    //these are the mouse confirmators
    boolean isMr[]=new boolean[c];
    boolean isMb[]=new boolean[c];
    boolean isMg[]=new boolean[c];
    boolean isMy[]=new boolean[c];
    boolean mp=false,dp=false;

    int i,j,k;
    boolean mouseMovd=false;
    public gameLudo()
    {
        f=new JFrame("LuDo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setSize(1000,900);
        f.add(this);
        t=new Thread(this);
        t.start();
        super.addMouseListener(new MouseListener()
            {
                public void mouseEntered(MouseEvent e)
                {
                }

                public void mouseExited(MouseEvent e)
                {
                }

                public void mousePressed(MouseEvent e)
                {
                    mp=true;

                    mx = e.getX();
                    my = e.getY();
                    //this checks the positions
                    for(int i=0;i<c;i++)
                    {
                        if ( xr[i] < mx && mx < xr[i]+40 && yr[i] < my && my < yr[i]+40 ) 
                            isMr[i] = true;

                        if ( xb[i] < mx && mx < xb[i]+40 && yb[i] < my && my < yb[i]+40 ) 
                            isMb[i] = true;

                        if ( xg[i] < mx && mx < xg[i]+40 && yg[i] < my && my < yg[i]+40 ) 
                            isMg[i] = true;

                        if ( xy[i] < mx && mx < xy[i]+40 && yy[i] < my && my < yy[i]+40 ) 
                            isMy[i] = true;
                    }
                    if(mx>900&&mx<1000&&my>880&&my<900)
                    {
                        dp=true;
                        
                    }
                    repaint();
                    e.consume();
                }

                public void mouseReleased(MouseEvent e)
                {
                    for(int i=0;i<c;i++)
                    {
                        isMr[i] = false;
                        isMb[i] = false;
                        isMg[i] = false;
                        isMy[i] = false;
                    }
                    mp=false;
                    if(dp==true)
                    {
                        dn=(d.value(6));
                        dp=false;
                        
                    }
                    repaint();
                    //e.consume();
                }

                public void mouseClicked(MouseEvent e)
                {
                }
            });
        super.addMouseMotionListener(new MouseMotionListener()
            {

                public void mouseMoved(MouseEvent e)
                {

                    Y=getSize().height;
                    X=getSize().width;
                    mouseMovd=true;
                    repaint();
                    e.consume();
                }

                public void mouseDragged(MouseEvent e)
                {
                    //this maintains the positions of the men
                    for(int i=0;i<c;i++)
                    {
                        if ( isMr[i] ) 
                        {
                            int new_mx = e.getX();
                            int new_my = e.getY();
                            xr[i] += new_mx - mx;
                            yr[i] += new_my - my;
                            mx = new_mx;
                            my = new_my;
                        }

                        if ( isMb[i] ) 
                        {
                            int new_mx = e.getX();
                            int new_my = e.getY();
                            xb[i] += new_mx - mx;
                            yb[i] += new_my - my;
                            mx = new_mx;
                            my = new_my;
                        }

                        if ( isMg[i] ) 
                        {
                            int new_mx = e.getX();
                            int new_my = e.getY();
                            xg[i] += new_mx - mx;
                            yg[i] += new_my - my;
                            mx = new_mx;
                            my = new_my;
                        }

                        if ( isMy[i] ) 
                        {
                            int new_mx = e.getX();
                            int new_my = e.getY();
                            xy[i] += new_mx - mx;
                            yy[i] += new_my - my;
                            mx = new_mx;
                            my = new_my;
                        }
                    }
                    repaint();
                    e.consume();
                }
            });

     
        X = 900;
        Y = 900;
        //this initialises the positions
        for(int i=0;i<c;i++)
        {
            xr[i] = X/2 - 20;
            yr[i] = Y/2 - 20;

            xb[i] = X/2 - 20;
            yb[i] = Y/2 - 20;

            xg[i] = X/2 - 20;
            yg[i] = Y/2 - 20;

            xy[i] = X/2 - 20;
            yy[i] = Y/2 - 20;
        }

    }

    public void run()
    {
        try{Thread.sleep(20);
        }
        catch(Exception e){}
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if(mp==true)
        {
            // clear the background to white

            g.setColor( Color.BLACK );
            g.fillRect( 0, 0, X, Y );

            //this is for the small columns
            g.setColor(new Color(100,10,10));
            g.fillRect(60,420,300,60);
            g.setColor(new Color(10,10,100));
            g.fillRect(420,60,60,300);
            g.setColor(new Color(10,100,10));
            g.fillRect(420,540,60,300);
            g.setColor(new Color(200,200,10));
            g.fillRect(540,420,300,60);
            //for-loops for the checked lines
            for(int x=0;x<=900;x+=60)
            {
                g.setColor(Color.GRAY);
                g.drawLine(x,0,x,900);
            }
            for(int y=0;y<=900;y+=60)
                g.drawLine(0,y,900,y);

            //this is for the central variation
            if(mouseMovd==true)
            {

                if(i>250)
                    i=0;
                if(k>100)
                    k=0;
                if(j<1)
                    j=250;

                g.setColor(new Color(i,j,k));
                g.fillRect(360,360,60,60);
                g.setColor(new Color(j,k,i));
                g.fillRect(420,360,60,60);
                g.setColor(new Color(k,i,j));
                g.fillRect(480,360,60,60);
                g.setColor(new Color(i,k,j));
                g.fillRect(360,420,60,60); 
                g.setColor(new Color(k,j,i));
                g.fillRect(480,420,60,60);
                g.setColor(new Color(k,k,i));
                g.fillRect(360,480,60,60);
                g.setColor(new Color(j,j,i));
                g.fillRect(420,480,60,60);
                g.setColor(new Color(i,i,i));
                g.fillRect(480,480,60,60);
                g.setColor(new Color(j,i,k));
                g.fillArc(400,400,100,100,0,360);
                i++;
                j--;
                k++;
                //this fills the eight circles
                g.setColor(new Color(i,j,k));
                g.fillArc(360,120,60,60,0,360);
                g.setColor(Color.green);
                g.fillArc(360,780,60,60,0,360);
                g.setColor(Color.red);
                g.fillArc(60,360,60,60,0,360);
                g.setColor(new Color(i,k,j));
                g.fillArc(120,480,60,60,0,360);
                g.setColor(Color.blue);
                g.fillArc(480,60,60,60,0,360);
                g.setColor(new Color(k,k,i));
                g.fillArc(720,360,60,60,0,360);
                g.setColor(Color.yellow);
                g.fillArc(780,480,60,60,0,360);
                g.setColor(new Color(j,j,j));
                g.fillArc(480,720,60,60,0,360);
            }
            //these are for the four big boxes 
            g.setColor(new Color(100,10,10));
            g.fillRect(0,0,360,360);
            g.setColor(new Color(10,10,100));
            g.fillRect(540,0,360,360);
            g.setColor(new Color(10,100,10));
            g.fillRect(0,540,360,360);
            g.setColor(new Color(200,200,10));
            g.fillRect(540,540,360,360);

            //these are for the 16 small boxes 
            g.setColor(Color.red);
            g.fillArc(60,60,90,90,0,360);
            g.fillArc(210,60,90,90,0,360);
            g.fillArc(60,210,90,90,0,360);
            g.fillArc(210,210,90,90,0,360);

            g.setColor(Color.blue);
            g.fillArc(600,60,90,90,0,360);
            g.fillArc(750,60,90,90,0,360);
            g.fillArc(600,210,90,90,0,360);
            g.fillArc(750,210,90,90,0,360);

            g.setColor(Color.green);
            g.fillArc(60,600,90,90,0,360);
            g.fillArc(210,600,90,90,0,360);
            g.fillArc(60,750,90,90,0,360);
            g.fillArc(210,750,90,90,0,360);

            g.setColor(Color.yellow);
            g.fillArc(600,600,90,90,0,360);
            g.fillArc(750,600,90,90,0,360);
            g.fillArc(750,750,90,90,0,360);
            g.fillArc(600,750,90,90,0,360);

            //this sets the colors and shape of the men
            for(int i=0;i<c;i++)
            {
                g.setColor(new Color(100,10,10));
                g.fillArc( xr[i], yr[i], 40, 40,0,360 );
                g.setColor( Color.white );
                g.drawArc( xr[i], yr[i], 40, 40,0,360 );

                g.setColor(new Color(10,10,100));
                g.fillArc( xb[i], yb[i], 40, 40,0,360 );
                g.setColor( Color.white );
                g.drawArc( xb[i], yb[i], 40, 40,0,360 );

                g.setColor(new Color(10,100,10));
                g.fillArc( xg[i], yg[i], 40, 40,0,360 );
                g.setColor( Color.white );
                g.drawArc( xg[i], yg[i], 40, 40,0,360 );

                g.setColor(new Color(200,200,10));
                g.fillArc( xy[i], yy[i], 40, 40,0,360 );
                g.setColor( Color.white );
                g.drawArc( xy[i], yy[i], 40, 40,0,360 );
            }
            //central Home
            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString("HOME",420,450);
        }
        else
        {
            // clear the background to white

            g.setColor( Color.BLACK );
            g.fillRect( 0, 0, X, Y );

            //this is for the small columns
            setBackground(Color.BLACK);
            g.setColor(Color.red);
            g.fillRect(60,420,300,60);
            g.setColor(Color.blue);
            g.fillRect(420,60,60,300);
            g.setColor(Color.green);
            g.fillRect(420,540,60,300);
            g.setColor(Color.yellow);
            g.fillRect(540,420,300,60);
            //for-loops for the checked lines
            for(int x=0;x<=900;x+=60)
            {
                g.setColor(Color.GRAY);
                g.drawLine(x,0,x,900);
            }
            for(int y=0;y<=900;y+=60)
                g.drawLine(0,y,900,y);

            //this is for the central variation
            if(mouseMovd==true)
            {
                if(i>250)
                    i=0;
                if(k>100)
                    k=0;
                if(j<1)
                    j=250;

                g.setColor(new Color(i,j,k));
                g.fillRect(360,360,60,60);
                g.setColor(new Color(j,k,i));
                g.fillRect(420,360,60,60);
                g.setColor(new Color(k,i,j));
                g.fillRect(480,360,60,60);
                g.setColor(new Color(i,k,j));
                g.fillRect(360,420,60,60); 
                g.setColor(new Color(k,j,i));
                g.fillRect(480,420,60,60);
                g.setColor(new Color(k,k,i));
                g.fillRect(360,480,60,60);
                g.setColor(new Color(j,j,i));
                g.fillRect(420,480,60,60);
                g.setColor(new Color(i,i,i));
                g.fillRect(480,480,60,60);
                g.setColor(new Color(j,i,k));
                g.fillArc(400,400,100,100,0,360);
                i++;
                j--;
                k++;
                //this fills the eight circles
                g.setColor(new Color(i,j,k));
                g.fillArc(360,120,60,60,0,360);
                g.setColor(Color.green);
                g.fillArc(360,780,60,60,0,360);
                g.setColor(Color.red);
                g.fillArc(60,360,60,60,0,360);
                g.setColor(new Color(i,k,j));
                g.fillArc(120,480,60,60,0,360);
                g.setColor(Color.blue);
                g.fillArc(480,60,60,60,0,360);
                g.setColor(new Color(k,k,i));
                g.fillArc(720,360,60,60,0,360);
                g.setColor(Color.yellow);
                g.fillArc(780,480,60,60,0,360);
                g.setColor(new Color(j,j,j));
                g.fillArc(480,720,60,60,0,360);
            }
            //these are for the four big boxes 
            g.setColor(Color.red);
            g.fillRect(0,0,360,360);
            g.setColor(Color.blue);
            g.fillRect(540,0,360,360);
            g.setColor(Color.green);
            g.fillRect(0,540,360,360);
            g.setColor(Color.yellow);
            g.fillRect(540,540,360,360);

            //these are for the 16 small boxes 
            g.setColor(new Color(100,10,10));
            g.fillArc(60,60,90,90,0,360);
            g.fillArc(210,60,90,90,0,360);
            g.fillArc(60,210,90,90,0,360);
            g.fillArc(210,210,90,90,0,360);

            g.setColor(new Color(10,10,100));
            g.fillArc(600,60,90,90,0,360);
            g.fillArc(750,60,90,90,0,360);
            g.fillArc(600,210,90,90,0,360);
            g.fillArc(750,210,90,90,0,360);

            g.setColor(new Color(10,100,10));
            g.fillArc(60,600,90,90,0,360);
            g.fillArc(210,600,90,90,0,360);
            g.fillArc(60,750,90,90,0,360);
            g.fillArc(210,750,90,90,0,360);

            g.setColor(new Color(200,200,10));
            g.fillArc(600,600,90,90,0,360);
            g.fillArc(750,600,90,90,0,360);
            g.fillArc(750,750,90,90,0,360);
            g.fillArc(600,750,90,90,0,360);

            //this sets the colors and shape of the men
            for(int i=0;i<c;i++)
            {
                g.setColor( Color.red );
                g.fillArc( xr[i], yr[i], 40, 40,0,360 );
                g.setColor( Color.black );
                g.drawArc( xr[i], yr[i], 40, 40,0,360 );

                g.setColor( Color.blue );
                g.fillArc( xb[i], yb[i], 40, 40,0,360 );
                g.setColor( Color.black );
                g.drawArc( xb[i], yb[i], 40, 40,0,360 );

                g.setColor( Color.green );
                g.fillArc( xg[i], yg[i], 40, 40,0,360 );
                g.setColor( Color.black );
                g.drawArc( xg[i], yg[i], 40, 40,0,360 );

                g.setColor( Color.yellow );
                g.fillArc( xy[i], yy[i], 40, 40,0,360 );
                g.setColor( Color.black );
                g.drawArc( xy[i], yy[i], 40, 40,0,360 );
            }
            //central Home
            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString("HOME",420,450);
        }
        
        int Hd=900/6;
        g.setColor(Color.RED);
        for(int i=0;i<dn;i++)
        {
            g.fillArc(900+100/4,Hd*i+Hd/4,(100)/2,(100)/2,0,360);
        }
        if(dp==true)
        g.setColor(Color.BLUE);
        g.fillRect(900,Y-25,100,25);
        
        g.setColor(Color.WHITE);
        for(int i=dn;i<6;i++)
        {
            g.fillArc(900+(100)/4,Hd*i+Hd/4,(100)/2,(100)/2,0,360);
        }
        
        
    }
}

class Dice
{
    int value(int v)
    {
        double nn=Math.random();
        nn=nn*Math.pow(10,7);
       int n=(int)nn;
       n=n%v+1;
       return n;
    }
}