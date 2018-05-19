package tictactoe;

import helpers.IJ;
import helpers.Player;

import java.util.Random;

/**
 * Created by Jonathan Fidelis Paul on 12/5/2015.
 */
public class Player2 extends Player {

    public Player2(Symbols s)
    {
        super(s);
        name="Player2";
        moves=0;
    }

    public final IJ getIndex(Symbols mat[][])
    {
        while(true)
        {
            IJ ind=new IJ((new Random().nextInt(3)),(new Random().nextInt(3)));
            if(checkEmpty(mat,ind))
                return ind;
        }
    }
    private boolean checkEmpty(Symbols mat[][],IJ ind)
    {
        if(mat[ind.i][ind.j]==Symbols.EMPTY)
            return true;
        else
            return false;
    }
}
