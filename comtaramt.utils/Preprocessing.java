import java.io.*;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.*;
class Preprocessing
{
	public static void main(String[] args)
	{
		Preprocessing obj=new Preprocessing();
		Hashtable<String,int[]> h=new Hashtable<String,int[]>();
		Hashtable<String, String> swords=obj.getStopWords();
		String art1=obj.getFileData("Article 1.txt");
		String art2=obj.getFileData("Article 2.txt");
		art1=obj.doPreprocessing(art1, swords);
		art2=obj.doPreprocessing(art2, swords);
		dictionary(h,art1,art2);
		 System.out.println(similarity(h));
	}
	public String doPreprocessing(String art,Hashtable<String, String> swords)
	{
		String result="";
		String arr[]=art.split(" ");
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i].length()>1)
			{
				int ascii=(int)arr[i].charAt(arr[i].length()-1);
				while(!((ascii>=48 && ascii<=57) || (ascii>=65 && ascii<=90) || (ascii>=97 && ascii<=122)))
				{
					arr[i]=arr[i].substring(0,arr[i].length()-1);
					ascii=(int)arr[i].charAt(arr[i].length()-1);
				}
				String c=arr[i].toLowerCase();
				if((int)arr[i].charAt(0)==8220)
					arr[i]=arr[i].substring(1);
				if(!swords.containsKey(c))
				{
					result=result+" "+arr[i];
				}
			}
		}
		return result;
	}
	public String getFileData(String name)
	{
		String data="";
		try 
		{
			Scanner in=new Scanner(new File(name));
			while(in.hasNext())
			{
				data=data+" "+in.next();
			}
			in.close();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		return data;
	}
	public Hashtable<String, String> getStopWords()
	{
		Hashtable<String, String> swords=new Hashtable<>();
		try 
		{
			Scanner in=new Scanner(new File("stopwords.txt"));
			while(in.hasNextLine())
			{
				swords.put(in.nextLine(), "");
			}
			in.close();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		return swords;
	}
	
	public static void dictionary(Hashtable<String,int[]> h,String s1, String s2)
	{
		//Hashtable<String,int[]> h=new Hashtable<String,int[]>();
		
		String[] s3=s1.split(" ");
		String[] s4=s2.split(" ");
				for(int i=0;i<s3.length;i++)
				{
					int[] a=new int[2];
					if(!h.containsKey(s3[i]))
					{
					 a[0]=1;
					 h.put(s3[i], a);
					}
					else
					{
						a=h.get(s3[i]);
						a[0]++;
						h.put(s3[i], a);
					}
				}
				for(int i=0;i<s4.length;i++)
				{
					int[] a=new int[2];
					if(!h.containsKey(s4[i]))
					{
					 a[1]=1;
					 h.put(s4[i], a);
					}
					else
					{
						a=h.get(s4[i]);
						a[1]++;
						h.put(s4[i], a);
					}
				}
				
	}

	public static double similarity(Hashtable<String,int[]> h)
	 {
		Enumeration s=h.keys();
		String str;
		int[] b=new int[1];
		double t=0,m=0,n=0;
		long total;
		while(s.hasMoreElements()) {
	        str = (String) s.nextElement();
	        b=h.get(str);
	       // System.out.println(str+"("+b[0]+","+b[1]+")");
	        t=t+b[0]*b[1];
	        m=m+(b[0]*b[0]);
	        n=n+(b[1]*b[1]);
	        }
		//System.out.println(m);
		total=Math.round(t/(Math.sqrt(m)*Math.sqrt(n)));
		return total;
	 }
}