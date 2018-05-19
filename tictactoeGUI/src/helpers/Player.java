package helpers;

import tictactoe.Symbols;

/**
 * Created by Jonathan Fidelis Paul on 12/3/2015.
 */
public abstract class Player {
    protected String name;
    protected int moves;
    public Symbols sym;

    public Player(Symbols s){
        sym=s;
    }

    public abstract IJ getIndex(Symbols mat[][]);

}
