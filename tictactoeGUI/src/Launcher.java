import tictactoe.TicTacToe;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

/**
 * Created by Jonathan Fidelis Paul on 12/9/2015.
 */
public class Launcher {

    static JFrame frame;
    static JMenuBar bar;
    static JMenu menu;
    static JMenuItem menuItem;
    private final static String LOG_NAME="tictactoeLog.log";

    static {
        bar = new JMenuBar();
        menu = new JMenu("Games");
        menuItem = new JMenuItem("TtT");
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                new TicTacToe();
            }
        });
        bar.add(menu);
        menu.add(menuItem);

        menu = new JMenu("Log");
        menuItem = new JMenuItem("View");
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                JTextArea tarea=new JTextArea();
                //BufferedReader br=null;
                FileReader br=null;
                try {
                    /*String s;
                    br=new BufferedReader(new FileReader(LOG_NAME));
                    while((s=br.readLine())!=null)
                    {
                        tarea.append(s);
                        tarea.append("\n");
                    }
                    */
                    int s;
                    br=new FileReader(LOG_NAME);
                    while((s=br.read())!=-1)
                    {
                        tarea.append(""+(char)s);
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                finally{
                    if(br!=null)
                        try {
                            br.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                }
                JFrame f=new JFrame("LOG");
                f.setVisible(true);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.pack();
                f.add(tarea);
            }
        });

        bar.add(menu);
        menu.add(menuItem);
        frame = new JFrame("Game Launcher");
    }

    public Launcher()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setJMenuBar(bar);
        frame.setVisible(true);
    }

    public static void main(String[]args)
    {
        new Launcher();
    }
}
