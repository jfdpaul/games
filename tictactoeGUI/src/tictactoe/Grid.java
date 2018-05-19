package tictactoe;

/**
 * Created by Jonathan Fidelis Paul on 12/3/2015.
 */
import helpers.IJ;
public class Grid {
    public int rows,cols;
    public Symbols mat[][];            //matrix

    /**Constructor Definition*/
    public Grid() {
        rows = 3;
        cols = 3;
    }
    public Grid(int r,int c) {
        rows = r;
        cols = c;
    }

    /**Setup function to create required objects*/
    public void setup()                 //call setup to reset all attributes after changing their values.
    {
        mat=new Symbols[rows][cols];
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
                mat[i][j]=Symbols.EMPTY;
        }
    }

    /**Setter Methods*/
    public void setSymbol(IJ ind, Symbols sym)
    {
        this.mat[ind.i][ind.j]=sym;
    }

    /**Getter Methods*/
    public Symbols getSymbol(IJ ind)
    {
        return this.mat[ind.i][ind.j];
    }

    public Symbols[][] getCopyMat(){
        Symbols m[][]=new Symbols[rows][cols];
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
            {
                m[i][j]=mat[i][j];
            }
        }
        return m;
    }
}