package helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Jonathan Fidelis Paul on 12/5/2015.
 * An abstract class for Game Panel
 */

public abstract class Game extends JPanel implements Runnable,MouseListener,MouseMotionListener{

    public class DebugWindow extends Thread
    {
        boolean bRun=true;
        public JFrame dFrame;
        protected JTextArea tArea;
        protected JScrollPane scrollPane;
        private String msg="";

        public DebugWindow(String name)
        {
            dFrame=new JFrame(name);
            dFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            dFrame.setSize(width/2,height/2);               //sets the frame size
            dFrame.setLocation(width/2,10);
            //dFrame.setVisible(true);

            tArea=new JTextArea();
            tArea.setAutoscrolls(true);
            scrollPane=new JScrollPane(tArea);
            scrollPane.setVisible(true);
            dFrame.add(scrollPane);
        }

        public void run()
        {
            while(bRun) {
                tArea.append(msg);
                this.suspend();
            }
        }

        public void start()
        {
            super.start();
        }
        public void stopThread()
        {
            bRun=false;
        }
        public void resumeThread()
        {
            this.resume();
        }
        public void setFrameVisible(boolean tr)
        {
            dFrame.setVisible(tr);
        }
        public void setMsg(String s)
        {
            msg=s;
        }
    }

    protected int height,width;           //Height and Width of the Frame
    protected Thread thread;              //Thread for game
    protected JFrame frame;               //Application Frame
    protected DebugWindow debug;

    protected int turn;
    protected int maxMoves;

    public Game(String gameName)
    {
        height=(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        width=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        debug=new DebugWindow("Debug");
        debug.start();

        frame=new JFrame(gameName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width/2,height/2);                 //sets the frame size
        frame.setVisible(true);
        frame.setLocation(10,10);
    }

    public abstract void run();

    public void start()
    {
        if(thread==null)
            thread=new Thread(this,"TicTacToe");
        try{
            thread.start();                    //starts game
        }
        catch(Exception e)
        {

        }
    }

}