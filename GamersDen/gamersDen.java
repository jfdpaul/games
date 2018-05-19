import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;
class gamersDen
{
    public static void main(String[]args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    new Kit();
                }
            });
    }
}
class Kit extends JPanel 
{
    JFrame f;
    Image img;
    public Kit()
    {
        f=new JFrame("GAMER'S DEN");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setSize(600,650);

        JLabel l=new JLabel("CHOOSE YOUR FAVOURITE GAME");
        f.add(l);
        f.add(this);
        super.addMouseListener(new MouseListener()
            {
                public void mouseClicked(MouseEvent me)
                {
                    int  mx=me.getX();
                    int my=me.getY();
                    if(mx>0&&mx<270&&my>0&&my<280)
                        new gameLudo();
                    if(mx>300&&mx<580&&my>300&&my<580)
                        new GameFrame();
                    if(mx>0&&mx<270&&my>300&&my<580)
                        new GameXarrom();
                    if(mx>300&&mx<580&&my>0&&my<280)
                        new gameCheckers();

                }

                public void mouseEntered(MouseEvent me){}

                public void mouseExited(MouseEvent me){}

                public void mousePressed(MouseEvent me)
                {

                }

                public void mouseReleased(MouseEvent me)
                {

                }
            });

    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        try
        {
            g.drawImage(ImageIO.read(new File("Ludo.jpg")),0,0,null); 
            g.drawImage(ImageIO.read(new File("Checkers.jpg")),300,0,null); 
            g.drawImage(ImageIO.read(new File("Xarrom.jpg")),0,300,null); 
            g.drawImage(ImageIO.read(new File("UpHill.jpg")),300,300,null); 
        }
        catch(Exception e){/*handled in paintComponent()*/}
        g.setColor(Color.WHITE);
        g.drawString("Created by <JONATHAN PAUL>--2012",0,600);
        g.drawString("Play all Games in Full Screen",0,280);
    }
}


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
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setSize(1100,900);
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
                    
                    dn=0;
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
      
       
       g.setColor(Color.RED);
       g.fillRoundRect(900,350,200,200,50,50);
        if(dp==true)
        g.setColor(Color.BLUE);
        g.fillRect(900,Y-25,100,25);
        if(dn==1)
        {
       
        g.setColor(Color.BLACK);
        g.fillArc(975,425,50,50,0,360);
        }
         if(dn==2)
        {
        
        g.setColor(Color.BLACK);
        g.fillArc(975,365,50,50,0,360);
        g.fillArc(975,485,50,50,0,360);
        }
         if(dn==3)
        {
        
        g.setColor(Color.BLACK);
        g.fillArc(975,365,50,50,0,360);
        g.fillArc(975,425,50,50,0,360);
        g.fillArc(975,485,50,50,0,360);
        }
         if(dn==4)
        {
        
        
        g.setColor(Color.BLACK);
        g.fillArc(915,365,50,50,0,360);
        g.fillArc(1035,365,50,50,0,360);
        g.fillArc(915,485,50,50,0,360);
        g.fillArc(1035,485,50,50,0,360);
        
        }
         if(dn==5)
        {
        
        
        g.setColor(Color.BLACK);
        g.fillArc(915,365,50,50,0,360);
        g.fillArc(1035,365,50,50,0,360);
        g.fillArc(975,425,50,50,0,360);
        g.fillArc(915,485,50,50,0,360);
        g.fillArc(1035,485,50,50,0,360);
        }
         if(dn==6)
        {
        
        g.setColor(Color.BLACK);
        g.fillArc(915,365,50,50,0,360);
        
        g.fillArc(1035,365,50,50,0,360);
        g.fillArc(915,425,50,50,0,360);
        
        g.fillArc(1035,425,50,50,0,360);
        g.fillArc(915,485,50,50,0,360);
        
        g.fillArc(1035,485,50,50,0,360);
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

class gameCheckers extends JPanel implements Runnable
{
    JFrame f;
    Thread t;

    int n=2;
    int c=12;
    int X, Y;    
    int mx, my;  // the most recently recorded mouse coordinates
    int xw[]=new int[c];
    int yw[]=new int [c];
    int xb[]=new int[c];
    int yb[]=new int [c];

    //these are the mouse confirmators
    boolean isMw[]=new boolean[c];
    boolean isMb[]=new boolean[c];
    boolean isQw[]=new boolean[c];
    boolean isQb[]=new boolean[c];
    boolean mp=false;

    Color cr[]={Color.MAGENTA,Color.GREEN,Color.BLUE,Color.YELLOW,Color.ORANGE};

    public gameCheckers()
    {
        f=new JFrame("ChEcKErS");
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setSize(950,900);
        f.add(this);
        t=new Thread(this);
        t.start();

        X = 820;
        Y = 800;
        //this initialises the positions
        for(int i=0;i<c;i++)
        {
            xw[i] = X/2 - 45;
            yw[i] = Y/2 - 45;

            xb[i] = X/2 - 45;
            yb[i] = Y/2 - 45;
        }
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
                        if ( xw[i] < mx && mx < xw[i]+90 && yw[i] < my && my < yw[i]+90 ) 
                            isMw[i] = true;

                        if ( xb[i] < mx && mx < xb[i]+90 && yb[i] < my && my < yb[i]+90 ) 
                            isMb[i] = true;
                    }
                    repaint();
                    e.consume();
                }

                public void mouseReleased(MouseEvent e)
                {
                    for(int i=0;i<c;i++)
                    {
                        isMw[i] = false;
                        isMb[i] = false;
                    }
                    mp=false;
                    e.consume();
                }

                public void mouseClicked(MouseEvent e)
                {
                }
            });
        super.addMouseMotionListener(new MouseMotionListener()
            {
                public void mouseMoved(MouseEvent e)
                {
                }

                public void mouseDragged(MouseEvent e)
                {
                    //this maintains the positions of the men
                    for(int i=0;i<c;i++)
                    {
                        if ( isMw[i] ) 
                        {
                            int new_mx = e.getX();
                            int new_my = e.getY();
                            xw[i] += new_mx - mx;
                            yw[i] += new_my - my;
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
                    }
                    repaint();
                    e.consume();
                }

            });

    }

    public void run()
    {
        try{Thread.sleep(200);
        }
        catch(Exception e){}
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        g.setColor( Color.RED );
        g.fillRect( 100, 0, X, Y );
        //black boxes
        int i=100;
        for(int j=0;j<=700;j+=100)
        {
            for(;i<=800;i+=200)
            {
                g.setColor(Color.black);
                g.fillRect(i,j,100,100);
            }
            if(j==0||j%200==0)
                i=200;
            else 
                i=100;
        }
        //for-loops for the checked lines
        g.setColor(Color.WHITE);
        for(int x=100;x<=900;x+=100)
            g.drawLine(x,0,x,800);
        for(int y=0;y<=800;y+=100)
            g.drawLine(100,y,800,y);
        //special effect
        for(int k=0;k<=700;k+=100)
        { 
            g.setColor(cr[n++]);
            g.fillRect(80,k,20,100); 
            g.fillRect(900,k,20,100); 
            if(n==5)
                n=0;
        }   

        //this sets the colors and shape of the men
        for( i=0;i<c;i++)
        {
            g.setColor(Color.white);
            g.fillArc( xw[i], yw[i], 90, 90,0,360 );
            g.setColor( Color.black);
            g.drawArc( xw[i], yw[i], 90, 90,0,360 );

            g.setColor(Color.black);
            g.fillArc( xb[i], yb[i], 90, 90,0,360 );
            g.setColor( Color.white);
            g.drawArc( xb[i], yb[i], 90, 90,0,360 );
        }

        for(int f=0;f<c;f++)
        {
            if(yw[f]>695)
                isQw[f]=true;
            if(yb[f]<15)
                isQb[f]=true;
            //queen
            g.setColor(Color.red);
            if(isQw[f]==true)
                g.fillArc( xw[f]+25, yw[f]+25, 40, 40,0,360 );
            if(isQb[f]==true)
                g.fillArc( xb[f]+25, yb[f]+25, 40, 40,0,360 );
        }
        //this is the text
        g.setColor(Color.red);
        g.drawString("WHITE",120,80);
        g.setColor(Color.white);
        g.drawString("Black",120,780);

    }
}
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
                    f.setVisible(false);
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
        //super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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