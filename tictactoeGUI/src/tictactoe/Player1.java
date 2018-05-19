package tictactoe;

import helpers.IJ;
import helpers.Player;

/**
 * Created by Jonathan Fidelis Paul on 12/4/2015.
 */
public class Player1 extends Player {

    public Player1(Symbols s)
    {
        super(s);
        name="Player1";
        moves=0;
    }

    @Override
    public IJ getIndex(Symbols mat[][])
    {
        for(int i=0;i<mat.length;i++)
        {
            for(int j=0;j<mat[0].length;j++)
            {
                //if(mat[i][j]==Symbols.EMPTY)
                {
                    return new IJ(i,j);
                }
            }
        }
        return null;
    }
}