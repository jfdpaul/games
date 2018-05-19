class Grid
{
    int g[][];
    int players;
    int N;
    Grid(int size,int p)
    {
        players=p;
        N=size;
        g=new int[N][N];
    }
    boolean setMove(int u,int i,int j)
    {
        if(!isPresent(i,j))
        {
            g[i][j]=u;
            
            return true;
        }
        return false;
    }
    boolean isPresent(int i,int j)
    {
        if(g[i][j]==0)
            return false;
        else
            return true;
    }
    boolean check_win(int u,int WN)
    {
        int hor=0;
        int ver=0;
        int col1=0;
        int col2=0;
        boolean win =false;
        here:for(int i=0;i<N;i++)
        {
            hor=0;
            ver=0;
            for(int j=0;j<N;j++)
            {
                if(g[i][j]==u)
                hor++;
                else
                hor=0;
                if(g[j][i]==u)
                ver++;
                else
                ver=0;
                if(hor==WN||ver==WN)
                {
                    win=true;
                    break here;
                }
            }
        }
        if(!win)
        here:for(int i=0;i<N;i++)
        {
            col1=0;
            for(int j=0;j<N;j++)
            {
                if(j+i<N)
                if(g[j][j+i]==u)
                col1++;
                else
                col1=0;
                if(col1==WN)
                {
                    win=true;
                    break here;
                }
            }
        }
         if(!win)
        here:for(int i=0;i<N;i++)
        {
            col1=0;
            for(int j=0;j<N;j++)
            {
                if(j+i<N)
                if(g[j+i][j]==u)
                col1++;
                else
                col1=0;
                if(col1==WN)
                {
                    win=true;
                    break here;
                }
            }
        }
        if(!win)
        here:for(int i=0;i<N;i++)
        {
            col2=0;
            for(int j=N-1;j>=0;j--)
            {
                if(N-1-j-i>=0)
                if(g[j][N-1-j-i]==u)
                col2++;
                else
                col2=0;
                if(col2==WN)
                {
                    win=true;
                    break here;
                }
            }
        }
        if(!win)
        here:for(int i=0;i<N;i++)
        {
            col2=0;
            for(int j=N-1;j>=0;j--)
            {
                if((N-1-j+i>=0)&&(N-1-j+i<N))
                if(g[j][N-1-j+i]==u)
                col2++;
                else
                col2=0;
                if(col2==WN)
                {
                    win=true;
                    break here;
                }
            }
        }
        return win;
    }

}