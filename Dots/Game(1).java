import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class Game extends JPanel implements Runnable,MouseListener
{
    JFrame j;
    String player[];
    int n;
    Dot d[][];
    int X,Y,XX,YY;
    Thread t;
    Dot d1,d2;
    int bx=0;
    int turn=0;
    String name[][];
    int dx,dy;
    boolean xtr=false;
    Color pColor[]={Color.red,Color.green,Color.blue,Color.yellow,Color.white,Color.cyan};
    public Game(String play[],int nn)
        {
            
            addMouseListener(this);
            d1=d2=null;
            X=Toolkit.getDefaultToolkit().getScreenSize().width;
             Y=Toolkit.getDefaultToolkit().getScreenSize().height;
            player=new String[play.length];
            for(int i=0;i<play.length;i++)
                player[i]=play[i];
            n=nn;
            j=new JFrame(".....DOTS.....");
            j.setVisible(true);
            j.setSize(X,Y);
            j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            j.add(this);
            d=new Dot[n][n];
            name=new String[n-1][n-1];
            dx=X/n;
            dy=Y/n;
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                    d[i][j]=new Dot();
                    d[i][j].i=i;
                    d[i][j].j=j;
                    d[i][j].px=15+i*dx;
                    d[i][j].py=15+j*dy;
                }
            }
            t=new Thread(this);
            t.start();
        }
    public void run()
    {
        try{
            while(xtr==false)
            {
                Thread.sleep(100);
                
                repaint();
            }
            if(xtr)
            {
                int ccc[]=new int[player.length];
                                for(int i=0;i<n-1;i++)
                                {
                                    for(int j=0;j<n-1;j++)
                                    {
                            
                                        for(int k=0;k<player.length;k++)
                                        if(name[i][j]!=null&&name[i][j].equals(""+player[k].charAt(0)))
                                        {
                                        ccc[k]=ccc[k]+1;
                                        }
                                     }
                                    }
                                    String s="";
                                    for(int i=0;i<player.length;i++)
                                        s=s+player[i]+"="+ccc[i]+"\n";
                                        JOptionPane.showMessageDialog(new JFrame(),s);
                                        JOptionPane.showMessageDialog(new JFrame(),"GAME OVER :)");
            }
       }
        catch(Exception e){}
    }
    public void mouseEntered(MouseEvent me){}
    public void mouseExited(MouseEvent me){}
    public void mousePressed(MouseEvent me){}
    public void mouseClicked(MouseEvent me)
    {
        int x=me.getX();
        int y=me.getY();
      here:  for(int i=0;i<n;i++)
        {
            //if(y>d[i][0].py&&y<d[i][0].py+d[i][0].r)
            for(int j=0;j<n;j++)
            {
                if(x>d[i][j].px&&x<d[i][j].px+d[i][j].r&&y>d[i][j].py&&y<d[i][j].py+d[i][j].r&&/*c%2==0*/d1==null)
                    {
                        d1=d[i][j];
                        //c++;
                        break here;
                    }
                    else if(d1!=null&&x>d1.px&&x<d1.px+d1.r&&y>d1.py&&y<d1.py+d1.r)
                    {
                        d1=null;
                        //c--;
                        break here;
                    }
                    
                else if(x>d[i][j].px&&x<d[i][j].px+d[i][j].r&&y>d[i][j].py&&y<d[i][j].py+d[i][j].r&&/*c%2==1*/d1!=null)
                {
                    if((i==d1.i&&((j-d1.j)==-1||(j-d1.j)==1))||(j==d1.j&&((i-d1.i)==-1||(i-d1.i)==1)))
                    {
                    
                    d2=d[i][j];
                    if(prevL(d1,d2)==false)
                    {
                   // c++;
                    Line l=new Line(d1,d2,pColor[turn]);
                    l.link=Line.head;
                    Line.head=l;
                    d[d1.i][d1.j].li[d[d1.i][d1.j].k++]=l;
                    d[d2.i][d2.j].li[d[d2.i][d2.j].k++]=l;
                    
                    if(!check(d1,d2))
                       {
                           turn++;
                        }
                        else
                        {
                            fillCheck(d1,d2);
                        }
                    if(turn==player.length)
                        turn=0;
                    }
                    else
                    JOptionPane.showMessageDialog(new JFrame(),"ALREADY LINKED!!");
                    d1=null;
                    d2=null;
                    break here;
                
                    }
                }
                
            }
            //else
            //continue;
        }
        //me.consume();
                    repaint();
    }
    public void mouseReleased(MouseEvent me)
    {}
    public void fillCheck(Dot dd1,Dot dd2)
    {
        boolean t1=false,t2=false,t3=false,t4=false,tr=false;//1->2->3->4->clockwise
        Dot min=dd1,mmin=null,dd=null;
        if(dd2.i<min.i||dd2.j<min.j)
        min=dd2;
        if(dd1.i==dd2.i)
        tr=true;
        if(tr)
        {
            if(min.i-1>-1&&name[min.i-1][min.j]==null)
            {
                dd=mmin=d[min.i-1][min.j];
                
            }
            if(min.i<n-1&&name[min.i][min.j]==null)
            {
                dd=mmin=d[min.i][min.j];
            }
        }
        else
        {
            if(min.j-1>-1&&name[min.i][min.j-1]==null)
            {
                dd=mmin=d[min.i][min.j-1];
            }
            if(min.j<n-1&&name[min.i][min.j]==null)
            {
                dd=mmin=d[min.i][min.j];
            } 
        }
        if(dd!=null)
        {
        for(int i=0;i<dd.k;i++)
                {
                    if(dd.li[i].d1==d[dd.i+1][dd.j]||dd.li[i].d2==d[dd.i+1][dd.j])
                    t1=true;
                    if(dd.li[i].d1==d[dd.i][dd.j+1]||dd.li[i].d2==d[dd.i][dd.j+1])
                    t2=true;
                }
                for(int i=0;i<d[dd.i][dd.j+1].k;i++)
                {
                    if(d[dd.i][dd.j+1].li[i].d1==dd||d[dd.i][dd.j+1].li[i].d2==dd)
                    t2=true;
                    if(d[dd.i][dd.j+1].li[i].d1==d[dd.i+1][dd.j+1]||d[dd.i][dd.j+1].li[i].d2==d[dd.i+1][dd.j+1])
                    t3=true;
                }
                for(int i=0;i<d[dd.i+1][dd.j].k;i++)
                {
                    if(d[dd.i+1][dd.j].li[i].d1==dd||d[dd.i+1][dd.j].li[i].d2==dd)
                    t1=true;
                    if(d[dd.i+1][dd.j].li[i].d1==d[dd.i+1][dd.j+1]||d[dd.i+1][dd.j].li[i].d2==d[dd.i+1][dd.j+1])
                    t4=true;
                }
                for(int i=0;i<d[dd.i+1][dd.j+1].k;i++)
                {
                    if(d[dd.i+1][dd.j+1].li[i].d1==d[dd.i][dd.j+1]||d[dd.i+1][dd.j+1].li[i].d2==d[dd.i][dd.j+1])
                    t3=true;
                    if(d[dd.i+1][dd.j+1].li[i].d1==d[dd.i+1][dd.j]||d[dd.i+1][dd.j+1].li[i].d2==d[dd.i+1][dd.j])
                    t4=true;
                }
            }
        int cc=0;
        if(t1)
        cc++;
        if(t2)
        cc++;
        if(t3)
        cc++;
        if(t4)
        cc++;
        
        if(cc==3)
        {
            Dot ddd1=null,ddd2=null;
            if(t1==false)
            {
                ddd1=mmin;
                ddd2=d[mmin.i+1][mmin.j];
            }
                
            if(t2==false)
            {
                ddd1=mmin;
                ddd2=d[mmin.i][mmin.j+1];
            }
            if(t3==false)
            {
                ddd1=d[mmin.i][mmin.j+1];
                ddd2=d[mmin.i+1][mmin.j+1];
            }
            if(t4==false)
            {
                ddd1=d[mmin.i+1][mmin.j];
                ddd2=d[mmin.i+1][mmin.j+1];
            }
            Line l=new Line(ddd1,ddd2,pColor[turn]);
            l.link=Line.head;
                    Line.head=l;
                    d[ddd1.i][ddd1.j].li[d[ddd1.i][ddd1.j].k++]=l;
                    d[ddd2.i][ddd2.j].li[d[ddd2.i][ddd2.j].k++]=l;
            if(name[mmin.i][mmin.j]==null)
                        {
                            bx++;
                            name[mmin.i][mmin.j]=""+player[turn].charAt(0);
                        }
                        
                        fillCheck(ddd1,ddd2);
        }
        if(cc==4)
        {
           if(name[mmin.i][mmin.j]==null)
                        {
                            bx++;
                            name[mmin.i][mmin.j]=""+player[turn].charAt(0);
                        }
        }
        if(bx==n*n-(2*n-1))
                            {  xtr=true;
                                }
    }
    public boolean prevL(Dot dd1,Dot dd2)
    {
        boolean tr=false;
        for(int i=0;i<dd1.k;i++)
        {
            if(dd1.li[i].d1==dd2||dd1.li[i].d2==dd2)
            tr=true;
        }
        return tr;
    }
    public boolean check(Dot dd1,Dot dd2)//functionn to check if box is formed and to assign name to a box
    {
        Dot dd3=null,dd4=null,dd5=null;
        boolean tr=false;
        for(int i=0;i<dd1.k;i++)
        {
            if(dd1.li[i].d1==dd1)
                dd3=dd1.li[i].d2;
            else
                dd3=dd1.li[i].d1;
            if(dd3!=null)
            for(int j=0;j<dd3.k;j++)
            {
                if(!(dd3.li[j].d2==dd1||dd3.li[j].d1==dd1))
                if(dd3.li[j].d1==dd3)
                    dd4=dd3.li[j].d2;
                else
                    dd4=dd3.li[j].d1;
                if(dd4!=null)
                for(int k=0;k<dd4.k;k++)
                {
                    if(!(dd4.li[k].d2==dd3||dd4.li[k].d1==dd3))
                    if(dd4.li[k].d1==dd4)
                        dd5=dd4.li[k].d2;
                    else
                        dd5=dd4.li[k].d1;
                    if(dd5!=null&&dd5.i==dd2.i&&dd5.j==dd2.j)
                    {
                        Dot min=dd1;
                        if(dd2.i<min.i||dd2.j<min.j)
                        min=dd2;
                        if(dd3.i<min.i||dd3.j<min.j)
                        min=dd3;
                        if(dd4.i<min.i||dd4.j<min.j)
                        min=dd4;
                        if(name[min.i][min.j]==null)
                        {
                            bx++;
                            name[min.i][min.j]=""+player[turn].charAt(0);
                            
                            
                        }
                        tr=true;
                    }
                }
            }
            dd3=dd4=dd5=null;
        }
                            if(bx==n*n-(2*n-1))
                            {
                                /*int cc[]=new int[player.length];
                                for(int i=0;i<n-1;i++)
                                {
                                    for(int j=0;j<n-1;j++)
                                    {
                            
                                        for(int k=0;k<player.length;k++)
                                        if(name[i][j]!=null&&name[i][j].equals(""+player[k].charAt(0)))
                                        {
                                        cc[k]=cc[k]+1;
                                        }
                                     }
                                    }
                                    String s="";
                                    for(int i=0;i<player.length;i++)
                                        s=s+player[i]+"="+cc[i]+"\n";
                                        JOptionPane.showMessageDialog(new JFrame(),s);
                                        JOptionPane.showMessageDialog(new JFrame(),"GAME OVER :)");*/
                                    xtr=true;
                                }
        return tr;
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setBackground(Color.black);
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(d1!=null)
                g.setColor(Color.gray);
                else
                g.setColor(d[i][j].cl);
                if(d1!=null&&((i==d1.i&&((j-d1.j)==-1||(j-d1.j)==1))||(j==d1.j&&((i-d1.i)==-1||(i-d1.i)==1))))
                g.setColor(Color.pink);
                g.fillArc(d[i][j].px,d[i][j].py,d[i][j].r,d[i][j].r,0,360);
            }
        }
        Line ptr=Line.head;
        while(ptr!=null)
        {
            g.setColor(ptr.cl);
            g.drawLine(ptr.d1.px,ptr.d1.py,ptr.d2.px,ptr.d2.py);
            ptr=ptr.link;
        }
        g.setColor(Color.white);
        for(int i=0;i<n-1;i++)
        {
            for(int j=0;j<n-1;j++)
            {
                if(name[i][j]!=null)
                g.drawString(name[i][j],d[i][j].px+dx/2,d[i][j].py+dy/2);
            }
        }
    }
}