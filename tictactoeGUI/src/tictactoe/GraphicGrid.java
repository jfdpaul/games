package tictactoe;

import helpers.IJ;
import java.awt.*;

/**
 * Created by Jonathan Fidelis Paul on 12/4/2015.
 */
public class GraphicGrid extends Grid {

    private int dx,dy;              //size between each cell
    private int W,H;                //total width and height
    private int marginX,marginY;   //stores distance from top and left side
    private Color colorMat[][];

    class XY                       //Nested Class
    {
        int x,y;
    }

    public XY coord[][];

    public GraphicGrid()
    {
        super();

        marginX=0;
        marginY=0;

        dx = 50;
        dy = 50;
    }

    public GraphicGrid(int r,int c)     //Overloaded constructor for adding rows and columns
    {
        super(r,c);

        marginX=0;
        marginY=0;

        dx = 50;
        dy = 50;
    }

    @Override
    public void setup()                 //call setup to reset all attributes after changing their values.
    {
        super.setup();

        W = dx * cols;
        H = dy * rows;

        coord=new XY[rows][cols];
        colorMat=new Color[rows][cols];

        for(int i=0;i<rows;i++) {
            for (int j = 0; j < cols; j++) {
                coord[i][j] = new XY();
                coord[i][j].x = marginX + j * dx;
                coord[i][j].y = marginY + i * dy;

                colorMat[i][j]=new Color(0,0,0);
                if((i+j)%2==0)
                    colorMat[i][j]=Color.WHITE;
                else
                    colorMat[i][j]=Color.DARK_GRAY;

            }
        }
    }

    /**Setter Methods*/
    public void setMargin(int mx,int my)
    {
        marginX=mx;
        marginY=my;
    }
    public void setDxy(int dix,int diy)
    {
        this.dx=dix;
        this.dy=diy;
    }
    public void setMargin()              /*Method Overloading*/
    {
        marginX=0;
        marginY=0;
    }
    public void setDxy()                /*Method Overloading*/
    {
        this.dx=0;
        this.dy=0;
    }

    /**Getter Methods*/
    public IJ getIndices(int x,int y) {
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
            {
                if(x<(marginX+dx*(j+1))&&x>(marginX+dx*j)&&y<(marginY+dy*(i+1))&&y>(marginY+dy*i))
                {
                    return new IJ(i,j);
                }
            }
        }
        return null;
    }
    public Color getColorMat(IJ ind){return colorMat[ind.i][ind.j];}
    public int getCellCoordX(int i,int j){return this.coord[i][j].x;}
    public int getCellCoordY(int i,int j)
    {
        return this.coord[i][j].y;
    }
    public int getCellWidth()
    {
        return dx;
    }
    public int getCellHeight()
    {
        return dy;
    }
}