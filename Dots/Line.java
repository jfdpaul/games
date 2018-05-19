import javax.swing.*;
import java.awt.*;

class Line
{
    static Line head;
    Dot d1,d2;
    Color cl;
    Line link;
    public Line(Dot d1,Dot d2,Color c)
    {
        this.d1=d1;
        this.d2=d2;
        cl=c;
        link=null;
    }
}