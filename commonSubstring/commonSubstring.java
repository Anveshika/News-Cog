import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class commonSubstring {
	
	 public static void main(String[] args) throws IOException
	    {    
		 
		 	commonSubstring obj = new commonSubstring();
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        System.out.println("Longest Common Substring Algorithm Test\n");
	 
	        System.out.println("\nEnter string 1");
	        String str1 = br.readLine();
	 
	        System.out.println("\nEnter string 2");
	        String str2 = br.readLine();
	        
	        SuffixArray suffixarray1 = new SuffixArray(str1);
	        suffixarray1.createSuffixArray();	        
	        obj.store(suffixarray1.getsuffix(), suffixarray1.getindex(),"1", str1);
	        
	        SuffixArray suffixarray2 = new SuffixArray(str1);
	        suffixarray2.createSuffixArray();
	        obj.store(suffixarray2.getsuffix(), suffixarray2.getindex(),"2", str2);
	        
	        //LongestSubstring obj = new LongestSubstring(); 
	        //String result = bstring(str1, str2);
	 
	        //System.out.println("\nLongest Common Substring : "+ result);
	    }
	 
	 public void store(String[] suffixarr, int[] index, String doc, String str)
	 {
		 String[][] arr = new String[suffixarr.length][4];
		 
		 for(int i=0;i<suffixarr.length;i++)
		 {
			 arr[i][0] = suffixarr[i];			
			 arr[i][1] = ""+ index[i];
			 arr[i][2] = ""+ doc;
			 arr[i][3] = ""+ suffixarr[i].length();			 
		 }			 
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
    }	*/
}
