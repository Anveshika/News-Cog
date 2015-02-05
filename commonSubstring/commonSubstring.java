import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;

public class commonSubstring {
    
     public static void main(String[] args) throws IOException
        {    
         
         //arraylist for storing the final list of common strings in the two articles
            ArrayList<String> finalart = new ArrayList<String>();           
            
            commonSubstring obj = new commonSubstring();
            
            System.out.println("Longest Common Substring Algorithm Test\n");
            Scanner in=new Scanner(System.in);
            
            //input first very large string
            System.out.println("\nEnter string 1");
            String str1 ="";
            
            while(in.hasNextLine())
            {
                String str=in.nextLine();
                
              //string input terminates at finding "end" string
                if(str.equals("end"))
                    break;
                str1=str1+str;
            }
            str1 = str1.toLowerCase();   
            System.out.println(str1);
     
            //input second very large string
            System.out.println("\nEnter string 2");
            String str2 ="";
            while(in.hasNextLine())
            {
                String str="";
                str=in.nextLine();
                
                //string input terminates at finding "end" string
                if(str.equals("end"))
                    break;
                str2=str2+str;
            }
            str2 = str2.toLowerCase();
            in.close();
            
            //class for generating the suffix arrays in sorted order 
            SuffixArray suffixarray1 = new SuffixArray(str1);
            suffixarray1.createSuffixArray();   
            
            //storing the generated suffix array into a 2 dimensional array along with other details
            String suffix1[][]=obj.store(suffixarray1.getsuffix(), suffixarray1.getindex(),"1");
            
            //processing for second article
            SuffixArray suffixarray2 = new SuffixArray(str2);
            suffixarray2.createSuffixArray();
            String suffix2[][]=obj.store(suffixarray2.getsuffix(), suffixarray2.getindex(),"2");
            
            //merging the two lists of suffix arrays of two input large articles
            String suffix[][]=obj.merge(suffix1, suffix2);
            
            //sorting merged suffix array list
            Arrays.sort(suffix, new ColumnComparator(0));              
            
            
            //calling function to compare the strings only if the two strings are from different documents
            for(int j = 0; j<suffix.length-1; j++)
            {
                if(!suffix[j][2].equals(suffix[j+1][2]))
                {
                    String s = obj.compare(suffix[j][0], suffix[j+1][0]);
                    
                    if(!s.equals(""))
                        finalart.add(s);
                }           
                
            }
            
            //displaying the common substrings generated
            for(int i=0;i<finalart.size();i++)
            {
                System.out.println();
               System.out.println(finalart.get(i));
            }
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
     
     //storing the suffix arrays of one article along with details like doc number, 
     //length and index into a 2 dimensional array
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
     
     //function to compare two small string and give the longest common substring
     public String compare(String S1, String S2)
     {
         int Start = 0;
         int Max = 0;
        
         for (int i = 0; i < S1.length(); i++)
         {
             for (int j = 0; j < S2.length(); j++)
             {
                 int x = 0;
                 
                 while (S1.charAt(i + x) == S2.charAt(j + x))
                 {                  
                     x++;
                     if (((i + x) >= S1.length()) || ((j + x) >= S2.length())) break;
                 }             
               
                 if (x > Max)
                 {
                     Max = x;
                     Start = i;                  
                 }             
              }
         }
         return S1.substring(Start, (Start + Max));
     }


}

class ColumnComparator implements Comparator 
{
    int columnToSort;
    ColumnComparator(int columnToSort) 
    {
        this.columnToSort = columnToSort;
    }
    
    //overriding compare method
    public int compare(Object o1, Object o2) 
    {
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
      
    }
    
    public String[] getsuffix()
    {
        return suffix;
    }
    
    public int[] getindex()
    {
        return index;
    } 
    
}


