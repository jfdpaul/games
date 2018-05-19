package tictactoe;

/**
 * Created by Jonathan Fidelis Paul on 12/3/2015.
 */

import javax.swing.*;

import helpers.IJ;
import helpers.Player;
import helpers.Game;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Date;

public final class TicTacToe extends Game {

    private GraphicGrid grid;       //Grid object
    private JButton button;         //Next Button
    private JButton toggle;         //Debug Button
    private JButton start_button;

    private enum PlayerType{
        USER,
        COMPUTER
    }

    IJ ind=null;
    boolean won=false;
    static boolean validMove=true;
    private PlayerType player[];
    private Player pt;

    private final static String LOG_NAME="tictactoeLog.log";

    private static class BadFunctionException extends Throwable
    {
        public BadFunctionException(String err_msg)
        {
            err_msg=err_msg+" "+new Date().toString();
            try {
                /*
                BufferedWriter out = new BufferedWriter(new FileWriter(LOG_NAME,true));
                out.write(err_msg);
                out.close();
                */
                FileWriter out= new FileWriter(LOG_NAME);
                out.write(err_msg);
                validMove=false;
            }
            catch (IOException e) {
            System.out.println("exception occoured"+ e);
        }
        }
    }
    /**Constructor Definition*/
    public TicTacToe(){

        super("TicTacToe");
        player=new PlayerType[2];

        getUserChoice();

        maxMoves=9;
        turn=0;

        grid=new GraphicGrid();
        grid.setMargin(super.frame.getWidth()/10,super.frame.getHeight()/10);       //sets the margin width
        grid.setDxy(100,100);                               //sets the cell size
        grid.setup();                                       //initializing the members

        button=new JButton("NEXT");
        button.setSize(10,10);
        button.setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeGame();
            }
        });

        start_button=new JButton("START");
        start_button.setSize(10,10);
        start_button.setVisible(true);
        start_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });

        toggle=new JButton("DEBUG");
        toggle.setSize(10,10);
        toggle.setVisible(true);
        toggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleViewDebugWindow();
            }
        });

        this.add(start_button);
        this.add(toggle);
        this.add(button);
        super.frame.add(this);          //adds the drawing panel to the frame

        addMouseListener(this);
    }

    /**OVERRIDDEN FUNCTIONS*/
    @Override
    public void run()
    {
        final int missLimit=3;

        Player p1=new Player1(Symbols.CROSS);
        Player p2=new Player2(Symbols.KNOT);

        //GAME LOOP
        for(int gm=0;gm<maxMoves&&!won;gm++)
        {
            int miss=0;

            if(turn%2==0){
                pt = p2;
            }
            else{
                pt=p1;
            }

            /*PLAY MOVE*/
            while(validMove)
            {
                if(player[turn%2]==PlayerType.USER)
                    this.pauseGame();
                else {
                    Symbols m[][] = grid.getCopyMat();
                    ind = pt.getIndex(m);                  //calls Players play() logic
                }

                if(checkValidMove(ind))
                {
                    grid.setSymbol(ind,pt.sym);
                    break;
                }
                else
                {
                    miss++;
                    if(miss>missLimit)
                    {
                        //report error in code design
                        try {
                            throw new BadFunctionException("\nPlayer"+(turn%2)+" crossed error limit");
                        } catch (BadFunctionException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                    continue;
                }
            }
            if(validMove==false)
                break;
            repaint();
            //CHECK FOR WIN
            if(hasWon(ind))
            {
                JOptionPane.showMessageDialog(super.frame,"Player "+pt.sym+" has Won");
                won=true;
            }

            /*
            try{
                thread.sleep(100);
            }
            catch(InterruptedException e){
                System.out.println("Error Occurred");
                e.printStackTrace();
            }*/

            turn++;

            debugPrintMatrix();

            super.debug.resumeThread();
            //this.pauseGame();
        }
        repaint();
        if(won==false)
            JOptionPane.showMessageDialog(super.frame,"No one has Won");
        this.stopGame();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();

        ind=grid.getIndices(x,y);
        this.resumeGame();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        for(int i=0;i<grid.rows;i++)
        {
            for(int j=0;j<grid.cols;j++)
            {
                g.setColor(grid.getColorMat(new IJ(i,j)));
                g.fillRect(grid.getCellCoordX(i,j),grid.getCellCoordY(i,j),grid.getCellWidth(),grid.getCellHeight());

                g.setColor(Color.RED);
                g.setFont(new Font("TimesRoman",Font.PLAIN,grid.getCellHeight()));
                if(grid.mat[i][j]==Symbols.CROSS)
                    g.drawString("X",grid.getCellCoordX(i,j),+grid.getCellHeight()+grid.getCellCoordY(i,j));
                if(grid.mat[i][j]==Symbols.KNOT)
                    g.drawString("O",grid.getCellCoordX(i,j),grid.getCellHeight()+grid.getCellCoordY(i,j));
            }
        }
    }

    /**Helper Functions*/
    private void toggleViewDebugWindow() {
        if(super.debug.dFrame.isVisible())
            super.debug.setFrameVisible(false);
        else
            super.debug.setFrameVisible(true);
    }

    private void debugPrintMatrix() {
        String str="Matrix Contents\n";
        for(int i=0;i<grid.rows;i++)
        {
            for(int j=0;j<grid.cols;j++)
            {
                str=str+grid.getSymbol(new IJ(i,j))+"  ";
            }
            str=str+"\n";
        }
        super.debug.setMsg(str);
    }

    private boolean hasWon(IJ ind) {
        int point=1;
        int i,j;

        //SOUTH motion
        for(i=ind.i+1;i<grid.rows;i++)
        {
            if(grid.getSymbol(ind)==grid.getSymbol(new IJ(i,ind.j)))
                point++;
            else
                break;
        }
        //NORTH motion
        for(i=ind.i-1;i>=0;i--)
        {
            if(grid.getSymbol(ind)==grid.getSymbol(new IJ(i,ind.j)))
                point++;
            else
                break;
        }
        if(point==3)
            return true;

        point=1;
        //SOUTH-EAST motion
        for(i=ind.i+1,j=ind.j+1;i<grid.rows&&j<grid.cols;i++,j++)
        {
            if(grid.getSymbol(ind)==grid.getSymbol(new IJ(i,j)))
                point++;
            else
                break;
        }

        //NORTH-WEST motion
        for(i=ind.i-1,j=ind.j-1;i>=0&&j>=0;i--,j--)
        {
            if(grid.getSymbol(ind)==grid.getSymbol(new IJ(i,j)))
                point++;
            else
                break;
        }
        if(point==3)
            return true;

        point=1;
        //NORTH-EAST motion
        for(i=ind.i-1,j=ind.j+1;i>=0&&j<grid.cols;i--,j++)
        {
            if(grid.getSymbol(ind)==grid.getSymbol(new IJ(i,j)))
                point++;
            else
                break;
        }
        //SOUTH-WEST motion
        for(i=ind.i+1,j=ind.j-1;i<grid.rows&&j>=0;i++,j--)
        {
            if(grid.getSymbol(ind)==grid.getSymbol(new IJ(i,j)))
                point++;
            else
                break;
        }
        if(point==3)
            return true;

        point=1;
        //EAST motion
        for(j=ind.j+1;j<grid.cols;j++)
        {
            if(grid.getSymbol(ind)==grid.getSymbol(new IJ(ind.i,j)))
                point++;
            else
                break;
        }
        //EAST motion
        for(j=ind.j-1;j>=0;j--)
        {
            if(grid.getSymbol(ind)==grid.getSymbol(new IJ(ind.i,j)))
                point++;
            else
                break;
        }
        if(point==3)
            return true;

        return false;
    }

    public final boolean checkValidMove(IJ ind)
    {
        if(grid.getSymbol(ind)==Symbols.EMPTY)
            return true;
        else
            return false;
    }

    private void pauseGame()throws IllegalThreadStateException
    {
        thread.suspend();
    }

    private void stopGame()throws IllegalThreadStateException
    {
        super.debug.stopThread();
    }

    private void resumeGame()throws IllegalThreadStateException
    {
        thread.resume();
    }

    private void getUserChoice()
    {
        String choice[]={"C vs C","C vs U","U vs U"};
        String op=(String)JOptionPane.showInputDialog(super.frame,"Make A Choice","",JOptionPane.PLAIN_MESSAGE,null,choice,"C vs U");

        switch(op)
        {
            case "C vs C":
                player[0]=PlayerType.COMPUTER;
                player[1]=PlayerType.COMPUTER;
                break;
            case "C vs U":
                player[0]=PlayerType.COMPUTER;
                player[1]=PlayerType.USER;
                break;
            case "U vs U":
                player[0]=PlayerType.USER;
                player[1]=PlayerType.USER;
                break;
            default:
        }
    }
}