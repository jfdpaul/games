
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Checkers
{
    public static void main(String[]args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
            {
                public void run(){new gameCheckers();}
            });
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
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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