import java.awt.event.*;
import java.awt.*;
class Grid
{
    int g[][];
    int li;
    int lj;
    int no_checked;
    int max;
    boolean big;
    String code;
    String winner;
     Grid()
    {
        g=new int[3][3];
        li=-1;
        lj=-1;
        no_checked=0;
        max=9;
        winner="";
    }
    Grid(String bg,String cd)
    {
        g=new int[3][3];
        li=-1;
        lj=-1;
        no_checked=0;
        max=9;
        if (bg.equals("big"))
            big=true;
        else
            big=false;
        code=cd;
        winner="";
    }
    void setCode(String cd)
    {
        code=cd;
    }
    void setType(String bg)
    {
        if (bg.equals("big"))
            big=true;
        else
            big=false;
    }
    void setWinner(String w)
    {
        winner=w;
    }
    void setMove(int u,int i,int j)
    {
        g[i][j]=u;
    }
    
    boolean isPresent(int i,int j)
    {   
        if(g[i][j]==0)
        return false;
        else
        return true;
    }
    boolean checkWin(int u)
    {
        boolean mTr=false;
        boolean tr;
        int d1=0;
        int d2=0;
        for (int i=0;i<3;i++)
        {
            int col=0;
            int row=0;
            for(int j=0;j<3;j++)
            {
                if(g[i][j]==u)
                {
                    row=row+u;
                }                   
                if(g[j][i]==u)
                {
                    col=col+u;
                }
                if(i==j)
                {
                    if(g[i][j]==u)
                    {
                        d1=d1+u;
                    }
                }
                if((i+j)==2)
                {
                    if(g[i][j]==u)
                    {
                        d2=d2+u;
                    }
                }
            }
            if (row==3*u||col==3*u)
            {
                mTr=true;
                break;
            }
        }
        if(d1==3*u||d2==3*u)
        {
            mTr=true;
        }
        return mTr;
    }
}