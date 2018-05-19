import java.io.*;
class find
{
    public static void main(String[]args)
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int n=0;
        while(n<10000)
        {
            int nn=n*4;
            int rn=rev(nn);
            if(n==rn)
            System.out.println(nn+"\n");
            n++;
        }
    }
    public static int rev(int n)
    {
        String s=""+n;
        String rs="";
        for(int i=0;i<s.length();i++)
        {
            rs=s.charAt(i)+rs;
        }
        int nn=Integer.parseInt(rs);
        return nn;
    }
}