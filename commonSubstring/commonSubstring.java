import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class commonSubstring {
    
     public static void main(String[] args) throws IOException
        {    
         
            commonSubstring obj = new commonSubstring();
            System.out.println("Longest Common Substring Algorithm Test\n");
            Scanner in=new Scanner(System.in);
            
            System.out.println("\nEnter string 1");
            String str1 ="";
            while(in.hasNextLine())
            {
                String str=in.nextLine();
                if(str.equals("end"))
                    break;
                str1=str1+str;
            }
            str1.toLowerCase();
            //System.out.println(str1);
     
            System.out.println("\nEnter string 2");
            String str2 ="";
            while(in.hasNextLine())
            {
                String str="";
                str=in.nextLine();
                if(str.equals("end"))
                    break;
                str2=str2+str;
            }
            str2.toLowerCase();
            in.close();
            SuffixArray suffixarray1 = new SuffixArray(str1);
            suffixarray1.createSuffixArray();           
            String suffix1[][]=obj.store(suffixarray1.getsuffix(), suffixarray1.getindex(),"1");
            
            SuffixArray suffixarray2 = new SuffixArray(str2);
            suffixarray2.createSuffixArray();
            String suffix2[][]=obj.store(suffixarray2.getsuffix(), suffixarray2.getindex(),"2");
            
            String suffix[][]=obj.merge(suffix1, suffix2);
            
            Arrays.sort(suffix, new ColumnComparator(0));           
            for(int i=0;i<suffix.length;i++)
            {
                System.out.println();
                for(int j=0;j<4;j++)
                {
                    System.out.print(suffix[i][j]+" ");
                }
            }
            
            //LongestSubstring obj = new LongestSubstring(); 
            //String result = bstring(str1, str2);
     
            //System.out.println("\nLongest Common Substring : "+ result);
        }
     public String[][] merge(String art1[][], String art2[][])
        {
            String article[][]=new String[art1.length+art2.length][4];
            int i=0;
            for(i=0;i<art1.length;i++)
            {
                for(int j=0;j<4;j++)
                {
                    article[i][j]=art1[i][j];
                }
            }
            for(int k=0;k<art2.length;k++)
            {
                for(int j=0;j<4;j++)
                {
                    article[i][j]=art2[k][j];                    
                }
                i++;
            }
            return article;     
        }
     
     public String[][] store(String[] suffixarr, int[] index, String doc)
     {
         String[][] arr = new String[suffixarr.length][4];
         
         for(int i=0;i<suffixarr.length;i++)
         {
             arr[i][0] = suffixarr[i];          
             arr[i][1] = ""+ index[i];
             arr[i][2] = ""+ doc;
             arr[i][3] = ""+ suffixarr[i].length();          
         }
         return arr;
     }   

}
class ColumnComparator implements Comparator {
    int columnToSort;
    ColumnComparator(int columnToSort) {
        this.columnToSort = columnToSort;
    }
    //overriding compare method
    public int compare(Object o1, Object o2) {
        String[] row1 = (String[]) o1;
        String[] row2 = (String[]) o2;
        //compare the columns to sort
        return row1[columnToSort].compareTo(row2[columnToSort]);
    }
}
class SuffixArray
{
    private String[] text;
    private int length;
    private int[] index;
    private String[] suffix;
 
    public SuffixArray(String text)
    {
        this.text = new String[text.length()]; 
 
        for (int i = 0; i < text.length(); i++)
        {
            this.text[i] = text.substring(i, i+1);
        } 
 
        this.length = text.length();
        this.index = new int[length];
        for (int i = 0; i < length; i++)
        {
            index[i] = i;
        }   
 
        suffix = new String[length];
    }
 
    public void createSuffixArray()
    {   
        for(int index = 0; index < length; index++) 
        {
            String text = "";
            for (int text_index = index; text_index < length; text_index++)
            {
                text+=this.text[text_index];
            } 
            suffix[index] = text;
        }
 
        int back;
        for (int iteration = 1; iteration < length; iteration++)
        {
            String key = suffix[iteration];
            int keyindex = index[iteration];
 
            for (back = iteration - 1; back >= 0; back--)
            {
                if (suffix[back].compareTo(key) > 0)
                {
                    suffix[back + 1] = suffix[back];
                    index[back + 1] = index[back];
                }
                else
                {
                    break;
                }
            }
            suffix[ back + 1 ] = key;
            index[back + 1 ] = keyindex;
        }
        
       // return suffix;
 
       /* System.out.println("SUFFIX \t INDEX");
        for (int iterate = 0; iterate < length; iterate++)
        {       
            System.out.println(suffix[iterate] + "\t" + index[iterate]);
        }*/
    }
    
    public String[] getsuffix()
    {
        return suffix;
    }
    
    public int[] getindex()
    {
        return index;
    }
 
 
    /*public static void main(String...arg)throws IOException
    {
        String text = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the Text String ");
        text = reader.readLine();
 
        SuffixArray suffixarray = new SuffixArray(text);
        suffixarray.createSuffixArray();
    }   */
}
